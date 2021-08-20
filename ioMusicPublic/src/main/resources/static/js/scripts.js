$(document).ready(function() {
	
	//***LOADING SCREEN***
	//Code below will show the loading gif when the page needs to load
	$(".loadFormButton").on("click", function (){
		$(".loadingArea").css("display", "block");
	})
	
	//***FORMS***/
	//Code below will prevent the student registration form from being submitted before it is validated
	$("#studentRegistrationForm").on("submit", function(event){
		//Make sure password and confirm password inputs match before the form is submitted
		let password = $("#studentPassword").val();
		let passwordConfirm = $("#studentPasswordConfirm").val();
		if(password == passwordConfirm){
			sessionStorage.clear();
			return true;
		}else {
		//If the password's dont match display an error
			$(".invalid-feedback").css("display", "block");
			event.preventDefault();
		}
	})
	
	//Code below will prevent the instructor registration form from being submitted before it is validated
	$("#instructorRegistrationForm").on("submit", function(event){
		//Make sure password and confirm password inputs match before the form is submitted
		let password = $("#candidatePassword").val();
		let passwordConfirm = $("#candidatePasswordConfirm").val();
		if(password == passwordConfirm){
			sessionStorage.clear();
			return true;
		}else {
		//If the password's dont match display an error
			$(".invalid-feedback").css("display", "block");
			event.preventDefault();
		}
	})
	
	//Code below will prevent the lesson request form from being submitted before it is validated
	$("#lessonRequestForm").on("submit", function (event){
		//Make sure the requested date isn't in the past
		let selectedDate = new Date($("#dateTimePicker").val());
		let selectedDateMilliSeconds = selectedDate.getTime();
		let currentDate = Date.now();
		if(currentDate < selectedDateMilliSeconds){
			return true;
		} else {
			event.preventDefault();
			$(".invalid-feedback").text("You have selected a date in the past. Please select a future date for your lesson request");
			$(".invalid-feedback").css("display", "block");
		}
	})
	
	//Code below will prevent the comment form from being submitted before it is validated
	$("#commentForm").on("submit", function (event){
		//Make sure there is a comment before the form is submitted
		let commentText = $(".commentArea").val();
		if(commentText){
			return true
		} else {
			event.preventDefault();
			$(".invalid-feedback").text("You have not entered a comment");
			$(".invalid-feedback").css("display", "block");
		}
	})
	
	

	//Code below will ensure the user doesn't have to re enter their data in the instructor registration form if they navigate to a different page on the instructor registration form
	$("#addGenreBtn").click(function(){
		scrapeInstructorRegistrationForm();
	})
	$("#addInstrumentBtn").click(function(){
		scrapeInstructorRegistrationForm();
	})
	$("#addArtisttBtn").click(function(){
		scrapeInstructorRegistrationForm();
	})
	$("#addPlatformBtn").click(function(){
		scrapeInstructorRegistrationForm();
	})
	

	//***PROFILE EDITS***
	//Code below will prevent the password change form being submitted if the two passwords provided dont match
	$("#passwordChangeForm").on("submit", function (event){
		let passwordOne = $("#editPassword").val();
		let passwordTwo = $("#editPasswordConfirm").val();
		if(passwordOne == passwordTwo){
			return true;
		} else {
			event.preventDefault();
			$(".invalid-feedback").text("Password's provided do not match");
			$(".invalid-feedback").css("display", "block");
		}
	})
	
	//Code below will prevent the description change form being submitted if the description length is above 2600 characters
	$("#descriptionChangeForm").on("submit", function (event){
		let descriptionLength = $("#editDescription").val().length;
		if(descriptionLength <= 2600){
			return true;
		} else {
			event.preventDefault();
			$(".invalid-feedback").text("Your description can only have a maximum length of 2600 characters. The description provided is " + 
										descriptionLength + " characters in length");
			$(".invalid-feedback").css("display", "block");
		}
	})
	
	//Code below will display what the instructors video platform tools will be replaced with when they submit the edit video tools form
	$("#editPlatformform").on("change", function(){
		let selected = new Array();
		$("#editVideoTools option:selected").each(function(){
			selected.push(" " + this.text);
		})
		$(".listSelected").text("Your video calling platform(s) will now be listed as:" + selected);	
	})
	
	//Code below will display what the instructors favourite artists will be replaced with when they submit the edit artis form
	$("#editArtistform").on("change", function(){
		let selected = new Array();
		$("#editArtists option:selected").each(function(){
			selected.push(" " + this.text);
		})
		$(".listSelected").text("Your favourite artists will now be listed as:" + selected);	
	})
	
	//Code below wil ldisplay an alert before an instuctor deletes their profile
	$("#deleteAccountButton").on("click", function(event){
		if(confirm("If you click OK your account will be removed completely from our database.\n" +
		"To become an instructor again you will have to register again\nClicking cancel will not delete your account")){
			return true;
		} else {
			return false;
		}
	})
		
	//***Instructor FIltering***/
	//Code below will allow the instructor search functionality to be invoked when the enter key is pressed
	$("#searchQuery").keypress(function(key){
		if(key.keyCode == 13){
			//Remove the select input if the user is searching using the search bar
			$("#filterInstrument").remove();
			$("#filterInstructorForm").submit();
		}
	})
	
	//Code below will allow the instructor instrument filter form to be submitted when a instrument is selected
	$("#filterInstrument").on("change", function(){
		$("#filterInstructorForm").submit();
	})
	
	//Code below will display the loading gif when the filter form is submitted
	$("#filterInstructorForm").on("submit", function(){
		$(".loadingArea").css("display", "block");
	})
});

