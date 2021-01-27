package pl.wwsis.MicroBlog.dao;

import java.text.ParseException;

import pl.wwsis.MicroBlog.model.User;


public interface UserDao {
	
		
	User getUserByLogin(String login);
	
	User registerUser(String login, String email, String password, String firstName, String lastName, 
			Character gender, String dob) throws ParseException;
	
	User giveLike(String login, Integer postId);
    
	User giveUnLike(String login, Integer postId);
	
}

