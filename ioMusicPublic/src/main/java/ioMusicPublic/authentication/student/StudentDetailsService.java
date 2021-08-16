package ioMusicPublic.authentication.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import ioMusicPublic.models.Student;
import ioMusicPublic.repositories.StudentRepository;

public class StudentDetailsService implements UserDetailsService{
	
	
	//Instantiate the Student repo
	@Autowired
	private StudentRepository repo;
		
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		//find the student details contained in the database
		Student student = repo.findByEmail(email);
		//If there is no student with that email address
		if(student == null) {
			throw new UsernameNotFoundException("There was an issue locating this user account");
		}
		//If a student is found with the email address entered, create a StudentDetails instance with their details
		return new StudentDetails(student);
	}

}
