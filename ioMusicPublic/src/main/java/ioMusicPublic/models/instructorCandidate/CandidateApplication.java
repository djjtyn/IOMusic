package ioMusicPublic.models.instructorCandidate;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import compositePrimaryKeys.CandidateApplicationKey;
import ioMusicPublic.models.Admin;
import ioMusicPublic.models.Status;

@Entity
public class CandidateApplication implements Serializable{
	
	
	//Composite Key using InstructorCandidate id and Admin id primary keys
	@EmbeddedId
	private CandidateApplicationKey id = new CandidateApplicationKey();
	
	//Relation with the InstructorCandidate entity
	@OneToOne(cascade = CascadeType.PERSIST)
	@MapsId("candidateId")
	@JoinColumn(name = "candidateId")
	private InstructorCandidate candidate;
		
	//Relation with the Admin entity
	@ManyToOne(cascade = CascadeType.PERSIST)
	@MapsId("adminId")
	@JoinColumn(name = "adminId")
	private Admin admin;
	
	private LocalDate creationDate;
       
    //Relation with Status entity
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "statusId")
	private Status status;
	
    //Getters and Setters
	public LocalDate getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	public InstructorCandidate getCandidate() {
		return candidate;
	}

	public void setCandidate(InstructorCandidate candidate) {
		this.candidate = candidate;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	//Default constructor
	public CandidateApplication() {}
}
