package pl.wwsis.MicroBlog;

import static org.junit.Assert.assertTrue;

import java.text.ParseException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import pl.wwsis.MicroBlog.dao.impl.FollowerDaoImpl;
import pl.wwsis.MicroBlog.dao.impl.PostDaoImpl;
import pl.wwsis.MicroBlog.dao.impl.UserDaoImpl;
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

	@Test
	public void shouldAnswerWithTrue() {
		System.out.println("test");
		assertTrue(true);
	}
	
	@Test
	public void shouldGiveUser() {
//		registerUser(String name, String email, String password, String firstName, String lastName,
//		Character gender, String dob)
		
		User u = null;
		try {
			u = userDaoImpl.registerUser("testowyLogin","test@test.com", "pass", "Jan", "Kowalski", 'M', "1990-01-01");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("test");
		
		System.out.println(u.getLogin());
		assertTrue(true);
	}
}
