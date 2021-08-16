//This class deals wit hboth instructor and instructor candidate user details

package ioMusicPublic.authentication.instructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import ioMusicPublic.models.instructor.Instructor;
import ioMusicPublic.models.instructorCandidate.InstructorCandidate;
import ioMusicPublic.repositories.InstructorCandidateRepository;
import ioMusicPublic.repositories.InstructorRepository;

public class InstructorDetailsService implements UserDetailsService {

	// Instantiate the InstructorCandidate repo
	@Autowired
	private InstructorCandidateRepository candidateRepo;
	
	// Instantiate the Instructor repo
	@Autowired
	private InstructorRepository instructorRepo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		//Check the instructor repo first for the user
		Instructor instructor = instructorRepo.findByEmail(email);
		//If there is no instructor linked to that email address, try the candidate repo
		if(instructor == null) {
			InstructorCandidate candidate = candidateRepo.findByEmail(email);
			//If there is no candidate linked to that email address, throw an error
			if(candidate == null) {
				throw new UsernameNotFoundException("There was an issue locating this user account");
			} else {
				//If there is an candidate found, use their details to instantiate a CandidateDetails instance
				return new CandidateDetails(candidate);
			}
		}
		//If there is an instructor found, use their details to instantiate a InstructorDetails instance
		return new InstructorDetails(instructor);
	}
}
