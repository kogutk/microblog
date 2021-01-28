package pl.wwsis.MicroBlog;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import pl.wwsis.MicroBlog.model.Follower;
import pl.wwsis.MicroBlog.model.Post;
import pl.wwsis.MicroBlog.model.User;
import pl.wwsis.MicroBlog.service.impl.MicroblogServiceImpl;

@RunWith(SpringRunner.class)

@SpringBootTest(classes = pl.wwsis.MicroBlog.App.class)

public class TestMicroblogServiceImpl {
	
	
	@Autowired
	MicroblogServiceImpl microblogServiceImpl;
	
	@Test
	public void TestShouldReturnAllPostsFromSpecificUser() {
		//when
		User testUser = microblogServiceImpl.registerUser("testowyLogin_101", "test101@test.com", "123pasS4%56ss", "Jan", "Kowalski", 'M', "1990-01-01");								
		
		microblogServiceImpl.createNewPost(testUser, "testowyPost1", true);
		microblogServiceImpl.createNewPost(testUser, "testowyPost11", false);
		
		List<Post> testUserPostList = microblogServiceImpl.getAllPostsFromSpecificUser(testUser);	
		
		//then
		assertEquals(2, testUserPostList.size());
	}

	@Test
	public void TestShouldGetAllPostsFromSpecificUserAndHisFollowers() {
		//when
		User testUser2 = microblogServiceImpl.registerUser("testowyLogin_202", "test202@test.com", "123pasS456%ss", "Jan", "Kowalski", 'M', "1990-01-01");								
		User testUser3 = microblogServiceImpl.registerUser("testowyLogin_303", "test303@test.com", "123pasS456%ss", "Jan", "Kowalski", 'M', "1990-01-01");								
		
		microblogServiceImpl.createNewPost(testUser2, "testowyPost2", true);
		microblogServiceImpl.createNewPost(testUser2, "testowyPost21", false);
		microblogServiceImpl.createNewPost(testUser3, "testowyPostUser3", true);
		
		microblogServiceImpl.addFolloweeOfUser(testUser2, testUser3);
		
		List<Post> testUserPostListWithFollowePosts = microblogServiceImpl.getAllPostsFromSpecificUserAndHisFollowers(testUser2);	
		
		//then
		assertEquals(3, testUserPostListWithFollowePosts.size());
	}
	
	@Test
	public void TestShouldDeleteAllPosts() {
		//when
		User testUser2 = microblogServiceImpl.registerUser("testowyLogin_606", "test606@test.com", "123pasS456%ss", "Jan", "Kowalski", 'M', "1990-01-01");								
		User testUser3 = microblogServiceImpl.registerUser("testowyLogin_707", "test707@test.com", "123pasS456%ss", "Jan", "Kowalski", 'M', "1990-01-01");								
		
		microblogServiceImpl.createNewPost(testUser2, "testowyPost2", true);
		microblogServiceImpl.createNewPost(testUser2, "testowyPost21", false);
		microblogServiceImpl.createNewPost(testUser3, "testowyPostUser3", true);
		
		
		microblogServiceImpl.deleteAllPosts();
		List<Post> listWithAllPosts = microblogServiceImpl.getAllPosts();	
		//then
		assertEquals(0, listWithAllPosts.size());
	}
	
	@Test
	public void TestShouldGetAllPosts() {
		//removed all posts
		microblogServiceImpl.deleteAllPosts();
		
		//when
		User testUser4 = microblogServiceImpl.registerUser("testowyLogin_4", "test4@test.com", "123pasS45%6ss", "Jan", "Kowalski", 'M', "1990-01-01");								
		User testUser5 = microblogServiceImpl.registerUser("testowyLogin_5", "test5@test.com", "123pasS45%6ss", "Jan", "Kowalski", 'M', "1990-01-01");								
		
		microblogServiceImpl.createNewPost(testUser4, "testowyPost4", true);
		microblogServiceImpl.createNewPost(testUser4, "testowyPost41", true);
		microblogServiceImpl.createNewPost(testUser5, "testowyPostUser5", true);
		microblogServiceImpl.createNewPost(testUser5, "testowyPostUser51", true);
		
		List<Post> allPosts = microblogServiceImpl.getAllPosts();
		
		//then
		assertEquals(4, allPosts.size());
	}
	
