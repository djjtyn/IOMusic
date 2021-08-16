package ioMusicPublic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import compositePrimaryKeys.CandidateApplicationKey;
import ioMusicPublic.models.instructorCandidate.CandidateApplication;
import ioMusicPublic.models.instructorCandidate.InstructorCandidate;

public interface CandidateApplicationRepository extends JpaRepository<CandidateApplication, CandidateApplicationKey>{
	
	CandidateApplication getByCandidate(InstructorCandidate candidate);
	
	//This method will count the amount of applications with a status of Pending Approval
	@Query(value = "SELECT Count(*) FROM CandidateApplication WHERE statusId = 1", nativeQuery = true)
	Long countApplicationsPendingApproval();
}
