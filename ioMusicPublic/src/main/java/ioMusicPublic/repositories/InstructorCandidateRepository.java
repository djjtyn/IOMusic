package ioMusicPublic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ioMusicPublic.models.instructorCandidate.InstructorCandidate;

public interface InstructorCandidateRepository extends JpaRepository<InstructorCandidate, Integer>{
	
	//No 2 candidates will have the same email address so this method just returns a single InstructorCandidate instance
	InstructorCandidate findByEmail(String emailAddress);
}
