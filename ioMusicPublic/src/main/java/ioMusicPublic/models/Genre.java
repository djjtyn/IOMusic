package ioMusicPublic.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Genre {
	//instance variables
	//Primary Key with auto increment
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Short genreId;
	
	@Column(length = 50, unique=true)
	private String genreName;

	//Getters and Setters
	public Short getGenreId() {
		return genreId;
	}

	public void setGenreId(Short genreId) {
		this.genreId = genreId;
	}

	public String getGenreName() {
		return genreName;
	}

	public void setGenreName(String genreName) {
		//First letter is capitalised
		this.genreName = genreName.substring(0,1).toUpperCase() + genreName.substring(1).toLowerCase();
	}
	
	//Default constructor
	public Genre() {}
}
