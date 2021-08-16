package ioMusicPublic.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import ioMusicPublic.models.instructor.InstructorFavouriteArtist;
import ioMusicPublic.models.instructorCandidate.CandidateFavouriteArtist;

@Entity
public class Artist {
	//instance variables
	//Primary Key with auto increment
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer artistId;
	
	@Column(length = 50, unique=true)
	private String artistName;
	
	//Relation with InstructorFavouriteArtist Table
	@OneToMany(mappedBy = "artist")
	private List<InstructorFavouriteArtist> instructors;
	
	//Relation with CandidateFavouriteArtist Table
	@OneToMany(mappedBy = "artist")
	private List<CandidateFavouriteArtist> instructorCandidates;
	
	//Getters and Setters
	public Integer getArtistId() {
		return artistId;
	}

	public void setArtistId(Integer artistId) {
		this.artistId = artistId;
	}

	public String getArtistName() {
		return artistName;
	}

	public void setArtistName(String artistName) {
		//First letter is capitalised
		this.artistName = artistName.substring(0,1).toUpperCase() + artistName.substring(1).toLowerCase();
	}

	public List<CandidateFavouriteArtist> getInstructorCandidates() {
		return instructorCandidates;
	}

	public void setInstructorCandidates(List<CandidateFavouriteArtist> instructorCandidates) {
		this.instructorCandidates = instructorCandidates;
	}

	public List<InstructorFavouriteArtist> getInstructors() {
		return instructors;
	}

	public void setInstructors(List<InstructorFavouriteArtist> instructors) {
		this.instructors = instructors;
	}
	
	//Default constructor
	public Artist() {}
}
