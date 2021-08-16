//This class is used to create the primary key for the InstructorVideoTools table using the Instructor and VideoTool Primary Keys
package compositePrimaryKeys;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class InstructorVideoToolKey implements Serializable{
	
	//The two instance variables below will be used to create a primary key in the InstructorVideoTools entity
	private Long instructorId;
	private Short videoToolId;
	
	//Getters and Setters
	public Long getInstructorId() {
		return instructorId;
	}
	public void setInstructorId(Long instructorId) {
		this.instructorId = instructorId;
	}
	public Short getVideoToolId() {
		return videoToolId;
	}
	public void setVideoToolId(Short videoToolId) {
		this.videoToolId = videoToolId;
	}
}
