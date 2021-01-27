package pl.wwsis.MicroBlog;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import pl.wwsis.MicroBlog.dao.impl.FollowerDaoImpl;
import pl.wwsis.MicroBlog.dao.impl.PostDaoImpl;
import pl.wwsis.MicroBlog.dao.impl.UserDaoImpl;
import pl.wwsis.MicroBlog.model.Follower;
import pl.wwsis.MicroBlog.model.Post;
import pl.wwsis.MicroBlog.model.User;

@RunWith(SpringRunner.class)

@SpringBootTest(classes = pl.wwsis.MicroBlog.App.class)

public class AppTest {

	@Autowired
	private UserDaoImpl userDaoImpl;
	@Autowired
	private FollowerDaoImpl followerDaoImpl;
	@Autowired
	private PostDaoImpl postDaoImpl;

//	@Before
//	public void setUp() {
//		
//	}

	@Test

	public void shouldRegisterAndGetUserByLogin() {
		User u = null;

		try {
			u = userDaoImpl.registerUser("testowyLogin", "test@test.com", "passsssw%S9", "Jan", "Kowalski", 'M', "1990-01-01");
		} catch (ParseException e) {

			e.printStackTrace();
		}

		assertNotNull(userDaoImpl.getUserByLogin(u.getLogin()));
	}

	@Test

	public void getTimelineOfUser() {
		User u = null;

		try {
			u = userDaoImpl.registerUser("testowyLogin2", "test2@test2.com", "passssssS9ssss%sss", "Jan", "Kowalski", 'M',
					"1990-01-01");

		} catch (ParseException e) {

			e.printStackTrace();
		}
		System.out.println(u.getLogin());
		System.out.println(u.getId());
		assertTrue(postDaoImpl.getTimelineOfUser(u).isEmpty());

	}

	@Test
	public void addPostOfUser() {
		User u = null;
		Post p = null;

		try {
			u = userDaoImpl.registerUser("testowyLogin3", "test3@test3.com", "passssssS9sss%ssss", "Jan", "Kowalski", 'M',
					"1990-01-01");
			p = postDaoImpl.addPostOfUser(u, "testowyPost", true);

		} catch (ParseException e) {

			e.printStackTrace();
		}

		assertEquals(1, postDaoImpl.getTimelineOfUser(u).size());

	}

	@Test
	public void getFullTimelineOfUser() {
		User u1 = null;
		User u2 = null;

		List<Post> postListTest = new ArrayList<>();

		try {
			u1 = userDaoImpl.registerUser("testowyUser23", "test4@test4.com", "passssssS9sss%ssss", "Jan", "Kowalski", 'M',
					"1990-01-01");
			u2 = userDaoImpl.registerUser("testowyFollowee23", "test5@test5.com", "passssssS9s%ssssss", "Jan", "Kowalski", 'M',
					"1990-01-01");
			followerDaoImpl.addFolloweeOfUser(u1, u2);

			Post p1 = postDaoImpl.addPostOfUser(u1, "testowyPost1", true);
			Post p2 = postDaoImpl.addPostOfUser(u2, "testowyPost2", true);
			System.out.println("u1: " + u1.getId());
			System.out.println("u2: " + u2.getId());
			postListTest.add(p1);
			postListTest.add(p2);
		} catch (ParseException e) {

			e.printStackTrace();
		}

		assertEquals(postListTest.get(0).getContent(), postDaoImpl.getFullTimelineOfUser(u1).get(0).getContent());
		assertEquals(postListTest.get(1).getContent(), postDaoImpl.getFullTimelineOfUser(u1).get(1).getContent());

	}

	@Test
	public void getAllPublicPosts() {
		postDaoImpl.deleteAllPosts();

		assertTrue(postDaoImpl.getFullPublicTimeline().isEmpty());

		User u1 = null;
		User u2 = null;

		List<Post> postListTest = new ArrayList<>();

		try {
			u1 = userDaoImpl.registerUser("testowyUser6", "test6@test6.com", "passssssS9s%ssssss", "Jan", "Kowalski", 'M',
					"1990-01-01");
			u2 = userDaoImpl.registerUser("testowyUser7", "test7@test7.com", "passssssS9ss%sssss", "Jan", "Kowalski", 'M',
					"1990-01-01");
			followerDaoImpl.addFolloweeOfUser(u1, u2);

			Post p1 = postDaoImpl.addPostOfUser(u1, "testowyPost3", true);
			Post p2 = postDaoImpl.addPostOfUser(u2, "testowyPost4", true);
			Post p3 = postDaoImpl.addPostOfUser(u2, "testowyPost5", false);

			postListTest.add(p1);
			postListTest.add(p2);
		} catch (ParseException e) {

			e.printStackTrace();
		}
	

		assertEquals(postListTest.size(), postDaoImpl.getFullPublicTimeline().size());

	}

	@Test
	public void addFollowerOfUser() {
		User u1 = null;
		User u2 = null;
		Follower f = null;

		try {
			u1 = userDaoImpl.registerUser("testowyUser8", "test8@test8.com", "passssssS9ssss%sss", "Jan", "Kowalski", 'M',
					"1990-01-01");
			u2 = userDaoImpl.registerUser("testowyFollowee9", "test9@test9.com", "passssssS9ss%sssss", "Jan", "Kowalski", 'M',
					"1990-01-01");
			f = followerDaoImpl.addFolloweeOfUser(u1, u2);

		} catch (ParseException e) {

			e.printStackTrace();
		}

		assertEquals(u1.getId(), f.getId());
		assertEquals(u2.getId(), f.getFollowsUserId());
		assertEquals(f.getId(), followerDaoImpl.findFolloweeOfUser(u1, u2).getId());
		assertEquals(f.getFollowsUserId(), followerDaoImpl.findFolloweeOfUser(u1, u2).getFollowsUserId());
	}

	@Test
	public void deleteFollowerOfUser() {
		User u1 = null;
		User u2 = null;
		Follower f = null;

		try {
			u1 = userDaoImpl.registerUser("testowyUser10", "test10@test10.com", "passssssS9ssss%sss", "Jan", "Kowalski", 'M',
					"1990-01-01");
			u2 = userDaoImpl.registerUser("testowyFollowee11", "test11@test11.com", "passssssS9s%ssssss", "Jan", "Kowalski", 'M',
					"1990-01-01");
			f = followerDaoImpl.addFolloweeOfUser(u1, u2);

		} catch (ParseException e) {

			e.printStackTrace();
		}

		assertEquals(u1.getId(), f.getId());
		assertEquals(u2.getId(), f.getFollowsUserId());
		assertEquals(f.getId(), followerDaoImpl.findFolloweeOfUser(u1, u2).getId());
		assertEquals(f.getFollowsUserId(), followerDaoImpl.findFolloweeOfUser(u1, u2).getFollowsUserId());
		Follower f2 = followerDaoImpl.deleteFolloweeOfUser(u1, u2);
		assertEquals(f.getId(), f2.getId());
		assertNull(followerDaoImpl.findFolloweeOfUser(u1, u2));

	}

}
