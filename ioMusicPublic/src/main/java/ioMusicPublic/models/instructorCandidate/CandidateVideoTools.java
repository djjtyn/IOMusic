package ioMusicPublic.models.instructorCandidate;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import compositePrimaryKeys.CandidateVideoToolKey;
import ioMusicPublic.models.VideoTool;

@Entity
public class CandidateVideoTools {
	
	//Composite Key using InstructorCandidate id and VideoTool id primary keys
	@EmbeddedId
	private CandidateVideoToolKey id = new CandidateVideoToolKey();
	
	//Relation with the InstructorCandidate entity
	@ManyToOne
	@MapsId("candidateId")
	@JoinColumn(name = "candidateId")
	private InstructorCandidate candidate;
	
	//Relation with the VideoTool entity
	@ManyToOne
	@MapsId("videoToolId")
	@JoinColumn(name = "videoToolId")
	private VideoTool videoTool;

	public CandidateVideoToolKey getId() {
		return id;
	}

	public void setId(CandidateVideoToolKey id) {
		this.id = id;
	}

	public InstructorCandidate getCandidate() {
		return candidate;
	}

	public void setCandidate(InstructorCandidate candidate) {
		this.candidate = candidate;
	}

	public VideoTool getVideoTool() {
		return videoTool;
	}

	public void setVideoTool(VideoTool videoTool) {
		this.videoTool = videoTool;
	}
}