	@Test
	public void TestShouldCreateNewPost() {
		//when
		User testUser6 = microblogServiceImpl.registerUser("testowyLogin_60", "test60@test.com", "123pasS4%56ss", "Jan", "Kowalski", 'M', "1990-01-01");								
		Integer userId = testUser6.getId();
		
		Post newPost = microblogServiceImpl.createNewPost(testUser6, "testowyPost6", true);
		
		//then
		assertEquals(userId, newPost.authorId());
		assertEquals("testowyPost6", newPost.getContent());
		assertTrue(newPost.isPublic());
	}
	
	@Test
	public void TestShouldGetUserByLogin() {
		//when
		User testUser7 = microblogServiceImpl.registerUser("testowyLogin_7", "test7@test.com", "123pasS4%56ss", "Jan", "Kowalski", 'M', "1990-01-01");								
		String userLogin = testUser7.getLogin();
		
		User testUserFromLogin = microblogServiceImpl.getUserByLogin(userLogin);
		
		//then
		assertAll("testUserFromLogin", 
				() -> assertEquals("test7@test.com", testUserFromLogin.getEmail()),
				() -> assertEquals("123pasS4%56ss", testUserFromLogin.getPassword()),
				() -> assertEquals("Jan", testUserFromLogin.getFirstName()),
				() -> assertEquals("Kowalski", testUserFromLogin.getLastName()),
				() -> assertEquals("M", testUserFromLogin.getGender().toString()),
				() -> assertEquals("INVISIBLE", testUserFromLogin.getStatus().toString()),
				() -> assertEquals("1990-01-01 00:01:00.0", testUserFromLogin.getDob().toString()),
				() -> assertEquals(0, testUserFromLogin.getAmountOfFollowee()),
				() -> assertEquals(0, testUserFromLogin.getAmountOfFollowers())
			);
	}
	
	@Test
	public void TestShouldRegisterUser() {
		//when
		User testUser8 = microblogServiceImpl.registerUser("testowyLogin_8", "test8@test.com", "123pasS45%6ss", "Jan", "Kowalski", 'M', "1990-01-01");								
		String userLogin = testUser8.getLogin();
		
		//find register user
		User testUserFromLogin = microblogServiceImpl.getUserByLogin(userLogin);
		
		//then
		assertNotNull(testUserFromLogin);
	}
	
	@Test
	public void TestShouldAddFolloweeOfUser() {
		//when
		User testUser9 = microblogServiceImpl.registerUser("testowyLogin_9", "test9@test.com", "123pasS4%56ss", "Jan", "Kowalski", 'M', "1990-01-01");								
		User testUser10 = microblogServiceImpl.registerUser("testowyLogin_10", "test10@test.com", "123pasS%456ss", "Jan", "Kowalski", 'M', "1990-01-01");								
		
		Follower newFollowee = microblogServiceImpl.addFolloweeOfUser(testUser9, testUser10);

		//then
		assertAll("newFollowee",
				() -> assertEquals(1, testUser9.getAmountOfFollowee()),
				() -> assertEquals(newFollowee.getFollowsUserId(), testUser10.getId()),
				() -> assertEquals(newFollowee.getId(), testUser9.getId())
		);
	}
	
	@Test
	public void TestShouldFindFolloweeOfUser() {
		//when
		User testUser11 = microblogServiceImpl.registerUser("testowyLogin_110", "test11@test.com", "123pasS%456ss", "Jan", "Kowalski", 'M', "1990-01-01");								
		User testUser12 = microblogServiceImpl.registerUser("testowyLogin_120", "test12@test.com", "123pasS4%56ss", "Jan", "Kowalski", 'M', "1990-01-01");								
		
		microblogServiceImpl.addFolloweeOfUser(testUser11, testUser12);
		Follower findedFollower = microblogServiceImpl.findFolloweeOfUser(testUser11, testUser12);

		//then
		assertEquals(findedFollower.getFollowsUserId(), testUser12.getId());
	}
	
