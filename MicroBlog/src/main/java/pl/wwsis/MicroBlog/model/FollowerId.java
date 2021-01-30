package pl.wwsis.MicroBlog.model;

import lombok.Data;
import java.io.Serializable;

@Data
public class FollowerId implements Serializable{
     int id;
     int followsUserId;

     
	public FollowerId(int id, int followsUserId) {
		this.id = id;
		this.followsUserId = followsUserId;
	}

     public FollowerId() {};
}
