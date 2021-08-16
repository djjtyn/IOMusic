package ioMusicPublic.models;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class User{
	//instance variables	
	@Column(length = 40, nullable = false)
	private String firstName;
	
	@Column(length = 40, nullable = false)
	private String lastName;
	
	@Column(nullable = false, unique=true)
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	//TimeZone foreign key
	@ManyToOne
	@JoinColumn(name = "timeZoneID")
	private TimeZone userTimeZone;

	//GETTERS AND SETTERS
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		//First letter is capitalised
		this.firstName = firstName.substring(0,1).toUpperCase() + firstName.substring(1).toLowerCase();
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		//First letter is capitalised
		this.lastName = lastName.substring(0,1).toUpperCase() + lastName.substring(1).toLowerCase();
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

	public TimeZone getUserTimeZone() {
		return userTimeZone;
	}

	public void setUserTimeZone(TimeZone userTimeZone) {
		this.userTimeZone = userTimeZone;
	}	
}