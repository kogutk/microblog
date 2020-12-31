package pl.wwsis.MicroBlog.dao;

import java.util.List;

import pl.wwsis.MicroBlog.model.Post;
import pl.wwsis.MicroBlog.model.User;

public interface PostDao {
	
	
	/** Method that retrieves all messages for a selected user (user's Timeline) */	
	List<Post> getTimelineOfUser(User user);
	
	
	/** Method that downloads all my messages (published by me) and all messages of other users that I follow (it is displaying user's Full Timeline)  */	
	List<Post> getFullTimelineOfUser(User user);
	
	/** Method that retrieves all messages from all users (it is displaying user's Full Public Timeline)  */
	List<Post> getFullPublicTimeline();
	
	/** Method that adds a message to the user */
	Post addPostOfUser(User user, String contentOfThePost ); 

}
