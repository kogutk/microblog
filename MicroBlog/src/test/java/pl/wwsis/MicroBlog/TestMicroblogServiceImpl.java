package pl.wwsis.MicroBlog;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import pl.wwsis.MicroBlog.model.Follower;
import pl.wwsis.MicroBlog.model.Post;
import pl.wwsis.MicroBlog.model.User;
import pl.wwsis.MicroBlog.model.UserStatus;
import pl.wwsis.MicroBlog.service.impl.MicroblogServiceImpl;

@RunWith(SpringRunner.class)

@SpringBootTest(classes = pl.wwsis.MicroBlog.App.class)

public class TestMicroblogServiceImpl {

	@Autowired
	MicroblogServiceImpl microblogServiceImpl;

	@Test
	public void TestShouldReturnAllPostsFromSpecificUser() throws ParseException {
		// when
		User testUser = microblogServiceImpl.registerUser("testowyLogin_101", "test101@test.com", "123pasS4%56ss",
				"Jan", "Kowalski", 'M', "1990-01-01");

		microblogServiceImpl.createNewPost(testUser, "testowyPost1", true);
		microblogServiceImpl.createNewPost(testUser, "testowyPost11", false);

		List<Post> testUserPostList = microblogServiceImpl.getAllPostsFromSpecificUser(testUser);

		// then
		assertEquals(2, testUserPostList.size());
	}

	@Test
	public void TestShouldGetAllPostsFromSpecificUserAndHisFollowers() throws ParseException {
		// when
		User testUser2 = microblogServiceImpl.registerUser("testowyLogin_202", "test202@test.com", "123pasS456%ss",
				"Jan", "Kowalski", 'M', "1990-01-01");
		User testUser3 = microblogServiceImpl.registerUser("testowyLogin_303", "test303@test.com", "123pasS456%ss",
				"Jan", "Kowalski", 'M', "1990-01-01");

		microblogServiceImpl.createNewPost(testUser2, "testowyPost2", true);
		microblogServiceImpl.createNewPost(testUser2, "testowyPost21", false);
		microblogServiceImpl.createNewPost(testUser3, "testowyPostUser3", true);

		microblogServiceImpl.addFolloweeOfUser(testUser2, testUser3);

		List<Post> testUserPostListWithFollowePosts = microblogServiceImpl
				.getAllPostsFromSpecificUserAndHisFollowers(testUser2);

		// then
		assertEquals(3, testUserPostListWithFollowePosts.size());
	}

	@Test
	public void TestShouldDeleteAllPosts() throws ParseException {
		// when
		User testUser2 = microblogServiceImpl.registerUser("testowyLogin_606", "test606@test.com", "123pasS456%ss",
				"Jan", "Kowalski", 'M', "1990-01-01");
		User testUser3 = microblogServiceImpl.registerUser("testowyLogin_707", "test707@test.com", "123pasS456%ss",
				"Jan", "Kowalski", 'M', "1990-01-01");

		microblogServiceImpl.createNewPost(testUser2, "testowyPost2", true);
		microblogServiceImpl.createNewPost(testUser2, "testowyPost21", false);
		microblogServiceImpl.createNewPost(testUser3, "testowyPostUser3", true);

		microblogServiceImpl.deleteAllPosts();
		List<Post> listWithAllPosts = microblogServiceImpl.getAllPosts();
		// then
		assertEquals(0, listWithAllPosts.size());
	}

	@Test
	public void TestShouldGetAllPosts() throws ParseException {
		// removed all posts
		microblogServiceImpl.deleteAllPosts();

		// when
		User testUser4 = microblogServiceImpl.registerUser("testowyLogin_4", "test4@test.com", "123pasS45%6ss", "Jan",
				"Kowalski", 'M', "1990-01-01");
		User testUser5 = microblogServiceImpl.registerUser("testowyLogin_5", "test5@test.com", "123pasS45%6ss", "Jan",
				"Kowalski", 'M', "1990-01-01");

		microblogServiceImpl.createNewPost(testUser4, "testowyPost4", true);
		microblogServiceImpl.createNewPost(testUser4, "testowyPost41", true);
		microblogServiceImpl.createNewPost(testUser5, "testowyPostUser5", true);
		microblogServiceImpl.createNewPost(testUser5, "testowyPostUser51", true);

		List<Post> allPosts = microblogServiceImpl.getAllPosts();

		// then
		assertEquals(4, allPosts.size());
	}

