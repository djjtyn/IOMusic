package ioMusicPublic.authentication.student;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import ioMusicPublic.models.Student;

public class StudentDetails implements UserDetails{
	
	//Instantiate a Student instance
	private Student student;
	
	//Create a student role String
	private String role = "student";
	
	//Constructor
	public StudentDetails(Student student) {
		this.student = student;
		this.role = "student";
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("student"));
		return authorities;
	}
	
	//Get the students password from the database
	@Override
	public String getPassword() {
		return student.getPassword();
	}
	
	//Login will happen with students email address instead of default username
	@Override
	public String getUsername() {
		return student.getEmail();
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
		return student.getFirstName();
	}
	
	public long getStudentId() {
		return student.getStudentId();
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	
}
