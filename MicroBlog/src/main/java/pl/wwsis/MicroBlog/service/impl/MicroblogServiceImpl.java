package pl.wwsis.MicroBlog.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.wwsis.MicroBlog.dao.impl.FollowerDaoImpl;
import pl.wwsis.MicroBlog.dao.impl.PostDaoImpl;
import pl.wwsis.MicroBlog.dao.impl.UserDaoImpl;
import pl.wwsis.MicroBlog.model.Follower;
import pl.wwsis.MicroBlog.model.Post;
import pl.wwsis.MicroBlog.model.User;
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
			Character gender, String dob) {
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
}
