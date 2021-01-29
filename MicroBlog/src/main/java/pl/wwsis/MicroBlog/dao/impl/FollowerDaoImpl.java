package pl.wwsis.MicroBlog.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import pl.wwsis.MicroBlog.dao.FollowerDao;
import pl.wwsis.MicroBlog.model.Follower;
import pl.wwsis.MicroBlog.model.FollowerId;
import pl.wwsis.MicroBlog.model.Post;
import pl.wwsis.MicroBlog.model.User;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import java.util.List;

@Transactional
@Component
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
		user.setAmountOfFollowee(user.getAmountOfFollowee() + 1);
		Follower follower = new Follower(userId, followeeId);
		entityManager.merge(user);
		entityManager.persist(follower);
	
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

		user.setAmountOfFollowee(user.getAmountOfFollowee() - 1);
		
		FollowerId followerId = new FollowerId(userId, followeeId);
		Follower follower = entityManager.find(Follower.class, followerId);
		entityManager.merge(user);
		entityManager.remove(follower);
		
		return follower;
	} 

	

	@Override
	public List<Follower> getListOfFollowers(User user) {
		int userId = user.getId();

		String queryString = "SELECT f FROM Follower f WHERE f.followsUserId=:id";
		Query query = entityManager.createQuery(queryString);
		query.setParameter("id", userId);

		List<Follower> followersList =null;
		try {
			followersList = (List<Follower>) query.getResultList();
		}
		catch (Exception e) {e.printStackTrace();}
		return followersList;
	}

}
