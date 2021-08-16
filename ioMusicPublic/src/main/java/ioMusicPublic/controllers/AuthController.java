//This controller deals with registration of instructor candidates and students
package ioMusicPublic.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import formHelpers.ArtistList;
import formHelpers.InstrumentList;
import formHelpers.VideoToolList;
import ioMusicPublic.models.Admin;
import ioMusicPublic.models.Artist;
import ioMusicPublic.models.Genre;
import ioMusicPublic.models.Instrument;
import ioMusicPublic.models.Status;
import ioMusicPublic.models.Student;
import ioMusicPublic.models.TimeZone;
import ioMusicPublic.models.VideoTool;
import ioMusicPublic.models.instructorCandidate.CandidateApplication;
import ioMusicPublic.models.instructorCandidate.CandidateFavouriteArtist;
import ioMusicPublic.models.instructorCandidate.CandidateInstrument;
import ioMusicPublic.models.instructorCandidate.CandidateVideoTools;
import ioMusicPublic.models.instructorCandidate.InstructorCandidate;
import ioMusicPublic.repositories.AdminRepository;
import ioMusicPublic.repositories.ArtistRepository;
import ioMusicPublic.repositories.CandidateApplicationRepository;
import ioMusicPublic.repositories.CandidateFavouriteArtistRepository;
import ioMusicPublic.repositories.CandidateInstrumentRepository;
import ioMusicPublic.repositories.CandidateVideoToolsRepository;
import ioMusicPublic.repositories.GenreRepository;
import ioMusicPublic.repositories.InstructorCandidateRepository;
import ioMusicPublic.repositories.InstrumentRepository;
import ioMusicPublic.repositories.StatusRepository;
import ioMusicPublic.repositories.StudentRepository;
import ioMusicPublic.repositories.TimeZoneRepository;
import ioMusicPublic.repositories.VideoToolRepository;

@Controller
public class AuthController {

	/// ***REPOSITORIES***

	// TimeZone Repository
	@Autowired
	TimeZoneRepository timeZoneRepo;

	// Student Repository
	@Autowired
	StudentRepository studentRepo;

	// Artist Repository
	@Autowired
	ArtistRepository artistRepo;

	// Genre Repository
	@Autowired
	GenreRepository genreRepo;

	// Instrument Repository
	@Autowired
	InstrumentRepository instrumentRepo;

	// VideoTools Repository
	@Autowired
	VideoToolRepository videoToolRepo;

	// InstructorCandidate Repository
	@Autowired
	InstructorCandidateRepository candidateRepo;

	// Admin Repository
	@Autowired
	AdminRepository adminRepo;

	// Status Repository
	@Autowired
	StatusRepository statusRepo;

	// CandidateApplication Repository
	@Autowired
	CandidateApplicationRepository candidateApplicationRepo;

	// CandidateFavourieArtist Repository
	@Autowired
	CandidateFavouriteArtistRepository candidateFavouriteArtistRepo;

	// CandidateInstrument Repository
	@Autowired
	CandidateInstrumentRepository candidateInstrumentRepo;

	// CandidateVideoTools Repository
	@Autowired
	CandidateVideoToolsRepository candidateVideoToolsRepo;
	
	//**LOGIN ERROR HANDLING***
	//The method below will display a login error for incorrect student login
	@GetMapping("student/loginFailure")
	public String displayStudentLoginError(RedirectAttributes attributes) {
		String message = "Login Failed. Are you sure your using the correct Login Portal for your account? (Student, Instructor, Admin)";
		attributes.addFlashAttribute("message", message);
		return "redirect:/student/login";
	}
	
	//The method below will display a login error for incorrect instructor/instructor candidate login
	@GetMapping("instructor/loginFailure")
	public String displayInstructorLoginError(RedirectAttributes attributes) {
		String message = "Login Failed. Are you sure your using the correct Login Portal for your account? (Student, Instructor, Admin)";
		attributes.addFlashAttribute("message", message);
		return "redirect:/instructor/login";
	}
	
