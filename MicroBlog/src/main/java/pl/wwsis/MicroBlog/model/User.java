package pl.wwsis.MicroBlog.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import java.util.ArrayList;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;



import javax.validation.constraints.Email;

@Data
@AllArgsConstructor
@Entity
@Table(name = "User")
public class User {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@Column(name = "login", unique = true, length = 200)
	@NotNull(message = "Login cannot be null")
	@NotBlank(message = "Login cannot be blank")
	@Size(min = 1, max = 200, message = "Login must be between 1 and 200 characters")
	private String login;

	@Column(name = "email", unique = true, length = 200)
	@Email(message = "Email should be valid")
	@Size(min = 1, max = 200, message = "Email must be between 1 and 200 characters")
	private String email;

	@Column(name = "password", length = 100)
	@NotNull(message = "Password cannot be null")
	@NotBlank(message = "Password cannot be blank")
	@Size(min = 10, max = 100, message = "Password must be between 10 and 200 characters")
	@Pattern(regexp = "(?=.*[`~!@#$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?]).+", message = "Password must contain one special character.")
	@Pattern(regexp = "(?=.*[0-9]).+", message = "Password must contain one digit.")
	@Pattern(regexp = "(?=.*[a-z]).+", message = "Password must contain one lowercase letter.")
	@Pattern(regexp = "(?=.*[A-Z]).+", message = "Password must contain one upper letter.")
	@Pattern(regexp = "(?=\\S+$).+", message = "Password must contain no whitespace.")
	private String password;

	@NotNull(message = "Firstname cannot be null")
	@NotBlank(message = "Firstname cannot be blank")
	@Column(name = "firstName", length = 200)
	@Size(max = 200, message = "FirstName must be less than 200 characters")
	private String firstName;

	@NotNull(message = "Lastname cannot be null")
	@NotBlank(message = "Lastname cannot be blank")
	@Column(name = "lastName", length = 200)
	@Size(max = 200, message = "LastName must be less than 200 characters")
	private String lastName;

	@Column(name = "gender")
	private Character gender;

	@Column(name = "dob")
	@Past(message = "Date of birth cannot be present or future")
	private Date dob;

	@Column(name = "statusId")
	private UserStatus status;

	@Column(name = "amountOfFollowers")
	private int amountOfFollowers;

	@Column(name = "amountOfFollowee")
	private int amountOfFollowee;
	
	private boolean isLogged;

    @Column(name = "token", length = 6)
	private String token;

	@Column(name="tokenExpirationDate")
	private Date tokenExpirationDate;

	@Column(name = "likedPosts")
	private ArrayList<String> likedPosts;

	public User(){}
	public User(
			String login, String email, String password,
			String firstName, String lastName, Character gender,
			Date dob
		) {
			this.login = login;
			this.email = email;
			this.password = password;
			this.firstName = firstName;
			this.lastName = lastName;
			this.gender = gender;
			this.dob = dob;
			this.status = UserStatus.INVISIBLE;
			this.amountOfFollowers = 0;
			this.amountOfFollowee = 0;
			this.likedPosts = new ArrayList<String>();
			this.isLogged=false;
	}

}
