package pl.wwsis.MicroBlog.servicE;

import pl.wwsis.MicroBlog.model.Post;
import pl.wwsis.MicroBlog.model.User;
import pl.wwsis.MicroBlog.model.Follower;

import java.util.List;




public interface MicroblogService {

	/**
	 * Retrieve all posts for specific user
	 * 
	 *@param user
	 *@return List<Post>
	 *
	 */
	List<Post> getAllPostsFromSpecificUser(User user);
	
	/**
	 * Retrieve all posts for specific user and users that
	 * I follow
	 * 
	 * @param user
	 * @return List<Post>
	 */
	List<Post> getAllPostsFromSpecificUserAndHisFollowers(User user);
	
	/**
	 * Retrieve all posts from all users
	 * @return List<Post>
	 */
	List<Post> getAllPosts();
	
	/**
	 * Create new post with content and isPublic flag
	 * @param user
	 * @param content
	 * @param isPublic
	 * @return Post
	 */
	Post createNewPost(User user, String content, Boolean isPublic);
	
	/**
	 * Get user by login
	 * @param login
	 * @return user
	 */
	User getUserByLogin(String login);
	
	/**
	 * Register new user
	 * @param name
	 * @param email
	 * @param password
	 * @param firstName
	 * @param gender
	 * @param dob
	 * @return user
	 */
	User registerUser(
			String name, String email,
			String password, String firstName,
			String lastName, Character gender,
			String dob);
	
	/**
	 * Add user to being tracked by the current logged user.
	 * @param user
	 * @param followee
	 * @return follower
	 */
	Follower addFolloweeOfUser(User user, User followee);
	
	/**
	 * Check if another user is on my tracked list
	 * @param user
	 * @param followee
	 * @return follower
	 */
	Follower findFolloweeOfUser(User user, User followee);

	/**
	 * Delete specific followee from the current logged user
	 * @param user
	 * @param followee
	 * @return follower
	 */
	Follower deleteFolloweeOfUser(User user, User followee);











}
