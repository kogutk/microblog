package pl.wwsis.MicroBlog.dao.impl;

import pl.wwsis.MicroBlog.dao.UserDao;
import pl.wwsis.MicroBlog.model.User;
import pl.wwsis.MicroBlog.model.UserStatus;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;



@Transactional
public class UserDaoImpl implements UserDao {

	@PersistenceContext
	EntityManager entityManager;
	
//	EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "test" );
//	EntityManager entityManager = emfactory.createEntityManager( );

	
	@Override
	public  User getUserByLogin(String login) {
		String queryString = "SELECT u FROM User u " + "WHERE LOWER(u.name) = LOWER(:login)";
		Query query = entityManager.createQuery(queryString);
		query.setParameter("login", login);
		User user = (User) query.getSingleResult(); 		
		return user;

	}
	
	
	@Override 
	public void registerUser(String name, String email, String password, String firstName, String lastName, Character gender,
			String dob) throws ParseException {
		
		String pattern = "yyyy-mm-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		Date d = simpleDateFormat.parse(dob);
		
		
		entityManager.getTransaction().begin();
		 
		      User user = new User(name,  email,  password,  firstName,  lastName,  gender, d);
		      entityManager.persist(user);
		  
		  entityManager.getTransaction().commit();
		
	}
	
		

}

