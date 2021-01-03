package pl.wwsis.MicroBlog.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.AccessLevel;
import lombok.Data;

@Data
//@AllArgsConstructor
@Entity @IdClass(FollowerId.class)


@Table(name="FOLLOWER")
public class Follower {

@Id
    @Column(name = "userId")
    private int id;
    
@Id
    @Column(name = "followsUserId")
    private int followsUserId;

    public int getId() {
	return id;
}


public int getFollowsUserId() {
	return followsUserId;
}


	public Follower() {}


	public Follower(int id, int followsUserId) {
		this.id = id;
		this.followsUserId = followsUserId;
	}
    
    
    
}
