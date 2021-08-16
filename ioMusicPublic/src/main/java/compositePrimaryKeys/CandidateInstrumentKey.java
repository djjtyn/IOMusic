//This class is used to create the primary key for the CandidateInstrument table using the Candidate and Instrument Primary Keys
package compositePrimaryKeys;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class CandidateInstrumentKey implements Serializable{
	
	//The two instance variables below will be used to create a primary key in the CandidateFavouriteArtist entity
	private Integer candidateId;
	private Short instrumentId;
	
	//Getters and Setters
	public Integer getCandidateId() {
		return candidateId;
	}
	public void setCandidateId(Integer candidateId) {
		this.candidateId = candidateId;
	}
	public Short getInstrumentId() {
		return instrumentId;
	}
	public void setInstrumentId(Short instrumentId) {
		this.instrumentId = instrumentId;
	}
}