	@Test
	public void TestShouldDeleteFolloweeOfUser() {
		//when
		User testUser13 = microblogServiceImpl.registerUser("testowyLogin_130", "test130@test.com", "123pasS4%56ss", "Jan", "Kowalski", 'M', "1990-01-01");								
		User testUser14 = microblogServiceImpl.registerUser("testowyLogin_140", "test140@test.com", "123pasS4%56ss", "Jan", "Kowalski", 'M', "1990-01-01");								
		
		Follower followee = microblogServiceImpl.addFolloweeOfUser(testUser13, testUser14);		
		assertEquals(followee.getFollowsUserId(), testUser14.getId());
		
		Follower removedFollowee = microblogServiceImpl.deleteFolloweeOfUser(testUser13, testUser14);		
		assertEquals(removedFollowee.getFollowsUserId(), testUser14.getId());
		
		assertEquals(0, testUser14.getAmountOfFollowee());
		
	}

	@Test
	public void TestShouldGetListOfUsersFollowers(){
		List<Follower> followers = new ArrayList<>();

		User testUser_1 = microblogServiceImpl.registerUser("testowyLogin_251", "test_251@test.com", "123pas%S456ss", "Jane", "Kowalski", 'F', "1990-01-01");
		User testUser_2 = microblogServiceImpl.registerUser("testowyLogin_261", "test_261@test.com", "123pa%sS456ss", "Jan", "Kowalski", 'M', "1990-01-01");
		User testUser_3 = microblogServiceImpl.registerUser("testowyLogin_271", "test_271@test.com", "123pa%sS456ss", "Jan", "Kowalski", 'M', "1990-01-01");
		User testUser_4 = microblogServiceImpl.registerUser("testowyLogin_281", "test_281@test.com", "123pa%sS456ss", "Jan", "Kowalski", 'M', "1990-01-01");


		followers.add(microblogServiceImpl.addFolloweeOfUser(testUser_2, testUser_1));
		followers.add(microblogServiceImpl.addFolloweeOfUser(testUser_3, testUser_1));
		followers.add(microblogServiceImpl.addFolloweeOfUser(testUser_4, testUser_1));

		List<Follower> followers1 = microblogServiceImpl.getListOfUsersFollowers(testUser_1);

		for (int i = 0;i<followers.size();i++) {
			assertEquals(followers.get(i).getId(), followers1.get(i).getId());
			assertEquals(followers.get(i).getFollowsUserId(), followers1.get(i).getFollowsUserId());
		}
	}
	
	@Test
	public void TestLikePostByUser() {
		//when
		User testUser_1 = microblogServiceImpl.registerUser("testowyLogin_311", "test_311@test.com", "123pas%S456ss", "Jane", "Kowalski", 'F', "1990-01-01");
		Post testPost_1 = microblogServiceImpl.createNewPost(testUser_1, "CONTENT", true);
		User userAfterLike = microblogServiceImpl.likePostByUser(testUser_1.getLogin(), testPost_1.getPostId());
		String idFromUserLikedPost = userAfterLike.getLikedPosts().get(0);
		
		//then
		assertNotNull(idFromUserLikedPost);
		assertEquals(testPost_1.getPostId(), Integer.parseInt(idFromUserLikedPost) );
	}
	
	@Test
	public void TestUnLikePostByUser() {
		//when
		User testUser_1 = microblogServiceImpl.registerUser("testowyLogin_312", "test_312@test.com", "123pas%S456ss", "Jane", "Kowalski", 'F', "1990-01-01");
		Post testPost_1 = microblogServiceImpl.createNewPost(testUser_1, "CONTENT", true);
		
		microblogServiceImpl.likePostByUser(testUser_1.getLogin(), testPost_1.getPostId());
		User userAfterUnlikePost = microblogServiceImpl.unlikePostByUser(testUser_1.getLogin(), testPost_1.getPostId());
		
		//then
		assertEquals(0, userAfterUnlikePost.getLikedPosts().size());
	}

}