	//The method below will display a login error for incorrect instructor/instructor candidate login
	@GetMapping("admin/loginFailure")
	public String displayAdminLoginError(RedirectAttributes attributes) {
		String message = "Login Failed. Are you sure your using the correct Login Portal for your account? (Student, Instructor, Admin)";
		attributes.addFlashAttribute("message", message);
		return "redirect:/admin/login";
	}
	
	
	//***REGISTRATION***
	// The method below will display the web applications registration options
	// (student or instructor)
	@GetMapping("/register")
	public String displayRegistrationOptions() {
		// Check if user is already logged in
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		// If the user is not logged in show the registration options page
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return "registrationOptions";
		} else {
			// If the user is logged in, show the home page
			return "redirect:/";
		}
	}

	// ***STUDENT REGISTRATION***

	// The method below will display the web applications student registration form
	@GetMapping("/register/student")
	public String displayStudentRegistrationForm(Model model) {
		// Check if user is already logged in
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		// If the user is not logged in show the student registration form page
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			// Create a list that displays all the time zones available to choose from
			List<TimeZone> timeZoneList = timeZoneRepo.findAll();
			model.addAttribute("timeZoneList", timeZoneList);
			return "studentRegistrationForm";
		} else {
			// If the user is logged in, show the home page
			return "redirect:/";
		}
	}
	
	//The method below will create a student record based on the values entered in the student registration form
	@PostMapping("/register/student/new")
	public String registerStudent(@RequestParam("studentFirstName") String firstName,@RequestParam("studentLastName") String lastName, 
			@RequestParam("studentEmail") String email, @RequestParam("password") String password, @RequestParam("timeZone") Short timeZone, RedirectAttributes attributes) {
		//Encrypt the students chosen password using BcryptPasswordEncoder
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		password = passwordEncoder.encode(password);
		String message;
		//Try to create a student record using details from the student registration form
		try {	
			Student student = new Student();
			student.setFirstName(firstName);
			student.setLastName(lastName);
			student.setEmail(email);
			student.setPassword(password);
			student.setUserTimeZone(timeZoneRepo.getById(timeZone));
			studentRepo.save(student);
			message = "Your student account has been created";
		} catch(Exception e) {
			e.printStackTrace();
			message = "There was an issue creating your account";
		}
		attributes.addFlashAttribute("message", message);
		return "redirect:/student/login";
	}

	//***INSTRUCTOR REGISTRATION***
	// The method below will display the web applications instructor registration
	// form
	@GetMapping("/register/instructor")
	public String displayInstructorRegistrationForm(Model model) {
		// Check if user is already logged in
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		// If the user is not logged in show the instructor registration form page
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			// Instantiate the ArtistList class which the candidate can populate with multiple artists they select
			model.addAttribute("favouriteArtists", new ArtistList());
			// Instantiate the InstrumentList class which the candidate can populate with multiple artists they select
			model.addAttribute("instruments", new InstrumentList());
			// Instantiate the VideoToolList class which the candidate can populate with multiple video tools they select
			model.addAttribute("videoTools", new VideoToolList());
			// Create a list that displays all the time zones available to choose from
			List<TimeZone> timeZoneList = timeZoneRepo.findAll();
			model.addAttribute("timeZoneList", timeZoneList);
			// Create a list that displays all the artists available to choose from
			List<Artist> artistList = artistRepo.findAll();
			// Check if there are artists already in the repo
			if (artistList.isEmpty()) {
				model.addAttribute("artistList", "emptyRepo");
			} else {
				model.addAttribute("artistList", artistList);
			}
			// Create a list that displays all the genres available to choose from
			List<Genre> genreList = genreRepo.findAll();
			model.addAttribute("genreList", genreList);
			// Create a list that displays all the instruments available to choose from
			List<Instrument> instrumentList = instrumentRepo.findAll();
			// Check if there are instrument's already in the repo
			if (instrumentList.isEmpty()) {
				model.addAttribute("instrumentList", "emptyRepo");
			} else {
				model.addAttribute("instrumentList", instrumentList);
			}
			// Create a list that displays all the videoTools available to choose from
			List<VideoTool> videoToolList = videoToolRepo.findAll();
			// Check if there are video tools in the videotool repo
			if (videoToolList.isEmpty()) {
				model.addAttribute("videoToolList", "emptyRepo");
			} else {
				model.addAttribute("videoToolList", videoToolList);
			}
			return "instructorRegistrationForm";
		} else {
			// If the user is logged in, show the home page
			return "redirect:/";
		}
	}

	// The method below creates an InstructorCandidate instance using the details
	// provided by the user in the instructor registration form
	@PostMapping("/register/instructorCandidate/new")
	public String registerInstructorCandidate(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, @RequestParam("email") String email, 
			@RequestParam("password") String password, @RequestParam("timeZone") Short timeZone, @RequestParam("genre") Short genre, @RequestParam("rate") float rate, 
			@RequestParam("description") String description, ArtistList artists, InstrumentList instruments, VideoToolList videoTools, RedirectAttributes attributes) {
		// Encrypt the candidate's chosen password using BcryptPasswordEncoder
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		password = passwordEncoder.encode(password);
		String message;
		//Try to create a InstructorCandidate record using details from the instructor registration form
		InstructorCandidate candidate = new InstructorCandidate();
		try {	
			candidate.setFirstName(firstName);
			candidate.setLastName(lastName);
			candidate.setEmail(email);
			candidate.setPassword(password);
			candidate.setUserTimeZone(timeZoneRepo.getById(timeZone));
			candidate.setGenreId(genreRepo.getById(genre));
			candidate.setHourlyRate(rate);
			candidate.setDescription(description);
			// Save the candidate to the repo
			candidateRepo.save(candidate);
			//Create a CandidateApplication for the candidate
			CandidateApplication application = new CandidateApplication();
			application.setCandidate(candidate);
			// Set the applications creationDate
			application.setCreationDate(LocalDate.now());
			// Set the status for the application as 'Pending Approval' (statusID 1)
			Status status = statusRepo.getById((short) 1);
			application.setStatus(status);
			// Set the applications admin
			Admin admin = adminRepo.getById((short) 1);
			application.setAdmin(admin);
			candidateApplicationRepo.save(application);
			// Add the application to the admin's list of applications
			admin.addApplication(application);
			// Add the application to the candidates application variable
			candidate.setApplication(application);
			// Create records in the CandidateFavouriteArtists table using the candidate and their selected artists
			for (Artist artist : artists) {
				CandidateFavouriteArtist selectedArtist = new CandidateFavouriteArtist();
				selectedArtist.setArtist(artist);
				selectedArtist.setCandidate(candidate);
				// Save each favourite artist to the candidate favourite artist repo
				candidateFavouriteArtistRepo.save(selectedArtist);
			}
			// Create records in the CandidateInstrument table using the candidate and their selected instruments
			for (Instrument instrument : instruments) {
				CandidateInstrument selectedInstrument = new CandidateInstrument();
				selectedInstrument.setInstrument(instrument);
				selectedInstrument.setCandidate(candidate);
				// Save each instrument to the candidate instrument repo
				candidateInstrumentRepo.save(selectedInstrument);
			}
			// Create records in the CandidateVideoTools table using the candidate and their selected video tools
			for (VideoTool videoTool : videoTools) {
				CandidateVideoTools selectedVideoTool = new CandidateVideoTools();
				selectedVideoTool.setVideoTool(videoTool);
				selectedVideoTool.setCandidate(candidate);
				// Save each video tool to the candidate video tools repo
				candidateVideoToolsRepo.save(selectedVideoTool);
			}
			candidateApplicationRepo.save(application);
			message = "The details provided in the Instructor Registration Form will be viewed by an admin who will approve/decline your registration. Plase note, you can "
					+ "sign in to view your application status using the Instructor Login Page but you will not be considered an instructor unless approved by an admin";
		} catch(Exception e) {
			e.printStackTrace();
			candidateRepo.delete(candidate);
			message = "There was an issue creating your account";
		}
		attributes.addFlashAttribute("message", message);
		return "redirect:/instructor/login";
	}
}