	@Test
	public void TestShouldCreateNewPost() throws ParseException {
		// when
		User testUser6 = microblogServiceImpl.registerUser("testowyLogin_60", "test60@test.com", "123pasS4%56ss", "Jan",
				"Kowalski", 'M', "1990-01-01");
		Integer userId = testUser6.getId();

		Post newPost = microblogServiceImpl.createNewPost(testUser6, "testowyPost6", true);

		// then
		assertEquals(userId, newPost.authorId());
		assertEquals("testowyPost6", newPost.getContent());
		assertTrue(newPost.isPublic());
	}

	@Test
	public void TestShouldAddFolloweeOfUser() throws ParseException {
		// when
		User testUser9 = microblogServiceImpl.registerUser("testowyLogin_9", "test9@test.com", "123pasS4%56ss", "Jan",
				"Kowalski", 'M', "1990-01-01");
		User testUser10 = microblogServiceImpl.registerUser("testowyLogin_10", "test10@test.com", "123pasS%456ss",
				"Jan", "Kowalski", 'M', "1990-01-01");

		Follower newFollowee = microblogServiceImpl.addFolloweeOfUser(testUser9, testUser10);

		// then
		assertAll("newFollowee", () -> assertEquals(1, testUser9.getAmountOfFollowee()),
				() -> assertEquals(newFollowee.getFollowsUserId(), testUser10.getId()),
				() -> assertEquals(newFollowee.getId(), testUser9.getId()));
	}

	@Test
	public void TestShouldFindFolloweeOfUser() throws ParseException {
		// when
		User testUser11 = microblogServiceImpl.registerUser("testowyLogin_110", "test11@test.com", "123pasS%456ss",
				"Jan", "Kowalski", 'M', "1990-01-01");
		User testUser12 = microblogServiceImpl.registerUser("testowyLogin_120", "test12@test.com", "123pasS4%56ss",
				"Jan", "Kowalski", 'M', "1990-01-01");

		microblogServiceImpl.addFolloweeOfUser(testUser11, testUser12);
		Follower findedFollower = microblogServiceImpl.findFolloweeOfUser(testUser11, testUser12);

		// then
		assertEquals(findedFollower.getFollowsUserId(), testUser12.getId());
	}

	@Test
	public void TestShouldDeleteFolloweeOfUser() throws ParseException {
		// when
		User testUser13 = microblogServiceImpl.registerUser("testowyLogin_130", "test130@test.com", "123pasS4%56ss",
				"Jan", "Kowalski", 'M', "1990-01-01");
		User testUser14 = microblogServiceImpl.registerUser("testowyLogin_140", "test140@test.com", "123pasS4%56ss",
				"Jan", "Kowalski", 'M', "1990-01-01");

		Follower followee = microblogServiceImpl.addFolloweeOfUser(testUser13, testUser14);
		assertEquals(followee.getFollowsUserId(), testUser14.getId());

		Follower removedFollowee = microblogServiceImpl.deleteFolloweeOfUser(testUser13, testUser14);
		assertEquals(removedFollowee.getFollowsUserId(), testUser14.getId());

		assertEquals(0, testUser14.getAmountOfFollowee());

	}

	@Test
	public void TestAddTagToPost() throws ParseException {
		// when
		User testUser6 = microblogServiceImpl.registerUser("testowyLogin_401", "test401@test.com", "123pasS4%56ss",
				"Jan", "Kowalski", 'M', "1990-01-01");
		Integer userId = testUser6.getId();
		Post newPost = microblogServiceImpl.createNewPost(testUser6, "testowyPost6", true);

		String tagTest = "#testTag";
		ArrayList<String> tags = newPost.getTags();
		tags.add(tagTest);
		microblogServiceImpl.addTagToPost(newPost.getId(), tags);

		// then
		assertTrue(newPost.getTags().contains(tagTest));
	}

	@Test
	public void TestAddCommentToPost() throws ParseException {
		// when
		User testUser6 = microblogServiceImpl.registerUser("testowyLogin_402", "test402@test.com", "123pasS4%56ss",
				"Jan", "Kowalski", 'M', "1990-01-01");
		Integer userId = testUser6.getId();
		Post newPost = microblogServiceImpl.createNewPost(testUser6, "testowyPost6", true);

		String commentTest = "comment Test";
		ArrayList<String> comments = newPost.getComments();
		comments.add(commentTest);
		microblogServiceImpl.addCommentToPost(newPost.getId(), comments);
		
		//then
		assertTrue(newPost.getComments().contains(commentTest));
	}

