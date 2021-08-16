package ioMusicPublic.models.instructor;

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

import ioMusicPublic.models.Comment;
import ioMusicPublic.models.Genre;
import ioMusicPublic.models.LessonRequest;
import ioMusicPublic.models.User;
import ioMusicPublic.models.instructorCandidate.CandidateApplication;

@Entity
public class Instructor extends User{
	
	//Primary Key with auto increment
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long instructorId;
	
	@Column(length = 2600)
	String description;
	float hourlyRate;
	
	//Relation with Genre entity
	@ManyToOne
	@JoinColumn(name = "genreId")
	private Genre genreId;
				
	//Relation with InstructorVideoTools table
	@OneToMany(mappedBy = "instructor")
	private List<InstructorVideoTools> videoTools;
	
	//Relation with LessonRequest entity
	@OneToMany(mappedBy = "instructor", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	private List<LessonRequest> lessonRequests;
	
	//Relation with Comment entity
	@OneToMany(mappedBy = "instructor", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	private List<Comment> comments;
		
	//Relation with InstructorFavouriteArtist Table
	@OneToMany(mappedBy = "instructor")
	private List<InstructorFavouriteArtist> favouriteArtists;
		
	//Relation with InstructorInstrument Table
	@OneToMany(mappedBy = "instructor")
	private List<InstructorInstrument> instruments;
	
	//Relation with ProfilePhoto entity
	@OneToOne(mappedBy = "instructor" , cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	private ProfilePhoto profilePhoto;

	//Getters and Setters
	public Long getInstructorId() {
		return instructorId;
	}

	public void setInstructorId(Long instructorId) {
		this.instructorId = instructorId;
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
	
	public ProfilePhoto getProfilePhoto() {
		return profilePhoto;
	}

	public void setProfilePhoto(ProfilePhoto profilePhoto) {
		this.profilePhoto = profilePhoto;
	}

	public List<InstructorVideoTools> getVideoTools() {
		return videoTools;
	}

	public void setVideoTools(List<InstructorVideoTools> videoTools) {
		this.videoTools = videoTools;
	}

	public List<InstructorFavouriteArtist> getFavouriteArtists() {
		return favouriteArtists;
	}

	public void setFavouriteArtists(List<InstructorFavouriteArtist> favouriteArtists) {
		this.favouriteArtists = favouriteArtists;
	}

	public List<InstructorInstrument> getInstruments() {
		return instruments;
	}

	public void setInstruments(List<InstructorInstrument> instruments) {
		this.instruments = instruments;
	}

	public List<LessonRequest> getLessonRequests() {
		return lessonRequests;
	}

	public void setLessonRequests(List<LessonRequest> lessonRequests) {
		this.lessonRequests = lessonRequests;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}	
	
	//Method to add a new lesson request to the list of existing requests
	public void addRequest(LessonRequest request) {
		this.lessonRequests.add(request);
	}
	
	//Method to add a new comment to the list of existing comments
	public void addComment(Comment comment) {
		this.comments.add(comment);
	}
	
	//Default constructor
	public Instructor() {}
}