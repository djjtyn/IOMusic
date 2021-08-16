//This class is used to create the primary key for the CandidateVideoTools table using the Candidate and VideoTool Primary Keys
package compositePrimaryKeys;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class CandidateVideoToolKey implements Serializable{
	//The two instance variables below will be used to create a primary key in the CandidateVideoTools entity
	private Integer candidateId;
	private Short videoToolId;
	
	//Getters and Setters
	public Integer getCandidateId() {
		return candidateId;
	}
	public void setCandidateId(Integer candidateId) {
		this.candidateId = candidateId;
	}
	public Short getVideoToolId() {
		return videoToolId;
	}
	public void setVideoToolId(Short videoToolId) {
		this.videoToolId = videoToolId;
	}
}