	@Test
	public void TestShouldGetListOfUsersFollowers() throws ParseException {
		List<Follower> followers = new ArrayList<>();

		User testUser_1 = microblogServiceImpl.registerUser("testowyLogin_251", "test_251@test.com", "123pas%S456ss",
				"Jane", "Kowalski", 'F', "1990-01-01");
		User testUser_2 = microblogServiceImpl.registerUser("testowyLogin_261", "test_261@test.com", "123pa%sS456ss",
				"Jan", "Kowalski", 'M', "1990-01-01");
		User testUser_3 = microblogServiceImpl.registerUser("testowyLogin_271", "test_271@test.com", "123pa%sS456ss",
				"Jan", "Kowalski", 'M', "1990-01-01");
		User testUser_4 = microblogServiceImpl.registerUser("testowyLogin_281", "test_281@test.com", "123pa%sS456ss",
				"Jan", "Kowalski", 'M', "1990-01-01");

		followers.add(microblogServiceImpl.addFolloweeOfUser(testUser_2, testUser_1));
		followers.add(microblogServiceImpl.addFolloweeOfUser(testUser_3, testUser_1));
		followers.add(microblogServiceImpl.addFolloweeOfUser(testUser_4, testUser_1));

		List<Follower> followers1 = microblogServiceImpl.getListOfUsersFollowers(testUser_1);

		for (int i = 0; i < followers.size(); i++) {
			assertEquals(followers.get(i).getId(), followers1.get(i).getId());
			assertEquals(followers.get(i).getFollowsUserId(), followers1.get(i).getFollowsUserId());
		}
	}

