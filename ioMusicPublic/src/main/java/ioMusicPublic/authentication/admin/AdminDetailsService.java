package ioMusicPublic.authentication.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import ioMusicPublic.models.Admin;
import ioMusicPublic.repositories.AdminRepository;


public class AdminDetailsService implements UserDetailsService{
	
	
	//Instantiate the admin repo
	@Autowired
	private AdminRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		//find the admin details contained in the database
		Admin admin = repo.findByEmail(email);
		//If there is no student with that email address
		if(admin == null) {
			throw new UsernameNotFoundException("There was an issue locating this user account");
		}
		//If an admin is found with the email address entered, create a AdminDetails instance with their details
		return new AdminDetails(admin);
	}

}
