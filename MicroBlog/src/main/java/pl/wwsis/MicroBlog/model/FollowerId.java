package pl.wwsis.MicroBlog.model;

import java.io.Serializable;

public class FollowerId implements Serializable{
     int id;
     int followsUserId;

     
	public FollowerId(int id, int followsUserId) {
		this.id = id;
		this.followsUserId = followsUserId;
	}

     
}