	@Test
	public void TestSchouldConvertString2Date() {
		try {
			Date date = Calendar.getInstance().getTime();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String strDate = dateFormat.format(date);
			Date dateAsString2Date = microblogServiceImpl.string2Date(strDate);

			assertEquals(dateFormat.format(date), dateFormat.format(dateAsString2Date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void TestShouldRegisterUser() {
		try {
			User testUser = microblogServiceImpl.registerUser("testowyLogin_kjjkjh", "testkhjk@test.com",
					"passSssss?sw9", "Jan", "Kowalski", 'M', "1990-01-01");
			assertEquals(testUser.getLogin(), "testowyLogin_kjjkjh");
			assertFalse(testUser.isLogged());
			assertEquals(testUser.getStatus(), UserStatus.INVISIBLE);
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void TestShouldGetUserById() {
		try {
			User testUser = microblogServiceImpl.registerUser("testowyLogin0jjhjh", "test0khkj@test.com",
					"passsss+sS9%sssssss", "Jan", "Kowalski", 'M', "1990-01-01");
			assertEquals(testUser.getId(), microblogServiceImpl.getUserById(testUser.getId()).getId());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void TestShouldGetUserByLogin() {
		try {
			User testUser = microblogServiceImpl.registerUser("testowyLogin_25455", "test25465465@test.com",
					"passs%sssS9sssssss", "Jan", "Kowalski", 'M', "1990-01-01");
			assertEquals(testUser.getLogin(), microblogServiceImpl.getUserByLogin(testUser.getLogin()).getLogin());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void TestShouldGetUserByLogin2() throws ParseException {
		// when
		User testUser7 = microblogServiceImpl.registerUser("testowyLogin_7", "test7@test.com", "123pasS4%56ss", "Jan",
				"Kowalski", 'M', "1990-01-01");
		String userLogin = testUser7.getLogin();

		User testUserFromLogin = microblogServiceImpl.getUserByLogin(userLogin);

		// then
		assertAll("testUserFromLogin", () -> assertEquals("test7@test.com", testUserFromLogin.getEmail()),
				() -> assertEquals("123pasS4%56ss", testUserFromLogin.getPassword()),
				() -> assertEquals("Jan", testUserFromLogin.getFirstName()),
				() -> assertEquals("Kowalski", testUserFromLogin.getLastName()),
				() -> assertEquals("M", testUserFromLogin.getGender().toString()),
				() -> assertEquals("INVISIBLE", testUserFromLogin.getStatus().toString()),
				() -> assertEquals("1990-01-01 00:00:00.0", testUserFromLogin.getDob().toString()),
				() -> assertEquals(0, testUserFromLogin.getAmountOfFollowee()),
				() -> assertEquals(0, testUserFromLogin.getAmountOfFollowers()));
	}

	@Test
	public void TestShouldGetUserByEmail() {
		try {
			User testUser = microblogServiceImpl.registerUser("testowyLogin_2534534", "test22454@test.com",
					"passsss+sS9%sssssss", "Jan", "Kowalski", 'M', "1990-01-01");
			assertEquals(testUser.getEmail(), microblogServiceImpl.getUserByEmail(testUser.getEmail()).getEmail());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void TestShouldAddToken() {
		try {
			User testUser = microblogServiceImpl.registerUser("testowyLogin_2gjhgjh", "test2njjhghj@test.com",
					"passsss+sS9%sssssss", "Jan", "Kowalski", 'M', "1990-01-01");
			String token = "665666";
			User u = microblogServiceImpl.addToken(testUser, token);

			assertEquals(testUser.getId(), u.getId());
			assertEquals(u.getToken(), token);

		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void TestShouldGetUserByToken() {
		try {
			User testUser = microblogServiceImpl.registerUser("testowyLogin_2ih55ia", "test2gt5ghj@test.com",
					"passsss+sS9%sssssss", "Jan", "Kowalski", 'M', "1990-01-01");
			String token = "000400";
			User u = microblogServiceImpl.addToken(testUser, token);

			assertEquals(testUser.getId(), u.getId());
			assertEquals(u.getToken(), token);
			assertEquals(microblogServiceImpl.getUserByToken(token).getId(), testUser.getId());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void TestShuldValidateUser() {
		try {
			User testUser = microblogServiceImpl.registerUser("testowyLogin_545kskj4", "test5486gsg465@test.com",
					"passsss+sS9%sssssss", "Jan", "Kowalski", 'M', "1990-01-01");
			String token = "hggy55";
			User u = microblogServiceImpl.addToken(testUser, token);
			assertTrue(microblogServiceImpl.validateUser(u.getEmail(), u.getToken()));

		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	@Transactional
	public void TestShuldGenerateTokenForUser() {
		try {
			User testUser = microblogServiceImpl.registerUser("testowyLogin_2544jnga", "test24g3545nga@test.com",
					"passsss+sS9%sssssss", "Jan", "Kowalski", 'M', "1990-01-01");
			long time = 5000;
			String token = microblogServiceImpl.generateTokenForUser(testUser, time);

			User u = microblogServiceImpl.getUserById(testUser.getId());
			assertEquals(u.getToken(), token);
			assertNotNull(u.getTokenExpirationDate());

			try {
				Thread.sleep(time + 1000);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}

			assertNull(u.getToken());
			assertNull(u.getTokenExpirationDate());

		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void TestShouldLoginUser() {
		try {
			User testUser = microblogServiceImpl.registerUser("testowyLogin_jh989", "test98gg9@test.com",
					"passsss+sS9%sssssss", "Jan", "Kowalski", 'M', "1990-01-01");
			User loggedInUser = microblogServiceImpl.login(testUser.getEmail(), testUser.getPassword());

			assertEquals(loggedInUser.getId(), testUser.getId());
			assertTrue(loggedInUser.isLogged());
			assertEquals(loggedInUser.getStatus(), UserStatus.ACTIVE);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void TestShouldLogoutUser() throws ParseException {
		try {
			User testUser = microblogServiceImpl.registerUser("testowyLogin_55s", "test5s5@test.com",
					"passsss+sS9%sssssss", "Jan", "Kowalski", 'M', "1990-01-01");
			microblogServiceImpl.login(testUser.getEmail(), testUser.getPassword());
			assertFalse(testUser.isLogged());
			assertEquals(testUser.getStatus(), UserStatus.INVISIBLE);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void TestShouldChangeUserStatus() {
		try {
			User testUser = microblogServiceImpl.registerUser("testowyLogin_6a6", "test6a6@test.com",
					"passsss+sS9%sssssss", "Jan", "Kowalski", 'M', "1990-01-01");
			UserStatus newUserStatus = UserStatus.OCCUPIED;

			assertTrue(microblogServiceImpl.changeUserStatus(testUser, newUserStatus));
			assertEquals(testUser.getStatus(), newUserStatus);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void TestShouldChangeUserStatusNothingToChange() {
		try {
			User testUser = microblogServiceImpl.registerUser("testowyLogin_77aa", "test77aa@test.com",
					"passsss+sS9%sssssss", "Jan", "Kowalski", 'M', "1990-01-01");
			UserStatus currentStatus = testUser.getStatus();

			assertFalse(microblogServiceImpl.changeUserStatus(testUser, currentStatus));
			assertEquals(testUser.getStatus(), currentStatus);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void TestShouldChangeBasicUserDetails() {
		try {
			User u = microblogServiceImpl.registerUser("testowyLogin_8a8", "test8a8@test.com", "passsss+sS9%sssssss",
					"Jan", "Kowalski", 'M', "1990-01-01");
			microblogServiceImpl.changeBasicUserDetails(u, "testowyLogin_0a0", "Janusz", "Malinowski", "2000-01-01",
					'F');
			User testUser = microblogServiceImpl.getUserById(u.getId());

			assertEquals(testUser.getLogin(), "testowyLogin_0a0");
			assertEquals(testUser.getFirstName(), "Janusz");
			assertEquals(testUser.getLastName(), "Malinowski");
			assertEquals(testUser.getDob().getTime(), microblogServiceImpl.string2Date("2000-01-01").getTime());
			assertEquals(testUser.getGender(), 'F');
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void TestShouldChangeUserEmail() {
		try {
			User u = microblogServiceImpl.registerUser("testowyLogin_99w", "test99w@test.com", "passsss+sS9%sssssss",
					"Jan", "Kowalski", 'M', "1990-01-01");
			microblogServiceImpl.changeUserEmail(u, "newtest99@test.com");
			User testUser = microblogServiceImpl.getUserById(u.getId());
			assertEquals(testUser.getEmail(), "newtest99@test.com");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void TestShouldChangeUserPassword() {
		try {
			User u = microblogServiceImpl.registerUser("testowyLogin_909q", "test909q@test.com", "passsss+sS9%sssssss",
					"Jan", "Kowalski", 'M', "1990-01-01");
			microblogServiceImpl.changeUserPassword(u, u.getPassword(), "9NEWPa$$Word55", "9NEWPa$$Word55");
			User testUser = microblogServiceImpl.getUserById(u.getId());
			assertEquals(testUser.getPassword(), "9NEWPa$$Word55");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	/*
	 * @Test public void TestShouldGiveLike() { try { User testUser =
	 * microblogServiceImpl.registerUser("testowyLogin_3", "test3@test.com",
	 * "passssss%S9sssssss", "Jan", "Kowalski", 'M', "1990-01-01"); Integer postId =
	 * 192; Integer postId2 = 62;
	 *
	 * microblogServiceImpl.giveLike(testUser.getLogin(), postId); User
	 * testUserWithLikedPost = microblogServiceImpl.giveLike(testUser.getLogin(),
	 * postId2); ArrayList<String> arrayWithPostId = new ArrayList<String>();
	 * arrayWithPostId.add("192"); arrayWithPostId.add("62");
	 *
	 * assertEquals(arrayWithPostId, testUserWithLikedPost.getLikedPosts());
	 * assertEquals(2, testUserWithLikedPost.getLikedPosts().size()); } catch
	 * (ParseException e) { e.printStackTrace(); } }
	 */

	@Test
	public void TestLikePostByUser() throws ParseException {
		// when
		User testUser_1 = microblogServiceImpl.registerUser("testowyLogin_311", "test_311@test.com", "123pas%S456ss",
				"Jane", "Kowalski", 'F', "1990-01-01");
		Post testPost_1 = microblogServiceImpl.createNewPost(testUser_1, "CONTENT", true);
		User userAfterLike = microblogServiceImpl.likePostByUser(testUser_1.getLogin(), testPost_1.getId());
		String idFromUserLikedPost = userAfterLike.getLikedPosts().get(0);

		// then
		assertNotNull(idFromUserLikedPost);
		assertEquals(testPost_1.getId(), Integer.parseInt(idFromUserLikedPost) );
	}

	@Test
	public void TestUnLikePostByUser() throws ParseException {
		// when
		User testUser_1 = microblogServiceImpl.registerUser("testowyLogin_312", "test_312@test.com", "123pas%S456ss",
				"Jane", "Kowalski", 'F', "1990-01-01");
		Post testPost_1 = microblogServiceImpl.createNewPost(testUser_1, "CONTENT", true);
		
		microblogServiceImpl.likePostByUser(testUser_1.getLogin(), testPost_1.getId());
		User userAfterUnlikePost = microblogServiceImpl.unlikePostByUser(testUser_1.getLogin(), testPost_1.getId());
		
		//then
		assertEquals(0, userAfterUnlikePost.getLikedPosts().size());
	}

}
