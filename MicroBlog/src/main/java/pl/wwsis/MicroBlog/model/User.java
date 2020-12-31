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
    
    @Column(name = "name")
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
    
    
    
}

