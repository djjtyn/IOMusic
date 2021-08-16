package ioMusicPublic.models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ioMusicPublic.models.instructor.Instructor;

@Entity
public class Comment {
	//instance variables
	//Primary Key with auto increment
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Short commentId;
	
	//Comments will be limited to 500 characters
	@Column(length = 500)
	private String commentText;
	
	private LocalDate creationDate;
	
	//Relation with Instructor entity
	@ManyToOne
	@JoinColumn(name = "instructorId")
	private Instructor instructor;
	
	//Relation with Student entity
	@ManyToOne
	@JoinColumn(name = "studentId")
	private Student student;
	
	//Getters and Setters
	public Short getCommentId() {
		return commentId;
	}

	public void setCommentId(Short commentId) {
		this.commentId = commentId;
	}

	public String getCommentText() {
		return commentText;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

	public Instructor getInstructor() {
		return instructor;
	}

	public void setInstructor(Instructor instructor) {
		this.instructor = instructor;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public LocalDate getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}
	
	//Default constructor
	public Comment() {}
}
