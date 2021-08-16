//This class is used to create the primary key for the InstructorFavouriteArtist table using the Instructor and Artist Primary Keys
package compositePrimaryKeys;

import java.io.Serializable;
import javax.persistence.Embeddable;

@Embeddable
public class InstructorFavouriteArtistKey implements Serializable{
	//The two instance variables below will be used to create a primary key in the InstructorFavouriteArtist entity
	private Long instructorId;
	private Integer artistId;
	
	//Getters and Setters
	public Long getInstructorId() {
		return instructorId;
	}
	public void setInstructorId(Long instructorId) {
		this.instructorId = instructorId;
	}
	public Integer getArtistId() {
		return artistId;
	}
	public void setArtistId(Integer artistId) {
		this.artistId = artistId;
	}
}
