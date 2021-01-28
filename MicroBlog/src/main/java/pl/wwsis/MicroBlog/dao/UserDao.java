package pl.wwsis.MicroBlog.dao;

import java.text.ParseException;
import java.util.Date;
import pl.wwsis.MicroBlog.model.User;
import pl.wwsis.MicroBlog.model.UserStatus;


public interface UserDao {
	
	Date string2Date (String dateAsString) throws ParseException;
	
	User getUserById(int userId);
		
	User getUserByLogin(String login);
	
	User getUserByEmail(String email);
	
	User registerUser(String login, String email, String password, String firstName, String lastName, Character gender, String dob) throws ParseException;
	
	User login(String email, String password);
	
	String logout(int userId);
		
	boolean changeUserStatus(User user, UserStatus status);
	
	void changeBasicUserDetails(User user, String login, String firstName, String lastName, String dob, Character gender) throws ParseException;
	
	void changeUserEmail (User user, String newEmail);
	
	void sendConfirmationEmail (User user, String newEmail);
	
	User giveLike(String login, Integer postId);

    User giveUnLike(String login, Integer postId);
	
}

