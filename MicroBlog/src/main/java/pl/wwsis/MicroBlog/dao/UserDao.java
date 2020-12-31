package pl.wwsis.MicroBlog.dao;

import java.text.ParseException;
import pl.wwsis.MicroBlog.model.User;


public interface UserDao {
	
		
	User getUserByLogin(String login);
	
	void registerUser(String login, String email, String password, String firstName, String lastName, Character gender, String dob) throws ParseException;
    
}
