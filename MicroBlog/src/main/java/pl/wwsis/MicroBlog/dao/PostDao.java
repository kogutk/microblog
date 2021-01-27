
package pl.wwsis.MicroBlog.dao;

import java.util.List;
import java.util.ArrayList;

import pl.wwsis.MicroBlog.model.Post;
import pl.wwsis.MicroBlog.model.User;


public interface PostDao {
	
	/** Method that return one Post by id */
	Post getPostById(Integer postId);
	
	/** Like Post by User */
	Post getLikedByUser(Integer postId);
	
	/** Unlike Post by User */
	Post getUnLikedByUser(Integer postId);
	
	/** Method that retrieves all messages for a selected user (user's Timeline) */	
	List<Post> getTimelineOfUser(User user);
	
	/** Method that downloads all my messages (published by me) and all messages of other users that I follow (it is displaying user's Full Timeline)  */	
	List<Post> getFullTimelineOfUser(User user);
	
	/** Method that retrieves all messages from all users (it is displaying user's Full Public Timeline)  */
	List<Post> getFullPublicTimeline();
	
	/** Method that adds a message to the user */
	Post addPostOfUser(User user, String contentOfThePost , Boolean isPublic);
	
	Post addCommentToPost(int postId, ArrayList<String> comments);
	
	Post addTagToPost(int postId, ArrayList<String> tags);
	
}