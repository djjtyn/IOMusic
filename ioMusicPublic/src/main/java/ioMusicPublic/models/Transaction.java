package ioMusicPublic.models;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Transaction {
	//Primary Key with auto increment
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long transactionId;
	
	LocalDate date;	
	float amount;
	
	//Relation with LessonRequest entity
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "lessonRequestId", referencedColumnName = "requestId")
    private LessonRequest request;
    
    //Getter and Setters
	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public LessonRequest getRequest() {
		return request;
	}

	public void setRequest(LessonRequest request) {
		this.request = request;
	}

	//Default constructor
	public Transaction() {}
}
