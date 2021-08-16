//This class is used to create the primary key for the InstructorInstrument table using the Instructor and Instrument Primary Keys
package compositePrimaryKeys;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class InstructorInstrumentKey implements Serializable{
	
	//The two instance variables below will be used to create a primary key in the InstructorFavouriteArtist entity
	private Long instructorId;
	private Short instrumentId;
	
	//Getters and Setters
	public Long getInstructorId() {
		return instructorId;
	}
	public void setInstructorId(Long instructorId) {
		this.instructorId = instructorId;
	}
	public Short getInstrumentId() {
		return instrumentId;
	}
	public void setInstrumentId(Short instrumentId) {
		this.instrumentId = instrumentId;
	}
}
