package pl.wwsis.MicroBlog.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import pl.wwsis.MicroBlog.dao.PostDao;
import pl.wwsis.MicroBlog.model.Follower;
import pl.wwsis.MicroBlog.model.Post;
import pl.wwsis.MicroBlog.model.User;

@Transactional
@Component
public class PostDaoImpl implements PostDao {

	@PersistenceContext
	EntityManager entityManager;


	public Post getPostById(Integer postId) {
		String queryString = "SELECT p FROM Post p WHERE p.id = :postId";
		Query query = entityManager.createQuery(queryString);
		query.setParameter("postId", postId);
		Post post = (Post) query.getSingleResult();
		return post;
	}

	@SuppressWarnings("unchecked")
	@Override
	/** Method that retrieves all messages for a selected user (user's Timeline) */
	public List<Post> getTimelineOfUser(User user) {
		int userId = user.getId();
		String queryString = "SELECT p FROM Post p WHERE p.authorId=:id";
		Query query = entityManager.createQuery(queryString);
		query.setParameter("id", userId);

		List<Post> postList = null;
		try {
		postList = (List<Post>) query.getResultList();
		}
		catch (Exception e) {e.printStackTrace();}
		System.out.println(postList!=null);
		return postList;

	}
	@SuppressWarnings("unchecked")
	@Override
	/**
	 * Method that downloads all my messages (published by me) and all messages of
	 * other users that I follow (it is displaying user's Full Timeline)
	 */

       public List<Post> getFullTimelineOfUser(User user) {
		int userId = user.getId();


		String queryString = "SELECT p FROM Post p WHERE p.authorId=:id OR p.authorId IN (SELECT followsUserId FROM Follower f WHERE f.id=:id)";
		Query query = entityManager.createQuery(queryString);
		query.setParameter("id", userId);


		List<Post> postList =null;
		try {
		postList = (List<Post>) query.getResultList();


		}
		catch (Exception e) {e.printStackTrace();}
		return postList;

	}

	@Override
	/**
	 * Method that retrieves all messages from all users (it is displaying user's
	 * Full Public Timeline)
	 */
	public List<Post> getFullPublicTimeline() {
		String queryString = "SELECT p FROM Post p WHERE p.isPublic=1";
		Query query = entityManager.createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<Post> postList = (List<Post>) query.getResultList();
		return postList;

	}


	/**
	 * Method that retrieves all messages from all users (it is displaying user's
	 * Full Public Timeline)
	 */
	public void deleteAllPosts() {

		String queryString = "DELETE FROM Post";

		Query query = entityManager.createQuery(queryString);

		query.executeUpdate();


	}

	@Override

	/** Method that adds a message to the user */
	public Post addPostOfUser(User user, String contentOfThePost, Boolean isPublic) {

		int userId = user.getId();

		//entityManager.getTransaction().begin();
		Post post = new Post(userId, contentOfThePost, isPublic);
		entityManager.persist(post);
		//entityManager.getTransaction().commit();
		return post;

	}

	@Override
	public Post getLikedByUser(Integer postId) {
		Post post = this.getPostById(postId);

		post.setAmountOfLike(post.getAmountOfLike() + 1);
		entityManager.merge(post);
		return post;
	}

	@Override
	public Post getUnLikedByUser(Integer postId) {
		Post post = this.getPostById(postId);

		post.setAmountOfLike(post.getAmountOfLike() - 1);
		entityManager.merge(post);
		return post;
	}


	@Override
	public Post addCommentToPost(int postId, ArrayList<String> comments) {
		Post post = this.getPostById(postId);
		post.setComments(comments);
		entityManager.merge(post);
		return post;
	}

	@Override
	public Post addTagToPost(int postId, ArrayList<String> tags) {
		Post post = this.getPostById(postId);
		post.setTags(tags);
		entityManager.merge(post);
		return post;
	}
}
