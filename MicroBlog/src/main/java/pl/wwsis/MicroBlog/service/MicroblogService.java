package pl.wwsis.MicroBlog.service;

import pl.wwsis.MicroBlog.model.Post;
import pl.wwsis.MicroBlog.model.User;
import pl.wwsis.MicroBlog.model.UserStatus;
import pl.wwsis.MicroBlog.model.Follower;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;





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
	 * Delete all posts
	 */
	void deleteAllPosts();
	
	/**
	 * Create new post with content and isPublic flag
	 * @param user
	 * @param content
	 * @param isPublic
	 * @return Post
	 */
	Post createNewPost(User user, String content, Boolean isPublic);
	
	Post addCommentToPost(Integer postId, ArrayList<String> commments);
	
	Post addTagToPost (Integer postId, ArrayList<String> tags);
	
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
			String dob) throws ParseException;


	/**
	 * Liked post by user.
	 * @param login
	 * @param postId
	 * @return user
	 */
	User likePostByUser(String login, Integer postId);
	
	/**
	 * Unlike post.
	 * @param login
	 * @param postId
	 * @return user
	 */
	User unlikePostByUser(String login, Integer postId);

	
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

	/**
	 * Retrive list of user's followers (users who follows loged in user).
	 * @param user
	 * @return List<follower>
	 */
	List<Follower> getListOfUsersFollowers (User user);


	Date string2Date (String dateAsString) throws ParseException;
	
	User getUserById(int userId);
	
	User getUserByEmail(String email);
	
	User getUserByToken(String token);
		
	User login(String email, String password);
	
	boolean logout(int userId);
	
	boolean changeUserStatus(User user, UserStatus status);
	
	void changeBasicUserDetails(User user, String login, String firstName, String lastName, String dob, Character gender) throws ParseException;
	
	boolean changeUserEmail (User user, String newEmail);
	
	boolean changeUserPassword (User user, String password, String newPassword, String repeatedPassword);
	
	String generateTokenForUser(User user, long expiration);
	
	boolean validateUser(String email, String token);
		
	boolean sendEmail (String email, String content);
	
	User giveLike(String login, Integer postId);

    User giveUnLike(String login, Integer postId);
    
    User addToken(User user,String token);


}
