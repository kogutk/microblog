package pl.wwsis.MicroBlog.dao;

import pl.wwsis.MicroBlog.model.Follower;
import pl.wwsis.MicroBlog.model.User;

import java.util.List;


public interface FollowerDao {
	
	//I follow FOLLOWWE, FOLLOWER follows me
	
	/** Adding another user to being tracked by the currently logged in user (A pair of "follower" and "followee" is created) */
	Follower addFolloweeOfUser(User user, User followee);
	
	/** Checking if another user is on my tracked list (Reading the "follower" and "followee" pairs) */
	Follower findFolloweeOfUser(User user, User followee);

	/** Removing another user from those currently logged in by the user (Removing the "follower" and "followee" pairs) */
	Follower deleteFolloweeOfUser(User user, User followee); //alt as parameter Follower

	/** Providing list of user's followers */
	List<Follower> getListOfFollowers (User user);

}
