package pl.wwsis.MicroBlog.model;


public class FollowerId {
     int id;
     int followsUserId;

     
	public FollowerId(int id, int followsUserId) {
		this.id = id;
		this.followsUserId = followsUserId;
	}

     
}
