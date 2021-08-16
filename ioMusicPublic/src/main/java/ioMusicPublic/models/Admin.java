package ioMusicPublic.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import ioMusicPublic.models.instructorCandidate.CandidateApplication;

@Entity
public class Admin extends User{
	
	//Primary Key with auto increment
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Short adminId;
	
	//Relation with CandidateApplication Table
	@OneToMany(mappedBy = "admin", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	private List<CandidateApplication> applications;
	
	//Getters and Setters
	public Short getAdminId() {
		return adminId;
	}

	public void setAdminId(Short adminId) {
		this.adminId = adminId;
	}

	public List<CandidateApplication> getApplications() {
		return applications;
	}

	public void setApplications(List<CandidateApplication> applications) {
		this.applications = applications;
	}
	
	//Method to add a new application to the list of existing applications
	public void addApplication(CandidateApplication application) {
		this.applications.add(application);
	}
	
	//Default constructor
	public Admin() {}
}