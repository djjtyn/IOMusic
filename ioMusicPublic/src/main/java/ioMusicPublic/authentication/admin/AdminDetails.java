package ioMusicPublic.authentication.admin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import ioMusicPublic.models.Admin;

public class AdminDetails implements UserDetails{
	
	//Instantiate an Admin instance
	private Admin admin;
	
	//Create a admin role String
	private String role = "admin";
	
	//Constructor
	public AdminDetails(Admin admin) {
		this.admin = admin;
		this.role = "admin";
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("admin"));
		return authorities;
	}
	
	//Get the admins password from the database
	@Override
	public String getPassword() {
		return admin.getPassword();
	}
	
	//Login will happen with admins email address instead of default username
	@Override
	public String getUsername() {
		return admin.getEmail();
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
		return admin.getFirstName();
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public Short getAdminId() {
		return admin.getAdminId();
	}
}
