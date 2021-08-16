//This class is used to create the primary key for the CandidateApplication table using the InstructorCandidate and Admin Primary Keys

package compositePrimaryKeys;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class CandidateApplicationKey implements Serializable{
	
	//The two instance variables below will be used to create a primary key in the CandidateApplication entity
	private Integer candidateId;
	private Short adminId;
	
	//Getters and Setters
	public Integer getCandidateId() {
		return candidateId;
	}
	public void setCandidateId(Integer candidateId) {
		this.candidateId = candidateId;
	}
	public Short getAdminId() {
		return adminId;
	}
	public void setAdminId(Short adminId) {
		this.adminId = adminId;
	}	
}
