package pl.wwsis.MicroBlog;

import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import pl.wwsis.MicroBlog.dao.impl.FollowerDaoImpl;
import pl.wwsis.MicroBlog.dao.impl.UserDaoImpl;
import pl.wwsis.MicroBlog.model.Follower;
import pl.wwsis.MicroBlog.model.FollowerId;
import pl.wwsis.MicroBlog.model.User;

@RunWith(SpringRunner.class)

@SpringBootTest(classes = pl.wwsis.MicroBlog.App.class)

class TestFollowerDaoImpl {
	
	@Autowired
	FollowerDaoImpl followerDaoImpl;
	
	@Autowired
	UserDaoImpl userDaoImpl;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void TestAddFolloweeOfUser() {
		try {
			User testUser_1 = userDaoImpl.registerUser("testowyLogin_1", "test@test.com", "pass", "Jan", "Kowalski", 'M', "1990-01-01");
			User testUser_2 = userDaoImpl.registerUser("testowyLogin_2", "test@test.com", "pass", "Jan", "Kowalski", 'M', "1990-01-01");
			
			Follower follower = followerDaoImpl.addFolloweeOfUser(testUser_1, testUser_2);
			assertEquals(follower.getFollowsUserId(), testUser_2.getId());
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}
	
	@Test
	void TestFindFolloweeOfUser() {
		try {
		User testUser_1 = userDaoImpl.registerUser("testowyLogin_1", "test@test.com", "pass", "Jan", "Kowalski", 'M', "1990-01-01");
		User testUser_2 = userDaoImpl.registerUser("testowyLogin_2", "test@test.com", "pass", "Jan", "Kowalski", 'M', "1990-01-01");
		
		Follower follower = followerDaoImpl.addFolloweeOfUser(testUser_1, testUser_2);
		assertEquals(follower.getFollowsUserId(), testUser_2.getId());
		followerDaoImpl.findFolloweeOfUser(testUser_1, testUser_2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void TestDeleteFolloweeOfUser() {
		try {
			User testUser_1 = userDaoImpl.registerUser("testowyLogin_1", "test@test.com", "pass", "Jan", "Kowalski", 'M', "1990-01-01");
			User testUser_2 = userDaoImpl.registerUser("testowyLogin_2", "test@test.com", "pass", "Jan", "Kowalski", 'M', "1990-01-01");
			
			Follower follower = followerDaoImpl.addFolloweeOfUser(testUser_1, testUser_2);
			assertEquals(follower.getFollowsUserId(), testUser_2.getId());
			Follower removedFollower = followerDaoImpl.deleteFolloweeOfUser(testUser_1, testUser_2);
			assertEquals(removedFollower.getFollowsUserId(), testUser_1.getId());
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
