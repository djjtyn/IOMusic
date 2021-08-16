package ioMusicPublic.models.instructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import compositePrimaryKeys.InstructorInstrumentKey;
import ioMusicPublic.models.Instrument;

@Entity
public class InstructorInstrument {
	
	//Composite Key using InstructorCandidate id and Artist id primary keys
	@EmbeddedId
	private InstructorInstrumentKey id = new InstructorInstrumentKey();
		
	//Relation with the Instructor entity
	@ManyToOne
	@MapsId("instructorId")
	@JoinColumn(name = "instructorId")
	private Instructor instructor;
		
	//Relation with the Instrument entity
	@ManyToOne
	@MapsId("instrumentId")
	@JoinColumn(name = "instrumentId")
	private Instrument instrument;

	//Getters and Setters
	public InstructorInstrumentKey getId() {
		return id;
	}

	public void setId(InstructorInstrumentKey id) {
		this.id = id;
	}

	public Instructor getInstructor() {
		return instructor;
	}

	public void setInstructor(Instructor instructor) {
		this.instructor = instructor;
	}

	public Instrument getInstrument() {
		return instrument;
	}

	public void setInstrument(Instrument instrument) {
		this.instrument = instrument;
	}
}
