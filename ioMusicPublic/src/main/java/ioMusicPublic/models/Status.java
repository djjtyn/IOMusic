package ioMusicPublic.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Status {
	
	//instance variables
	//Primary Key with auto increment
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Short statusId;
	
	private String statusType;
	
//	//Relation with CandidateApplication Table
//	@OneToMany(mappedBy = "status", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
//	private List<CandidateApplication> applications;
	
	
	//Getters and Setters
	public Short getStatusId() {
		return statusId;
	}

	public void setStatusId(Short statusId) {
		this.statusId = statusId;
	}

	public String getStatusType() {
		return statusType;
	}

	public void setStatusType(String statusType) {
		this.statusType = statusType;
	}
	
	//Default constructor
	public Status() {}
}
