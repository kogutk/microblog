package pl.wwsis.MicroBlog.dao.impl;

import pl.wwsis.MicroBlog.dao.UserDao;
import pl.wwsis.MicroBlog.model.User;
import pl.wwsis.MicroBlog.model.UserStatus;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import org.springframework.stereotype.Component;

@Transactional
@Component
public class UserDaoImpl implements UserDao {

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public Date string2Date(String dateAsString) throws ParseException {
		String pattern = "yyyy-mm-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		Date string2Date = simpleDateFormat.parse(dateAsString);
		return string2Date;
	}

	@Override
	public User getUserById(int userId) {
		User user = entityManager.find(User.class, userId);
		return user;
	}

	@Override
	public User getUserByLogin(String login) {
		String queryString = "FROM User u WHERE LOWER(u.login) = LOWER(:login)";
		Query query = entityManager.createQuery(queryString);
		query.setParameter("login", login);
		User user = (User) query.getSingleResult();
		return user;
	}

	@Override
	public User getUserByEmail(String email) {
		String queryString = "FROM User u WHERE LOWER(u.email) = LOWER(:email)";
		Query query = entityManager.createQuery(queryString);
		query.setParameter("email", email);
		User user = (User) query.getSingleResult();
		return user;
	}

	@Override
	public User getUserByToken(String token) {
		String queryString = "FROM User u WHERE u.token = :token";
		Query query = entityManager.createQuery(queryString);
		query.setParameter("token", token);
		User user = null;
		try {
			user = (User) query.getSingleResult();
		} catch (NoResultException e) {

		}
		return user;
	}

	@Override
	public User registerUser(String name, String email, String password, String firstName, String lastName,
			Character gender, String dob) throws ParseException {

		User user = new User(name, email, password, firstName, lastName, gender, string2Date(dob));

		entityManager.persist(user);
		return user;
	}

	@Override
	public User login(String email, String password) {
		String queryString = "FROM User u WHERE u.email = :email AND u.password= :password";
		Query query = entityManager.createQuery(queryString);
		query.setParameter("email", email);
		query.setParameter("password", password);
		User user = (User) query.getSingleResult();
		user.setLogged(true);
		user.setStatus(UserStatus.ACTIVE);
		entityManager.persist(user);
		return user;
	}

	@Override
	public boolean logout(int userId) {
		User user = entityManager.find(User.class, userId);
		user.setLogged(false);
		user.setStatus(UserStatus.INVISIBLE);
		entityManager.persist(user);
		System.out.println("You've been successfully logged out");

		return true;
	}

	@Override
	public boolean changeUserStatus(User user, UserStatus status) {

		if (!status.equals(user.getStatus())) {
			user.setStatus(status);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void changeBasicUserDetails(User u, String login, String firstName, String lastName, String dob,
			Character gender) throws ParseException {
		User user = entityManager.find(User.class, u.getId());
		Date dobAsDateFromString = string2Date(dob);

		boolean toChange = false;

		if (!login.equals(user.getLogin())) {
			user.setLogin(login);
			toChange = true;
		}

		if (!firstName.equals(user.getFirstName())) {
			user.setFirstName(firstName);
			toChange = true;
		}

		if (!firstName.equals(user.getFirstName())) {
			user.setFirstName(firstName);
			toChange = true;
		}

		if (!lastName.equals(user.getLastName())) {
			user.setLastName(lastName);
			toChange = true;
		}

		if (!dobAsDateFromString.equals(user.getDob())) {
			user.setDob(dobAsDateFromString);
			toChange = true;
		}
		if (!gender.equals(user.getGender())) {
			user.setGender(gender);
			toChange = true;
		}

		if (toChange) {
			entityManager.persist(user);
		}

	}

	@Override
	public boolean sendEmail(String email, String content) {
		// TODO
		return true;
	}

	@Override
	public boolean changeUserEmail(User u, String newEmail) {
		User user = entityManager.find(User.class, u.getId());
		String content = null;
		if (!newEmail.toLowerCase().equals(user.getEmail().toLowerCase())) {
			sendEmail(newEmail, content);
			user.setEmail(newEmail.toLowerCase());
			entityManager.persist(user);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean changeUserPassword(User u, String password, String newPassword, String repeatedPassword) {
		User user = entityManager.find(User.class, u.getId());
		if (newPassword.equals(repeatedPassword)) {
			user.setPassword(newPassword);
			entityManager.persist(user);
			return true;
		} else {
			return false;
		}
	}

	@Override

	public String generateTokenForUser(User u, long expirationTime) {
		User user = entityManager.find(User.class, u.getId());
		int leftLimit = 48; // numeral '0'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 6;
		String token = null;
		boolean unique = false;

		while (!unique) {
			Random random = new Random();
			token = random.ints(leftLimit, rightLimit + 1).filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
					.limit(targetStringLength)
					.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

			if (getUserByToken(token) == null) {
				unique = true;
				user.setToken(token);
				long currentDateTime = System.currentTimeMillis();
				Date tokenExpirationDate = new Date(currentDateTime + expirationTime);
				user.setTokenExpirationDate(tokenExpirationDate);
				entityManager.persist(user);
				TimerTask task = new TimerTask() {

					public void run() {
						deleteTokenExpired(user, expirationTime);
					}
				};
				Timer timer = new Timer("ExpirationTimer");

				timer.schedule(task, expirationTime);

			}

		}
		return token;

	}

	public boolean deleteTokenExpired(User u, long expirationTime) {

		u.setToken(null);
		u.setTokenExpirationDate(null);

		boolean deleted = true;
		System.out.println("Token and expiration date deleted: " + deleted);
		return deleted;

	}

	@Override
	public boolean validateUser(String email, String token) {
		User user = getUserByEmail(email);
		if (user.getToken().equals(token)) {
			return true;
		} else
			return false;
	}

	@Override
	public User giveLike(String login, Integer postId) {
		User user = this.getUserByLogin(login);
		ArrayList<String> arr = user.getLikedPosts();
		arr.add(postId.toString());
		user.setLikedPosts(arr);
		entityManager.merge(user);

		return user;
	}

	@Override
	public User giveUnLike(String login, Integer postId) {
		User user = this.getUserByLogin(login);

		ArrayList<String> arr = user.getLikedPosts();
		arr.remove(postId.toString());
		user.setLikedPosts(arr);
		entityManager.merge(user);

		return user;
	}

	@Override
	public User addToken(User user, String token) {
		User u = entityManager.find(User.class, user.getId());
		u.setToken(token);
		entityManager.persist(u);

		return u;

	}
}
