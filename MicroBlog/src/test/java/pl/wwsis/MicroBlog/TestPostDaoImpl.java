package pl.wwsis.MicroBlog;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import pl.wwsis.MicroBlog.dao.impl.PostDaoImpl;
import pl.wwsis.MicroBlog.dao.impl.UserDaoImpl;
import pl.wwsis.MicroBlog.model.Post;
import pl.wwsis.MicroBlog.model.User;

@RunWith(SpringRunner.class)

@SpringBootTest(classes = pl.wwsis.MicroBlog.App.class)

public class TestPostDaoImpl {
	
	@Autowired
	PostDaoImpl postDaoImpl;
	
	@Autowired
	UserDaoImpl userDaoImpl;

//	@BeforeEach
//	void setUp() throws Exception {
//	}
//
//	@AfterEach
//	void tearDown() throws Exception {
//	}

	@Test
	public void TestGetTimelineOfUser() {
		
		try {
		User testUser = userDaoImpl.registerUser("testowyLogin_21", "test21@test.com", "123pasS456ss", "Jan", "Kowalski", 'M', "1990-01-01");		
		Post testPost = postDaoImpl.addPostOfUser(testUser, "testowyPost123", true);
		
		assertEquals(1, postDaoImpl.getTimelineOfUser(testUser).size());

		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void TestGetFullTimelineOfUser() {
		List<Post> postListTest = new ArrayList<>();
		try {
			User testUser = userDaoImpl.registerUser("testowyLogin_22", "test22@test.com", "123pasS456ss", "Jan", "Kowalski", 'M', "1990-01-01");		
			Post testPost = postDaoImpl.addPostOfUser(testUser, "testowyPost123", true);
			postDaoImpl.addPostOfUser(testUser, "testowyPost23", true);
			postDaoImpl.addPostOfUser(testUser, "testowyPost3", false);
			
			postListTest = postDaoImpl.getFullTimelineOfUser(testUser);
			assertEquals(3, postListTest.size());

			} catch (ParseException e) {
				e.printStackTrace();
			}
		
	}
	
	@Test
	public void TestGetFullPublicTimelinee() {
		List<Post> postListTest = new ArrayList<>();
		postDaoImpl.deleteAllPosts();
		try {
			User testUser = userDaoImpl.registerUser("testowyLogin_23", "test23@test.com", "123pasS456ss", "Jan", "Kowalski", 'M', "1990-01-01");		
			Post testPost = postDaoImpl.addPostOfUser(testUser, "testowyPost123", true);
			postDaoImpl.addPostOfUser(testUser, "testowyPost23", true);
			postDaoImpl.addPostOfUser(testUser, "testowyPost3", false);
			
			postListTest = postDaoImpl.getFullPublicTimeline();
			assertEquals(2, postListTest.size());

			} catch (ParseException e) {
				e.printStackTrace();
			}
	}
	
	@Test
	public void TestDeleteAllPosts() {
		List<Post> postListTest = new ArrayList<>();
		try {
			User testUser = userDaoImpl.registerUser("testowyLogin_24", "test24@test.com", "123pasS456ss", "Jan", "Kowalski", 'M', "1990-01-01");		
			Post testPost = postDaoImpl.addPostOfUser(testUser, "testowyPost123", true);
			postDaoImpl.addPostOfUser(testUser, "testowyPost23", true);
			postDaoImpl.addPostOfUser(testUser, "testowyPost3", false);
			postDaoImpl.deleteAllPosts();
			postListTest = postDaoImpl.getFullTimelineOfUser(testUser);
			assertEquals(0, postListTest.size());

			} catch (ParseException e) {
				e.printStackTrace();
			}
	}
	
	@Test
	public void TestAddPostOfUser() {
		try {
			User testUser = userDaoImpl.registerUser("testowyLogin_25", "test25@test.com", "123pasS456ss", "Jan", "Kowalski", 'M', "1990-01-01");		
			Post testPost = postDaoImpl.addPostOfUser(testUser, "testowyPost123", true);
			
			assertEquals(1, postDaoImpl.getTimelineOfUser(testUser).size());

			} catch (ParseException e) {
				e.printStackTrace();
			}
	}
}
