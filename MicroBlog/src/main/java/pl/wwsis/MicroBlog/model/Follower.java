package pl.wwsis.MicroBlog.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.AccessLevel;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name="FOLLOWER")
public class Follower {

    @Id
    @GeneratedValue
    @Column(name = "userId")
    private int id;
    
    @Column(name = "followsUserId")
    private int followsUserId;

    public Follower() {}
    
}
