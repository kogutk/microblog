package pl.wwsis.MicroBlog;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import pl.wwsis.MicroBlog.dao.impl.UserDaoImpl;
import pl.wwsis.MicroBlog.model.User;


@RunWith(SpringRunner.class)

@SpringBootTest(classes = pl.wwsis.MicroBlog.App.class)
public class TestUserDaoImpl {
	
	@Autowired
	UserDaoImpl userDaoImpl;
	
	@Test
	public void TestRegisterUser() {
		try {
			User testuser = userDaoImpl.registerUser("testowyLogin_1", "test1@test.com", "passssswS9", "Jan", "Kowalski", 'M', "1990-01-01");
			assertEquals(testuser.getLogin(), "testowyLogin_1");
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		
	}
	
	@Test
	public void TestGetUserByLogin() {
		try {
			User testUser = userDaoImpl.registerUser("testowyLogin_2", "test2@test.com", "passssssS9sssssss", "Jan", "Kowalski", 'M', "1990-01-01");
			assertEquals(testUser.getLogin(), userDaoImpl.getUserByLogin(testUser.getLogin()).getLogin());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void TestGiveLike() {
		try {
			User testUser = userDaoImpl.registerUser("testowyLogin_3", "test3@test.com", "passssssS9sssssss", "Jan", "Kowalski", 'M', "1990-01-01");
			Integer postId = 192;
			Integer postId2 = 62;
			
			userDaoImpl.giveLike(testUser.getLogin(), postId);
			User testUserWithLikedPost = userDaoImpl.giveLike(testUser.getLogin(), postId2);
			
			System.out.print("%%%%%%%%%%%%%" + testUserWithLikedPost.getLikedPosts());
			
			assertEquals(testUser.getLogin(), userDaoImpl.getUserByLogin(testUser.getLogin()).getLogin());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
}
