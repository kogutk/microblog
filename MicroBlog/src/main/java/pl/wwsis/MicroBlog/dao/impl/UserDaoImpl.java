package pl.wwsis.MicroBlog.dao.impl;

import pl.wwsis.MicroBlog.dao.UserDao;
import pl.wwsis.MicroBlog.model.User;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;
import org.thymeleaf.util.ArrayUtils;

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
	public User registerUser(String name, String email, String password,
			String firstName, String lastName,
			Character gender, String dob ) throws ParseException {
		
		String pattern = "yyyy-mm-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		Date d = simpleDateFormat.parse(dob);

		User user = new User(name, email, password, firstName, lastName, gender, d);
		entityManager.persist(user);

		return user;
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
		System.out.println("$$$" + arr);
		arr.remove(postId.toString());
		System.out.println("$$$ after remove" + arr);
		user.setLikedPosts(arr);
		
		entityManager.merge(user);
		
		return user;
	}
	
}
