package ioMusicPublic.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Lesson {
	//instance variables
	//Primary Key with auto increment
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long lessonId;
	
	private String videoCallLink;
	
	//Relation with LessonRequest entity
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "requestId", referencedColumnName = "requestId", unique = true)
    private LessonRequest request;
	
	//Getters and Setters
	public Long getLessonId() {
		return lessonId;
	}

	public void setLessonId(Long lessonId) {
		this.lessonId = lessonId;
	}

	public String getVideoCallLink() {
		return videoCallLink;
	}

	public void setVideoCallLink(String videoCallLink) {
		this.videoCallLink = videoCallLink;
	}

	public LessonRequest getRequest() {
		return request;
	}

	public void setRequest(LessonRequest request) {
		this.request = request;
	}
	
	//Default constructor
	public Lesson() {}
}