//The method below will be used to populate the instructor registration forms using data stored in the session data
function populateForm(){
	document.getElementById("candidateFirstName").value = sessionStorage.getItem("candidateFirstName");
	document.getElementById("candidateLastName").value = sessionStorage.getItem("candidateLastName");
	document.getElementById("candidateEmail").value = sessionStorage.getItem("candidateEmail");
	if(sessionStorage.getItem("candidateGenre") != null){	
		document.getElementById("candidateGenre").value = sessionStorage.getItem("candidateGenre");
	}
	document.getElementById("rate").value = sessionStorage.getItem("rate");
}

function scrapeInstructorRegistrationForm(){
		let candidateFirstName = document.getElementById("candidateFirstName").value;
		sessionStorage.setItem("candidateFirstName", candidateFirstName);
		let candidateLastName = document.getElementById("candidateLastName").value;
		sessionStorage.setItem("candidateLastName", candidateLastName);
		let candidateEmail = document.getElementById("candidateEmail").value;
		sessionStorage.setItem("candidateEmail", candidateEmail);
		let candidateGenre = document.getElementById("candidateGenre").value;
		sessionStorage.setItem("candidateGenre", candidateGenre);
		let rate = document.getElementById("rate").value;
		sessionStorage.setItem("rate", rate);
}

function adjustEmail() {
	let emailArea = document.getElementById("emailEdit");
	emailArea.style.display = "block";
}

function adjustPassword() {
	let passwordArea = document.getElementById("passwordEdit");
	passwordArea.style.display = "block";
}

//The method below will allow a form to be displayed when a student clicks the 'Leave Comment' button on an instructors profile
/*function displayCommentForm(instructorId){
	//Create a form
	let form = document.createElement("form");
	let formAction = "/postComment";
	form.setAttribute("action", formAction);
	form.setAttribute("method", "get");
	//Create the texarea
	let div = document.createElement("div");
	div.setAttribute("class", "form-group");
	let textArea = document.createElement("textarea");
	textArea.style.width = "100%";

	textArea.placeholder = "Leave a Comment";
	div.append(textArea);
	form.append(div);
	//Create the submit button
	let divTwo = document.createElement("div");
	divTwo.setAttribute("class", "requestButtons");
	let submitButton = document.createElement("input");
	submitButton.setAttribute("type", "submit");
	submitButton.setAttribute("class", "validButton");
	divTwo.append(submitButton);
	form.append(divTwo);
	//Create a hidden input to store the instructorId
	let instructor = document.createElement("input");
	instructor.setAttribute("type", "hidden");
	instructor.setAttribute("name", "instructorId")
	instructor.setAttribute("value", instructorId);
	form.append(instructor);
	let formArea = document.getElementById("commentFormArea");
	formArea.append(form);
}*/