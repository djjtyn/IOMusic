package compositePrimaryKeys;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Embeddable;

@Embeddable
public class LessonRequestKey implements Serializable{
	
	//The three instance variables below will be used to create a primary key in the LessonRequest entity
	private Long instructorId;
	private Long studentId;
	private LocalDateTime dateTime;
	public Long getInstructorId() {
		return instructorId;
	}
	public void setInstructorId(Long instructorId) {
		this.instructorId = instructorId;
	}
	public Long getStudentId() {
		return studentId;
	}
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	public LocalDateTime getDateTime() {
		return dateTime;
	}
	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}
}
