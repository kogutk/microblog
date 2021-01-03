package pl.wwsis.MicroBlog.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import pl.wwsis.MicroBlog.dao.PostDao;
import pl.wwsis.MicroBlog.model.Post;
import pl.wwsis.MicroBlog.model.User;

@Transactional
@Component
public class PostDaoImpl implements PostDao {

	@PersistenceContext
	EntityManager entityManager;

	@Override

	/** Method that retrieves all messages for a selected user (user's Timeline) */
	public List<Post> getTimelineOfUser(User user) {
		int userId = user.getId();
		String queryString = "SELECT * FROM Post WHERE AuthorId=:id";
		Query query = entityManager.createQuery(queryString);
		query.setParameter("id", userId);
		@SuppressWarnings("unchecked")
		List<Post> postList = (List<Post>) query.getResultList();
		return postList;

	}

	@Override
	/**
	 * Method that downloads all my messages (published by me) and all messages of
	 * other users that I follow (it is displaying user's Full Timeline)
	 */
	public List<Post> getFullTimelineOfUser(User user) {
		int userId = user.getId();
		String queryString = "SELECT * FROM Post WHERE authorId=:id OR authorId in (SELECT followsUserId FROM Follower where userId=:id)";
		Query query = entityManager.createQuery(queryString);
		query.setParameter("id", userId);
		@SuppressWarnings("unchecked")
		List<Post> postList = (List<Post>) query.getResultList();
		return postList;

	}

	@Override
	/**
	 * Method that retrieves all messages from all users (it is displaying user's
	 * Full Public Timeline)
	 */
	public List<Post> getFullPublicTimeline() {
		String queryString = "SELECT * FROM Post WHERE isPublic=1";
		Query query = entityManager.createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<Post> postList = (List<Post>) query.getResultList();
		return postList;

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
}
