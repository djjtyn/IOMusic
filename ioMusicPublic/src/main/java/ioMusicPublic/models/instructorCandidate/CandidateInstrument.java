package ioMusicPublic.models.instructorCandidate;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import compositePrimaryKeys.CandidateInstrumentKey;
import ioMusicPublic.models.Instrument;

@Entity
public class CandidateInstrument {
	
	//Composite Key using InstructorCandidate id and Artist id primary keys
	@EmbeddedId
	private CandidateInstrumentKey id = new CandidateInstrumentKey();
	
	//Relation with the InstructorCandidate entity
	@ManyToOne
	@MapsId("candidateId")
	@JoinColumn(name = "candidateId")
	private InstructorCandidate candidate;
	
	//Relation with the Instrument entity
	@ManyToOne
	@MapsId("instrumentId")
	@JoinColumn(name = "instrumentId")
	private Instrument instrument;
	
	//Getters and Setters
	public CandidateInstrumentKey getId() {
		return id;
	}

	public void setId(CandidateInstrumentKey id) {
		this.id = id;
	}

	public InstructorCandidate getCandidate() {
		return candidate;
	}

	public void setCandidate(InstructorCandidate candidate) {
		this.candidate = candidate;
	}

	public Instrument getInstrument() {
		return instrument;
	}

	public void setInstrument(Instrument instrument) {
		this.instrument = instrument;
	}
}
