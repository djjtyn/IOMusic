package ioMusicPublic.models.instructorCandidate;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import ioMusicPublic.models.Genre;
import ioMusicPublic.models.User;

@Entity
public class InstructorCandidate extends User{
	
	//instance variables
	//Primary Key with auto increment
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer candidateId;
	
	@Column(length = 2600)
	String description;
	float hourlyRate;
	
	//Relation with Genre entity
	@ManyToOne
	@JoinColumn(name = "genreId")
	private Genre genreId;
		
	//Relation with CandidateVideoTools table
	@OneToMany(mappedBy = "candidate")
	private List<CandidateVideoTools> videoTools;
	
	//Relation with CandidateFavouriteArtist Table
	@OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL)
	private List<CandidateFavouriteArtist> favouriteArtists;
	
	//Relation with CandidateInstrument Table
	@OneToMany(mappedBy = "candidate")
	private List<CandidateInstrument> instruments;
	
	//Relation with CandidateApplication entity
	@OneToOne(mappedBy = "candidate", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	private CandidateApplication application;
	
	//Getters and Setters
	public Integer getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Integer candidateId) {
		this.candidateId = candidateId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getHourlyRate() {
		return hourlyRate;
	}

	public void setHourlyRate(float hourlyRate) {
		this.hourlyRate = hourlyRate;
	}

	public Genre getGenreId() {
		return genreId;
	}

	public void setGenreId(Genre genreId) {
		this.genreId = genreId;
	}

	public List<CandidateVideoTools> getVideoTools() {
		return videoTools;
	}

	public void setVideoTools(List<CandidateVideoTools> videoTools) {
		this.videoTools = videoTools;
	}

	public List<CandidateFavouriteArtist> getFavouriteArtists() {
		return favouriteArtists;
	}

	public void setFavouriteArtists(List<CandidateFavouriteArtist> favouriteArtists) {
		this.favouriteArtists = favouriteArtists;
	}

	public List<CandidateInstrument> getInstruments() {
		return instruments;
	}

	public void setInstruments(List<CandidateInstrument> instruments) {
		this.instruments = instruments;
	}

	public CandidateApplication getApplication() {
		return application;
	}

	public void setApplication(CandidateApplication application) {
		this.application = application;
	}	
	
	//Default constructor
	public InstructorCandidate() {}
}