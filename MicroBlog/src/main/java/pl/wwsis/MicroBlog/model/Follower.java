package pl.wwsis.MicroBlog.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Column;
import lombok.Data;

@Data
@Entity
@IdClass(FollowerId.class)
@Table(name = "FOLLOWER")
public class Follower {

	@Id
	@Column(name = "userId")
	private int id;

	@Id
	@Column(name = "followsUserId")
	private int followsUserId;

	public Follower() {
	}

	public Follower(int id, int followsUserId) {
		this.id = id;
		this.followsUserId = followsUserId;
	}

}
