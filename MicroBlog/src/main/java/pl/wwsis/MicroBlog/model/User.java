package pl.wwsis.MicroBlog.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name="User")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;
    
    @Column(name = "login")
    private String login;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "password")
    private String password;
    
    @Column(name = "firstName")
    private String firstName;
    
    @Column(name = "lastName")
    private String lastName;

    @Column(name = "gender")
    private Character gender;

    @Column(name = "dob")
    private Date dob;
    
    @Column(name = "statusId")
    private UserStatus status;
    
    public User() {}


	public User(String login, String email, String password, String firstName, String lastName, Character gender,
			Date dob) {
		
		this.login = login;
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.dob = dob;
		this.status = UserStatus.INVISIBLE;
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
	
	
	
	
    
    
    
}

