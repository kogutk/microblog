package pl.wwsis.MicroBlog.dao.impl;

import pl.wwsis.MicroBlog.dao.UserDao;
import pl.wwsis.MicroBlog.model.User;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
	public User getUserByLogin(String login) {
		String queryString = "SELECT u FROM User u WHERE LOWER(u.login) = LOWER(:login)";
		Query query = entityManager.createQuery(queryString);
		query.setParameter("login", login);
		User user = (User) query.getSingleResult(); 
		return user;

	}

	@Override
	public User registerUser(String name, String email, String password, String firstName, String lastName,
			Character gender, String dob) throws ParseException {
		
		String pattern = "yyyy-mm-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		Date d = simpleDateFormat.parse(dob);

		User user = new User(name, email, password, firstName, lastName, gender, d, 0, 0);
		entityManager.persist(user);

		return user;

	}

}
