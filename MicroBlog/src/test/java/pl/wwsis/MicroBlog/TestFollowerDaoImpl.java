package pl.wwsis.MicroBlog;

import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.wwsis.MicroBlog.dao.impl.FollowerDaoImpl;
import pl.wwsis.MicroBlog.dao.impl.UserDaoImpl;
import pl.wwsis.MicroBlog.model.Follower;
import pl.wwsis.MicroBlog.model.User;

@RunWith(SpringRunner.class)

@SpringBootTest(classes = pl.wwsis.MicroBlog.App.class)

public class TestFollowerDaoImpl {
	
	@Autowired
	FollowerDaoImpl followerDaoImpl;
	
	@Autowired
	UserDaoImpl userDaoImpl;
	
	@Test
	public void TestShouldAddFolloweeOfUser() {
		try {
			
			User testUser_1 = userDaoImpl.registerUser("testowyLogin_11", "test_11@test.com", "123pasS456%ss", "Jan", "Kowalski", 'F', "1990-01-01");
			User testUser_2 = userDaoImpl.registerUser("testowyLogin_12", "test_12@test.com", "123pasS456%ss", "Jan", "Kowalski", 'M', "1990-01-01");
			
			Follower follower = followerDaoImpl.addFolloweeOfUser(testUser_1, testUser_2);
			assertEquals(follower.getFollowsUserId(), testUser_2.getId());
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}
	
	@Test
	public void TestShouldFindFolloweeOfUser() {
		try {
		User testUser_1 = userDaoImpl.registerUser("testowyLogin_13", "test13@test.com", "123pasS456%ss", "Jan", "Kowalski", 'M', "1990-01-01");
		User testUser_2 = userDaoImpl.registerUser("testowyLogin_14", "test14@test.com", "123pasS456%ss", "Jan", "Kowalski", 'M', "1990-01-01");
		
		Follower follower = followerDaoImpl.addFolloweeOfUser(testUser_1, testUser_2);
		assertEquals(follower.getFollowsUserId(), testUser_2.getId());
		followerDaoImpl.findFolloweeOfUser(testUser_1, testUser_2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void TestShouldDeleteFolloweeOfUser() {
		try {
			User testUser_1 = userDaoImpl.registerUser("testowyLogin_15", "test15@test.com", "123pasS456%ss", "Jan", "Kowalski", 'M', "1990-01-01");
			User testUser_2 = userDaoImpl.registerUser("testowyLogin_16", "test16@test.com", "123pasS456%ss", "Jan", "Kowalski", 'M', "1990-01-01");
			
			Follower follower = followerDaoImpl.addFolloweeOfUser(testUser_1, testUser_2);
			assertEquals(follower.getFollowsUserId(), testUser_2.getId());
			Follower removedFollower = followerDaoImpl.deleteFolloweeOfUser(testUser_1, testUser_2);
			assertEquals(removedFollower.getFollowsUserId(), testUser_2.getId());
			assertNotEquals(removedFollower.getFollowsUserId(), testUser_1.getId());
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void TestShouldGetListOfUsersFollowers(){
		List<Follower> followers = new ArrayList<>();
		try {

			User testUser_1 = userDaoImpl.registerUser("testowyLogin_211", "test_211@test.com", "123pa%sS456ss", "Jane", "Kowalski", 'F', "1990-01-01");
			User testUser_2 = userDaoImpl.registerUser("testowyLogin_221", "test_221@test.com", "123pa%sS456ss", "Jan", "Kowalski", 'M', "1990-01-01");
			User testUser_3 = userDaoImpl.registerUser("testowyLogin_231", "test_231@test.com", "123pa%sS456ss", "Jan", "Kowalski", 'M', "1990-01-01");
			User testUser_4 = userDaoImpl.registerUser("testowyLogin_241", "test_241@test.com", "123pa%sS456ss", "Jan", "Kowalski", 'M', "1990-01-01");


			followers.add(followerDaoImpl.addFolloweeOfUser(testUser_2, testUser_1));
			followers.add(followerDaoImpl.addFolloweeOfUser(testUser_3, testUser_1));
			followers.add(followerDaoImpl.addFolloweeOfUser(testUser_4, testUser_1));

			List<Follower> followers1 = followerDaoImpl.getListOfFollowers(testUser_1);

			for (int i = 0;i<followers.size();i++) {
				assertEquals(followers.get(i).getId(), followers1.get(i).getId());
				assertEquals(followers.get(i).getFollowsUserId(), followers1.get(i).getFollowsUserId());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
