//This class is used to create the primary key for the CandidateFavouriteArtist table using the Candidate and Artist Primary Keys
package compositePrimaryKeys;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class CandidateFavouriteArtistKey implements Serializable{
	
	//The two instance variables below will be used to create a primary key in the CandidateFavouriteArtist entity
	private Integer candidateId;
	private Integer artistId;
	
	//Getters and Setters
	public Integer getCandidateId() {
		return candidateId;
	}
	public void setCandidateId(Integer candidateId) {
		this.candidateId = candidateId;
	}
	public Integer getArtistId() {
		return artistId;
	}
	public void setArtistId(Integer artistId) {
		this.artistId = artistId;
	}
}
