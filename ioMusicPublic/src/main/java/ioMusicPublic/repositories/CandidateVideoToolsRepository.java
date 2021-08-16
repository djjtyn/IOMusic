package ioMusicPublic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import compositePrimaryKeys.CandidateVideoToolKey;
import ioMusicPublic.models.instructorCandidate.CandidateVideoTools;

public interface CandidateVideoToolsRepository extends JpaRepository<CandidateVideoTools, CandidateVideoToolKey>{

}
