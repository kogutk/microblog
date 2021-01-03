package pl.wwsis.MicroBlog.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import pl.wwsis.MicroBlog.dao.FollowerDao;
import pl.wwsis.MicroBlog.model.Follower;
import pl.wwsis.MicroBlog.model.FollowerId;
import pl.wwsis.MicroBlog.model.User;
import javax.transaction.Transactional;

@Transactional
public class FollowerDaoImpl implements FollowerDao {

	@PersistenceContext
	EntityManager entityManager;

	// I follow FOLLOWWE, FOLLOWER follows me

	/**
	 * Adding another user to being tracked by the currently logged in user (A pair
	 * of "follower" and "followee" is created)
	 */
	@Override
	public Follower addFolloweeOfUser(User user, User followee) {

		int userId = user.getId();
		int followeeId = followee.getId();

		entityManager.getTransaction().begin();
		Follower follower = new Follower(userId, followeeId);
		entityManager.persist(follower);
		entityManager.getTransaction().commit();
		return follower;
	}

	/**
	 * Checking if another user is on my tracked list (Reading the "follower" and
	 * "followee" pairs)
	 */
	@Override
	public Follower findFolloweeOfUser(User user, User followee) {
		int userId = user.getId();
		int followeeId = followee.getId();

		FollowerId followerId = new FollowerId(userId, followeeId);
		Follower follower = entityManager.find(Follower.class, followerId);
		return follower;
	}

	/**
	 * Removing another user from those currently logged in by the user (Removing
	 * the "follower" and "followee" pairs)
	 */
	@Override
	public Follower deleteFolloweeOfUser(User user, User followee) {
		int userId = user.getId();
		int followeeId = followee.getId();

		FollowerId followerId = new FollowerId(userId, followeeId);
		Follower follower = entityManager.find(Follower.class, followerId);

		entityManager.getTransaction().begin();

		entityManager.remove(follower);

		entityManager.getTransaction().commit();
		return follower;

	} // alt as parameter Follower
}
