package pl.wwsis.MicroBlog.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	@NotBlank(message = "Login cannot be blank")
	@Size(min = 10, max = 100, message = "Password must be between 10 and 200 characters")
	@Pattern(regexp = "(?=.*[0-9]).+", message = "Password must contain one digit.")
	@Pattern(regexp = "(?=.*[a-z]).+", message = "Password must contain one lowercase letter.")
	@Pattern(regexp = "(?=.*[A-Z]).+", message = "Password must contain one upper letter.")
	@Pattern(regexp = "(?=.*[!@#$%^&*+=?-_()/\"\\.,<>~`;:]).+", message = "Password must contain one special character.")
	@Pattern(regexp = "(?=\\S+$).+", message = "Password must contain no whitespace.")

	private String password;

	@Column(name = "firstName", length = 200)
	@Size(max = 200, message = "FirstName must be between less than 200 characters")
	private String firstName;

	@Column(name = "lastName", length = 200)
	@Size(max = 200, message = "LastName must be between less than 200 characters")
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

	@Column(name = "likedPosts")
	private ArrayList<String> likedPosts;
	
	public User() {
	}

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
	}

	public int getId() {
		return id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Character getGender() {
		return gender;
	}

	public void setGender(Character gender) {
		this.gender = gender;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	public int getAmountOfFollowers() {
		return amountOfFollowers;
	}

	public void setAmountOfFollowers(int amountOfFollowers) {
		this.amountOfFollowers = amountOfFollowers;
	}

	public int getAmountOfFollowee() {
		return amountOfFollowee;
	}

	public void setAmountOfFallowee(int amountOfFollowee) {
		this.amountOfFollowee = amountOfFollowee;
	}
	
	public ArrayList<String> getLikedPosts() {
		return likedPosts;
	}

	public void setLikedPosts(ArrayList<String> likedPosts) {
		this.likedPosts = likedPosts;
	}
}
