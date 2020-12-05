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
@Table(name="POST")
public class Post {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;
    
    @Column(name = "authorId", nullable = false)
    private int authorId ;

    @Column(name = "content", length = 4000)
    private String content;

    @Column(name = "isPublic")
    private Boolean isPublic;

    public Post() {}
    
}
