package ioMusicPublic.models.instructorCandidate;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import compositePrimaryKeys.CandidateFavouriteArtistKey;
import ioMusicPublic.models.Artist;


@Entity
public class CandidateFavouriteArtist {
	
	//Composite Key using InstructorCandidate id and Artist id primary keys
	@EmbeddedId
	private CandidateFavouriteArtistKey id = new CandidateFavouriteArtistKey();
	
	//Relation with the InstructorCandidate entity
	@ManyToOne
	@MapsId("candidateId")
	@JoinColumn(name = "candidateId")
	private InstructorCandidate candidate;
	
	//Relation with the Artist entity
	@ManyToOne
	@MapsId("artistId")
	@JoinColumn(name = "artistId")
	private Artist artist;
	
	//Getters and Setters
	public CandidateFavouriteArtistKey getId() {
		return id;
	}

	public void setId(CandidateFavouriteArtistKey id) {
		this.id = id;
	}

	public InstructorCandidate getCandidate() {
		return candidate;
	}

	public void setCandidate(InstructorCandidate candidate) {
		this.candidate = candidate;
	}

	public Artist getArtist() {
		return artist;
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
	}
}
