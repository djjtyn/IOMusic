package ioMusicPublic.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import ioMusicPublic.models.instructor.InstructorInstrument;
import ioMusicPublic.models.instructorCandidate.CandidateInstrument;

@Entity
public class Instrument {
	//instance variables
	//Primary Key with auto increment
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Short instrumentId;
	
	@Column(length = 20, unique=true)
	private String name;
	
	//Relation with InstructorInstrument Table
	@OneToMany(mappedBy = "instrument")
	private List<InstructorInstrument> instructors;
	
	//Relation with CandidateInstrument Table
	@OneToMany(mappedBy = "instrument")
	private List<CandidateInstrument> instructorCandidates;
	
	//Getters and Setters
	public Short getInstrumentId() {
		return instrumentId;
	}

	public void setInstrumentId(Short instrumentId) {
		this.instrumentId = instrumentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		//First letter is capitalised
		this.name = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
	}

	public List<CandidateInstrument> getInstructorCandidates() {
		return instructorCandidates;
	}

	public void setInstructorCandidates(List<CandidateInstrument> instructorCandidates) {
		this.instructorCandidates = instructorCandidates;
	}

	public List<InstructorInstrument> getInstructors() {
		return instructors;
	}

	public void setInstructors(List<InstructorInstrument> instructors) {
		this.instructors = instructors;
	}
	
	//Default constructor
	public Instrument() {}
}
