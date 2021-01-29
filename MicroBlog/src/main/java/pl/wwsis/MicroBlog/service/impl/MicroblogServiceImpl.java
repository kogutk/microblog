package pl.wwsis.MicroBlog.service.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.wwsis.MicroBlog.dao.impl.FollowerDaoImpl;
import pl.wwsis.MicroBlog.dao.impl.PostDaoImpl;
import pl.wwsis.MicroBlog.dao.impl.UserDaoImpl;
import pl.wwsis.MicroBlog.model.Follower;
import pl.wwsis.MicroBlog.model.Post;
import pl.wwsis.MicroBlog.model.User;
import pl.wwsis.MicroBlog.model.UserStatus;
import pl.wwsis.MicroBlog.service.MicroblogService;

@Service
public class MicroblogServiceImpl implements MicroblogService {

	@Autowired
	private UserDaoImpl userDaoImpl;
	@Autowired
	private FollowerDaoImpl followerDaoImpl;
	@Autowired
	private PostDaoImpl postDaoImpl;

	@Override
	public List<Post> getAllPostsFromSpecificUser(User user) {
		List<Post> postsList = postDaoImpl.getTimelineOfUser(user);
		return postsList;
	}

	@Override
	public List<Post> getAllPostsFromSpecificUserAndHisFollowers(User user) {
		List<Post> postsList = postDaoImpl.getFullTimelineOfUser(user);
		return postsList;
	}

	@Override
	public List<Post> getAllPosts() {
		List<Post> postsList = postDaoImpl.getFullPublicTimeline();
		return postsList;
	}

	@Override
	public void deleteAllPosts() {
		postDaoImpl.deleteAllPosts();
	}

	@Override
	public Post createNewPost(User user, String content, Boolean isPublic) {
		Post newPost = postDaoImpl.addPostOfUser(user, content, isPublic);
		return newPost;
	}
	
	@Override
	public Post addCommentToPost(Integer postId, ArrayList<String> comments) {
		return postDaoImpl.addCommentToPost(postId, comments);
	}
	
	@Override
	public Post addTagToPost(Integer postId, ArrayList<String> tags) {
		return postDaoImpl.addTagToPost(postId, tags);
	}

	@Override
	public User getUserByLogin(String login) {
		User user = userDaoImpl.getUserByLogin(login);
		return user;
	}

	@Override
	public User registerUser(String name, String email, String password, String firstName, String lastName,
			Character gender, String dob) throws ParseException {
		User user = null;
		try {
			user = userDaoImpl.registerUser(name, email, password, firstName, lastName, gender, dob);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public Follower addFolloweeOfUser(User user, User followee) {
		Follower newFollower = followerDaoImpl.addFolloweeOfUser(user, followee);
		return newFollower;
	}

	@Override
	public Follower findFolloweeOfUser(User user, User followee) {
		Follower findFollower = followerDaoImpl.findFolloweeOfUser(user, followee);
		return findFollower;
	}

	@Override
	public Follower deleteFolloweeOfUser(User user, User followee) {
		Follower deletedFollower = followerDaoImpl.deleteFolloweeOfUser(user, followee);
		return deletedFollower;
	}

	@Override
	public List<Follower> getListOfUsersFollowers(User user) {
		List<Follower> followers = followerDaoImpl.getListOfFollowers(user);
		return followers;
	}

	@Override

	public Date string2Date(String dateAsString) throws ParseException {
		Date string2Date = userDaoImpl.string2Date(dateAsString);
		return string2Date;
	}

	@Override
	public User getUserById(int userId) {
		User user = userDaoImpl.getUserById(userId);
		return user;
	}

	@Override
	public User getUserByEmail(String email) {
		User user = userDaoImpl.getUserByEmail(email);
		return user;
	}

	@Override
	public User getUserByToken(String token) {
		User user = null;
		try {
			user = userDaoImpl.getUserByToken(token);
		} catch (NoResultException e) {

		}
		return user;
	}

	@Override
	public User login(String email, String password) {
		User user = userDaoImpl.login(email, password);
		return user;
	}

	@Override
	public boolean logout(int userId) {
		boolean logOut = userDaoImpl.logout(userId);
		return logOut;
	}

	@Override
	public boolean changeUserStatus(User user, UserStatus status) {
		boolean changeUserStatus = userDaoImpl.changeUserStatus(user, status);
		return changeUserStatus;
	}

	@Override
	public void changeBasicUserDetails(User u, String login, String firstName, String lastName, String dob,
			Character gender) throws ParseException {
		userDaoImpl.changeBasicUserDetails(u, login, firstName, lastName, dob, gender);
	}

	@Override
	public boolean sendEmail(String email, String content) {
		boolean sendEmail = userDaoImpl.sendEmail(email, content);
		return sendEmail;
	}

	@Override
	public boolean changeUserEmail(User u, String newEmail) {
		boolean changeUserEmail = userDaoImpl.changeUserEmail(u, newEmail);
		return changeUserEmail;
	}

	@Override
	public boolean changeUserPassword(User u, String password, String newPassword, String repeatedPassword) {
		boolean changeUserPassword = userDaoImpl.changeUserPassword(u, password, newPassword, repeatedPassword);
		return changeUserPassword;
	}

	@Override
	public String generateTokenForUser(User u, long expirationTime) {
		String token = userDaoImpl.generateTokenForUser(u, expirationTime);
		return token;
	}

	public boolean deleteTokenExpired(User u, long expirationTime) {
		boolean deleted = userDaoImpl.deleteTokenExpired(u, expirationTime);
		return deleted;
	}

	@Override
	public boolean validateUser(String email, String token) {
		boolean validatedUser = userDaoImpl.validateUser(email, token);
		return validatedUser;
	}

	@Override
	public User giveLike(String login, Integer postId) {
		User user = userDaoImpl.giveLike(login, postId);
		return user;
	}

	@Override
	public User giveUnLike(String login, Integer postId) {
		User user = userDaoImpl.giveUnLike(login, postId);
		return user;
	}

	@Override
	public User addToken(User user, String token) {
		User u = userDaoImpl.addToken(user, token);
		return u;

	}


	public User likePostByUser(String login, Integer postId) {
		User user = userDaoImpl.giveLike(login, postId);
		postDaoImpl.getLikedByUser(postId);
		return user;
	}

	@Override
	public User unlikePostByUser(String login, Integer postId) {
		User user = userDaoImpl.giveUnLike(login, postId);
		postDaoImpl.getLikedByUser(postId);
		return user;
	}
}

