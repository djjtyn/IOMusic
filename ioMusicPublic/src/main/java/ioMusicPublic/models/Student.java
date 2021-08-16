package ioMusicPublic.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Student extends User{
	
	//Primary Key with auto increment
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long studentId;
	
	//Relation with LessonRequest table
	@OneToMany(mappedBy = "student", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	private List<LessonRequest> lessonRequests;
	
	//Relation with the comment table
	@OneToMany(mappedBy = "student", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	private List<Comment> comments;
	
	//Getters and Setters
	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
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
	//Default Constructor
	public Student() {}
}