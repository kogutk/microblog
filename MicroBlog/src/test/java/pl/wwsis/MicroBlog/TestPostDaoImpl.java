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

	@Test
	public void TestGetTimelineOfUser() {		
		try {
			User testUser = userDaoImpl.registerUser("testowyLogin_21", "test21@test.com", "123pasS456ss", "Jan", "Kowalski", 'M', "1990-01-01");		
			postDaoImpl.addPostOfUser(testUser, "testowyPost123", true);
			
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
			postDaoImpl.addPostOfUser(testUser, "testowyPost123", true);
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
			postDaoImpl.addPostOfUser(testUser, "testowyPost123", true);
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
			postDaoImpl.addPostOfUser(testUser, "testowyPost123", true);
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
			postDaoImpl.addPostOfUser(testUser, "testowyPost123", true);
			
			assertEquals(1, postDaoImpl.getTimelineOfUser(testUser).size());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void TestGetLikedByUser() {
		try {
			User testUser = userDaoImpl.registerUser("testowyLogin_26", "test26@test.com", "123pasS456ss", "Jan", "Kowalski", 'M', "1990-01-01");		
			Post testPostLike = postDaoImpl.addPostOfUser(testUser, "testowyPost123", true);
			
			Post testPostAfterLike = postDaoImpl.getLikedByUser(testPostLike.getPostId());

			assertEquals(1, testPostAfterLike.amountOfLike());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void TestGetUnLikedByUser() {
		try {
			User testUser = userDaoImpl.registerUser("testowyLogin_27", "test27@test.com", "123pasS456ss", "Jan", "Kowalski", 'M', "1990-01-01");		
			Post testPostLike = postDaoImpl.addPostOfUser(testUser, "testowyPost1235", true);
			
			Post testPostAfterLike = postDaoImpl.getLikedByUser(testPostLike.getPostId());
			assertEquals(1, testPostAfterLike.amountOfLike());

			Post testPostAfterUnLike = postDaoImpl.getUnLikedByUser(testPostLike.getPostId());
			assertEquals(0, testPostAfterUnLike.amountOfLike());
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void TestAddCommentToPost() {
		try {
			User testUser = userDaoImpl.registerUser("testowyLogin_28", "test28@test.com", "123pasS456ss", "Jan", "Kowalski", 'M', "1990-01-01");		
			Post testPost = postDaoImpl.addPostOfUser(testUser, "testowyPost123", true);
			
			assertEquals(1, postDaoImpl.getTimelineOfUser(testUser).size());
			String commentTextTest = "Test comment for Post";
			ArrayList<String> testPostComments = testPost.getComments();
			testPostComments.add(commentTextTest);
			postDaoImpl.addCommentToPost(testPost.getPostId(), testPostComments);
			assertTrue(testPost.getComments().contains(commentTextTest));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void TestAddTagToPost() {
		try {
			User testUser = userDaoImpl.registerUser("testowyLogin_29", "test29@test.com", "123pasS456ss", "Jan", "Kowalski", 'M', "1990-01-01");		
			Post testPost = postDaoImpl.addPostOfUser(testUser, "testowyPost123", true);
			
			assertEquals(1, postDaoImpl.getTimelineOfUser(testUser).size());
			String commentTagTest = "#tag1";
			ArrayList<String> testPostTags = testPost.getTags();
			testPostTags.add(commentTagTest);
			postDaoImpl.addTagToPost(testPost.getPostId(), testPostTags);
			assertTrue(testPost.getTags().contains(commentTagTest));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
}
