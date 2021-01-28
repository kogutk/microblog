package pl.wwsis.MicroBlog.dao.impl;

import pl.wwsis.MicroBlog.dao.UserDao;
import pl.wwsis.MicroBlog.model.User;
import pl.wwsis.MicroBlog.model.UserStatus;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.persistence.EntityManager;
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

		return user;

	}

	@Override
	public String logout(int userId) {
		User user = entityManager.find(User.class, userId);
		user.setLogged(false);
		user.setStatus(UserStatus.INVISIBLE);
		String logoutStatement = "You've been successfully logged out";

		return logoutStatement;

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
	public void changeBasicUserDetails(User user, String login, String firstName, String lastName, String dob,
			Character gender) throws ParseException {

		Date dobAsDateFromString = string2Date(dob);

		if (!login.equals(user.getLogin())) {
			user.setLogin(login);
		}

		if (!firstName.equals(user.getFirstName())) {
			user.setFirstName(firstName);
		}

		if (!firstName.equals(user.getFirstName())) {
			user.setFirstName(firstName);
		}

		if (!lastName.equals(user.getLastName())) {
			user.setLastName(lastName);
		}

		if (!dobAsDateFromString.equals(user.getDob())) {
			user.setDob(dobAsDateFromString);
		}
		if (!gender.equals(user.getGender())) {
			user.setGender(gender);
		}

	}

	@Override
	public void sendConfirmationEmail(User user, String newEmail) {
		// TO DO
	}

	@Override
	public void changeUserEmail(User user, String newEmail) {
		if (!newEmail.toLowerCase().equals(user.getEmail().toLowerCase())) {
			sendConfirmationEmail(user, newEmail);
			user.setEmail(newEmail);
		}

	}

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

}
