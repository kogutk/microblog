package pl.wwsis.MicroBlog;

import static org.junit.jupiter.api.Assertions.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.transaction.Transactional;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import pl.wwsis.MicroBlog.dao.impl.UserDaoImpl;
import pl.wwsis.MicroBlog.model.User;
import pl.wwsis.MicroBlog.model.UserStatus;

@RunWith(SpringRunner.class)

@SpringBootTest(classes = pl.wwsis.MicroBlog.App.class)
public class TestUserDaoImpl {

	@Autowired
	UserDaoImpl userDaoImpl;

	private ArrayList<String> arrayWithPostId;

	@Before
	public void setUp() {
		arrayWithPostId = new ArrayList<String>();
	}

	@AfterEach
	public void tearDown() {
		arrayWithPostId = null;
	}

	@Test
	public void TestString2Date() {
		try {
			Date date = Calendar.getInstance().getTime();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String strDate = dateFormat.format(date);
			Date dateAsString2Date = userDaoImpl.string2Date(strDate);

			assertEquals(dateFormat.format(date), dateFormat.format(dateAsString2Date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void TestRegisterUser() {
		try {
			User testUser = userDaoImpl.registerUser("testowyLogin_1", "test1@test.com", "passSssss?sw9", "Jan",
					"Kowalski", 'M', "1990-01-01");
			assertEquals(testUser.getLogin(), "testowyLogin_1");
			assertFalse(testUser.isLogged());
			assertEquals(testUser.getStatus(), UserStatus.INVISIBLE);
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void TestGetUserById() {
		try {
			User testUser = userDaoImpl.registerUser("testowyLogin0", "test0@test.com", "passsss+sS9%sssssss", "Jan",
					"Kowalski", 'M', "1990-01-01");
			assertEquals(testUser.getId(), userDaoImpl.getUserById(testUser.getId()).getId());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void TestGetUserByLogin() {
		try {
			User testUser = userDaoImpl.registerUser("testowyLogin_2", "test2@test.com", "passs%sssS9sssssss", "Jan",
					"Kowalski", 'M', "1990-01-01");
			assertEquals(testUser.getLogin(), userDaoImpl.getUserByLogin(testUser.getLogin()).getLogin());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void TestGetUserByEmail() {
		try {
			User testUser = userDaoImpl.registerUser("testowyLogin_2a", "test2a@test.com", "passsss+sS9%sssssss", "Jan",
					"Kowalski", 'M', "1990-01-01");
			assertEquals(testUser.getEmail(), userDaoImpl.getUserByEmail(testUser.getEmail()).getEmail());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void TestaddToken() {
		try {
			User testUser = userDaoImpl.registerUser("testowyLogin_2nga", "test2nga@test.com", "passsss+sS9%sssssss",
					"Jan", "Kowalski", 'M', "1990-01-01");
			String token = "666666";
			User u = userDaoImpl.addToken(testUser, token);

			assertEquals(testUser.getId(), u.getId());
			assertEquals(u.getToken(), token);

		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void TestGetUserByToken() {
		try {
			User testUser = userDaoImpl.registerUser("testowyLogin_2ihuia", "test2gtfa@test.com", "passsss+sS9%sssssss",
					"Jan", "Kowalski", 'M', "1990-01-01");
			String token = "000000";
			User u = userDaoImpl.addToken(testUser, token);

			assertEquals(testUser.getId(), u.getId());
			assertEquals(u.getToken(), token);
			assertEquals(userDaoImpl.getUserByToken(token).getId(), testUser.getId());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void validateUser() {
		try {
			User testUser = userDaoImpl.registerUser("testowyLogin_5454", "test5486465@test.com", "passsss+sS9%sssssss",
					"Jan", "Kowalski", 'M', "1990-01-01");
			String token = "hggy57";
			User u = userDaoImpl.addToken(testUser, token);
			assertTrue(userDaoImpl.validateUser(u.getEmail(), u.getToken()));

		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	@Transactional
	public void generateTokenForUser() {
		try {
			User testUser = userDaoImpl.registerUser("testowyLogin_2544nga", "test243545nga@test.com",
					"passsss+sS9%sssssss", "Jan", "Kowalski", 'M', "1990-01-01");
			long time = 5000;
			String token = userDaoImpl.generateTokenForUser(testUser, time);

			User u = userDaoImpl.getUserById(testUser.getId());
			assertEquals(u.getToken(), token);
			assertNotNull(u.getTokenExpirationDate());

			try {
				Thread.sleep(time + 1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			assertNull(u.getToken());
			assertNull(u.getTokenExpirationDate());

		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void TestLogin() {
		try {
			User testUser = userDaoImpl.registerUser("testowyLogin_989", "test989@test.com", "passsss+sS9%sssssss",
					"Jan", "Kowalski", 'M', "1990-01-01");
			User loggedInUser = userDaoImpl.login(testUser.getEmail(), testUser.getPassword());

			assertEquals(loggedInUser.getId(), testUser.getId());
			assertTrue(loggedInUser.isLogged());
			assertEquals(loggedInUser.getStatus(), UserStatus.ACTIVE);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void TestLogout() throws ParseException {
		try {
			User testUser = userDaoImpl.registerUser("testowyLogin_55", "test55@test.com", "passsss+sS9%sssssss", "Jan",
					"Kowalski", 'M', "1990-01-01");
			userDaoImpl.login(testUser.getEmail(), testUser.getPassword());
			assertFalse(testUser.isLogged());
			assertEquals(testUser.getStatus(), UserStatus.INVISIBLE);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void TestChangeUserStatus() {
		try {
			User testUser = userDaoImpl.registerUser("testowyLogin_66", "test66@test.com", "passsss+sS9%sssssss", "Jan",
					"Kowalski", 'M', "1990-01-01");
			UserStatus newUserStatus = UserStatus.OCCUPIED;

			assertTrue(userDaoImpl.changeUserStatus(testUser, newUserStatus));
			assertEquals(testUser.getStatus(), newUserStatus);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void TestChangeUserStatusNothingToChange() {
		try {
			User testUser = userDaoImpl.registerUser("testowyLogin_77", "test77@test.com", "passsss+sS9%sssssss", "Jan",
					"Kowalski", 'M', "1990-01-01");
			UserStatus currentStatus = testUser.getStatus();

			assertFalse(userDaoImpl.changeUserStatus(testUser, currentStatus));
			assertEquals(testUser.getStatus(), currentStatus);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void TestChangeBasicUserDetails() {
		try {
			User u = userDaoImpl.registerUser("testowyLogin_88", "test88@test.com", "passsss+sS9%sssssss", "Jan",
					"Kowalski", 'M', "1990-01-01");
			userDaoImpl.changeBasicUserDetails(u, "testowyLogin_00", "Janusz", "Malinowski", "2000-01-01", 'F');
			User testUser = userDaoImpl.getUserById(u.getId());

			assertEquals(testUser.getLogin(), "testowyLogin_00");
			assertEquals(testUser.getFirstName(), "Janusz");
			assertEquals(testUser.getLastName(), "Malinowski");
			assertEquals(testUser.getDob().getTime(), userDaoImpl.string2Date("2000-01-01").getTime());
			assertEquals(testUser.getGender(), 'F');
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void TestChangeUserEmail() {
		try {
			User u = userDaoImpl.registerUser("testowyLogin_99", "test99@test.com", "passsss+sS9%sssssss", "Jan",
					"Kowalski", 'M', "1990-01-01");
			userDaoImpl.changeUserEmail(u, "newtest99@test.com");
			User testUser = userDaoImpl.getUserById(u.getId());
			assertEquals(testUser.getEmail(), "newtest99@test.com");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void TestChangeUserPassword() {
		try {
			User u = userDaoImpl.registerUser("testowyLogin_909", "test909@test.com", "passsss+sS9%sssssss", "Jan",
					"Kowalski", 'M', "1990-01-01");
			userDaoImpl.changeUserPassword(u, u.getPassword(), "9NEWPa$$Word55", "9NEWPa$$Word55");
			User testUser = userDaoImpl.getUserById(u.getId());
			assertEquals(testUser.getPassword(), "9NEWPa$$Word55");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void TestGiveLike() {
		try {
			User testUser = userDaoImpl.registerUser("testowyLogin_3", "test3@test.com", "passssss%S9sssssss", "Jan",
					"Kowalski", 'M', "1990-01-01");
			Integer postId = 192;
			Integer postId2 = 62;

			userDaoImpl.giveLike(testUser.getLogin(), postId);
			User testUserWithLikedPost = userDaoImpl.giveLike(testUser.getLogin(), postId2);

			arrayWithPostId.add("192");
			arrayWithPostId.add("62");

			assertEquals(arrayWithPostId, testUserWithLikedPost.getLikedPosts());
			assertEquals(2, testUserWithLikedPost.getLikedPosts().size());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}



}
