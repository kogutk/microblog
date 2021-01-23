package pl.wwsis.MicroBlog.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name = "POST")
public class Post {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@Column(name = "authorId", nullable = false)
	private int authorId;

	@NotNull(message = "Content cannot be null")
	@NotBlank(message = "Content cannot be blank")
	@Size(min = 1, max = 4000, message = "Content must be between 1 and 4000 characters")
	@Column(name = "content", length = 4000)
	private String content;

	@Column(name = "isPublic")
	private Boolean isPublic;

	public String getContent() {
		return content;
	}
		
	public Boolean isPublic() {
		return isPublic;
	}
	
	public Integer authorId() {
		return authorId;
	}
	
	public Post() {
	}

	public Post(int authorId, String content, Boolean isPublic) {
		super();
		this.authorId = authorId;
		this.content = content;
		this.isPublic = isPublic;
	}

}
