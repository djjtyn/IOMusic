//This class will be beneficial if application is expanded for use to users residing outside Ireland to make sure each user see's their own local time

package ioMusicPublic.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TimeZone {
	//Primary Key with auto increment
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Short id;	
	
	private String timeZone;
	private float utcOffset;
	
	//GETTERS AND SETTERS
	public Short getId() {
		return id;
	}

	public void setId(Short id) {
		this.id = id;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public float getUtcOffset() {
		return utcOffset;
	}

	public void setUtcOffset(float utcOffset) {
		this.utcOffset = utcOffset;
	}
	
	//Default constructor
	public TimeZone() {}
}
