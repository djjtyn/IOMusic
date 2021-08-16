package ioMusicPublic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import compositePrimaryKeys.CandidateInstrumentKey;
import ioMusicPublic.models.instructorCandidate.CandidateInstrument;

public interface CandidateInstrumentRepository extends JpaRepository<CandidateInstrument, CandidateInstrumentKey>{

}
