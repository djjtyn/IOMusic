//This class is used for converting time in the lesson request form to a data type recognised by MySql
package formHelpers;

import java.time.LocalDateTime;


public class TimeFormatter {
	
	String dateString;
	
	//Getters and Setters
	public String getDateString() {
		return dateString;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}


	public LocalDateTime convertStringToDate(String date) {
		LocalDateTime localDate = LocalDateTime.parse(date);
		return localDate;
	}

}
