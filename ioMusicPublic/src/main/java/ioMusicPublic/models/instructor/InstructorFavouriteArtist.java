package ioMusicPublic.models.instructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import compositePrimaryKeys.InstructorFavouriteArtistKey;
import ioMusicPublic.models.Artist;

@Entity
public class InstructorFavouriteArtist {
	
	//Composite Key using Instructor id and Artist id primary keys
	@EmbeddedId
	private InstructorFavouriteArtistKey id = new InstructorFavouriteArtistKey();
	
	//Relation with Instructor Table
	@ManyToOne
	@MapsId("instructorId")
	@JoinColumn(name = "instructorId")
	private Instructor instructor;
	
	//Relation with Artist Table
	@ManyToOne
	@MapsId("artistId")
	@JoinColumn(name = "artistId")
	private Artist artist;
	
	//Getters and Setters
	public InstructorFavouriteArtistKey getId() {
		return id;
	}

	public void setId(InstructorFavouriteArtistKey id) {
		this.id = id;
	}

	public Instructor getInstructor() {
		return instructor;
	}

	public void setInstructor(Instructor instructor) {
		this.instructor = instructor;
	}

	public Artist getArtist() {
		return artist;
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
	}
}
