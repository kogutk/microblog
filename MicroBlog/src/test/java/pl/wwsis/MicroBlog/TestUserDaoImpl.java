package pl.wwsis.MicroBlog;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import pl.wwsis.MicroBlog.dao.impl.UserDaoImpl;
import pl.wwsis.MicroBlog.model.User;


@RunWith(SpringRunner.class)

@SpringBootTest(classes = pl.wwsis.MicroBlog.App.class)
class TestUserDaoImpl {
	
	@Autowired
	UserDaoImpl userDaoImpl;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Test
	void TestRegisterUser() {
		try {
			User testuser = userDaoImpl.registerUser("testowyLogin_1", "test@test.com", "pass", "Jan", "Kowalski", 'M', "1990-01-01");
			assertEquals(testuser.getLogin(), "testowyLogin_1");
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		
	}
	
	@Test
	void TestGetUserByLogin() {
		try {
		User testUser = userDaoImpl.registerUser("testowyLogin", "test@test.com", "pass", "Jan", "Kowalski", 'M', "1990-01-01");
		assertEquals(testUser, userDaoImpl.getUserByLogin(testUser.getLogin()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
