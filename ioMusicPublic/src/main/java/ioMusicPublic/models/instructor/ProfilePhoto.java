package ioMusicPublic.models.instructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class ProfilePhoto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long photoId;
	
	@Column(nullable = false)
	private String imageUrl;
	
	@OneToOne
	@JoinColumn(name = "instructorId")
	private Instructor instructor;

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Instructor getInstructor() {
		return instructor;
	}

	public void setInstructor(Instructor instructor) {
		this.instructor = instructor;
	}

}
