package ioMusicPublic.authentication.instructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import ioMusicPublic.models.instructor.Instructor;


public class InstructorDetails implements UserDetails{

	// Instantiate an Instructor instance
	private Instructor instructor;
	
	//Create a instructor role String
	private String role = "instructor";

	// Constructor
	public InstructorDetails(Instructor instructor) {
		this.instructor = instructor;
		this.role = "instructor";
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("instructor"));
		return authorities;
	}

	// Get the candidates password from the database
	@Override
	public String getPassword() {
		return instructor.getPassword();
	}

	// Login will happen with candidates email address instead of default username
	@Override
	public String getUsername() {
		return instructor.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public String getFirstName() {
		return instructor.getFirstName();
	}

	public Instructor getInstructor() {
		return instructor;
	}

	public void setInstructor(Instructor instructor) {
		this.instructor = instructor;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public long getInstructorId() {
		return instructor.getInstructorId();
	}
}
