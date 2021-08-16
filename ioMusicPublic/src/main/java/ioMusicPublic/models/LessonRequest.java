package ioMusicPublic.models;

import java.time.LocalDateTime;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import ioMusicPublic.models.instructor.Instructor;


@Entity
public class LessonRequest {
	
	//Primary Key with auto increment
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long requestId;
	
	private LocalDateTime dateTime;
	private Short duration;
	private float cost;
	
	//Relation with Student entity
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "studentId")
	private Student student;
	
	//Relation with Instructor entity
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "instructorId")
	private Instructor instructor;
	
	//Relation with Status entity
	@ManyToOne
	@JoinColumn(name = "statusId")
	private Status status;
	
	//Relation with VideoTool entity
	@ManyToOne
	@JoinColumn(name = "videoToolId")
	private VideoTool videoTool;
	
	//Relation with Instrument entity
	@ManyToOne
	@JoinColumn(name = "instrumentId")
	private Instrument instrument;
	
	//Relation with Lesson entity
	@OneToOne(mappedBy = "request", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	private Lesson lesson;
	
	//Relation with Transaction entity
	@OneToOne(mappedBy = "request", cascade = CascadeType.ALL)
	private Transaction transaction;
	
	//Getters and Setters
	public Long getRequestId() {
		return requestId;
	}

	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}

	public Short getDuration() {
		return duration;
	}

	public void setDuration(Short duration) {
		this.duration = duration;
	}

	public float getCost() {
		return cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Instructor getInstructor() {
		return instructor;
	}

	public void setInstructor(Instructor instructor) {
		this.instructor = instructor;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public VideoTool getVideoTool() {
		return videoTool;
	}

	public void setVideoTool(VideoTool videoTool) {
		this.videoTool = videoTool;
	}

	public Lesson getLesson() {
		return lesson;
	}

	public void setLesson(Lesson lesson) {
		this.lesson = lesson;
	}

	public Instrument getInstrument() {
		return instrument;
	}

	public void setInstrument(Instrument instrument) {
		this.instrument = instrument;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}
	
	//This method will get the date from the dateTime value
	public String getDate() {
		String fullDate = dateTime.toString();
		String[] seperatedDate = fullDate.split("T");
		return seperatedDate[0];
	}
	
	//This method will get the time from the dateTime value
	public String getTime() {
		String fullDate = dateTime.toString();
		String[] seperatedDate = fullDate.split("T");
		return seperatedDate[1];
	}

	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}
	
	//Default constructor
	public LessonRequest() {}
}
