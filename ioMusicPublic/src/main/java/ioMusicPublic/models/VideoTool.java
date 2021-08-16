package ioMusicPublic.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import ioMusicPublic.models.instructor.InstructorVideoTools;
import ioMusicPublic.models.instructorCandidate.CandidateVideoTools;

@Entity
public class VideoTool {
	
	//instance variables
	//Primary Key with auto increment
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Short videoToolId;
	
	@Column(length = 50, unique = true)
	private String name;
	
	//Relation with CandidateVideoTools Table
	@OneToMany(mappedBy = "videoTool")
	private List<CandidateVideoTools> instructorCandidates;
	
	//Relation with InstructorVideoTools Table
	@OneToMany(mappedBy = "videoTool")
	private List<InstructorVideoTools> instructors;
	
	//Getters and Setters
	public Short getVideoToolId() {
		return videoToolId;
	}

	public void setVideoToolId(Short videoToolId) {
		this.videoToolId = videoToolId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		//First letter is capitalised
		this.name = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
	}

	public List<CandidateVideoTools> getInstructorCandidates() {
		return instructorCandidates;
	}

	public void setInstructorCandidates(List<CandidateVideoTools> instructorCandidates) {
		this.instructorCandidates = instructorCandidates;
	}

	public List<InstructorVideoTools> getInstructors() {
		return instructors;
	}

	public void setInstructors(List<InstructorVideoTools> instructors) {
		this.instructors = instructors;
	}
	
	//Default constructor
	public VideoTool() {}
}
