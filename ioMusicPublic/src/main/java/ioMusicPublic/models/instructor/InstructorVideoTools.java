package ioMusicPublic.models.instructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import compositePrimaryKeys.InstructorVideoToolKey;
import ioMusicPublic.models.VideoTool;


@Entity
public class InstructorVideoTools {
	
	//Composite Key using Instructor id and VideoTool id primary keys
	@EmbeddedId
	private InstructorVideoToolKey id = new InstructorVideoToolKey();
	
	//Relation with the Instructor entity
	@ManyToOne
	@MapsId("instructorId")
	@JoinColumn(name = "instructorId")
	private Instructor instructor;
	
	//Relation with the VideoTool entity
	@ManyToOne
	@MapsId("videoToolId")
	@JoinColumn(name = "videoToolId")
	private VideoTool videoTool;
	
	//Getters and Setters
	public InstructorVideoToolKey getId() {
		return id;
	}

	public void setId(InstructorVideoToolKey id) {
		this.id = id;
	}

	public Instructor getInstructor() {
		return instructor;
	}

	public void setInstructor(Instructor instructor) {
		this.instructor = instructor;
	}

	public VideoTool getVideoTool() {
		return videoTool;
	}

	public void setVideoTool(VideoTool videoTool) {
		this.videoTool = videoTool;
	}
}
