package ioMusicPublic.authentication.instructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import ioMusicPublic.models.instructorCandidate.InstructorCandidate;

public class CandidateDetails implements UserDetails{
	
	// Instantiate a Instructor Candidate instance
	private InstructorCandidate instructorCandidate;
	
	//Create a candidate role String
	private String role = "candidate";

	// Constructor
	public CandidateDetails(InstructorCandidate instructorCandidate) {
		this.instructorCandidate = instructorCandidate;
		this.role = "candidate";
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("instructorCandidate"));
		return authorities;
	}

	// Get the candidates password from the database
	@Override
	public String getPassword() {
		return instructorCandidate.getPassword();
	}

	// Login will happen with candidates email address instead of default username
	@Override
	public String getUsername() {
		return instructorCandidate.getEmail();
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
		return instructorCandidate.getFirstName();
	}
	
	public Integer getId() {
		return instructorCandidate.getCandidateId();
	}

	public InstructorCandidate getInstructorCandidate() {
		return instructorCandidate;
	}

	public void setInstructorCandidate(InstructorCandidate instructorCandidate) {
		this.instructorCandidate = instructorCandidate;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
