package ioMusicPublic.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ioMusicPublic.authentication.instructor.CandidateDetails;
import ioMusicPublic.authentication.instructor.InstructorDetails;
import ioMusicPublic.authentication.student.StudentDetails;
import ioMusicPublic.models.Comment;
import ioMusicPublic.models.Instrument;
import ioMusicPublic.models.Student;
import ioMusicPublic.models.instructor.Instructor;
import ioMusicPublic.models.instructorCandidate.InstructorCandidate;
import ioMusicPublic.repositories.CandidateApplicationRepository;
import ioMusicPublic.repositories.CommentRepository;
import ioMusicPublic.repositories.InstructorCandidateRepository;
import ioMusicPublic.repositories.InstructorInstrumentRepository;
import ioMusicPublic.repositories.InstructorRepository;
import ioMusicPublic.repositories.InstrumentRepository;
import ioMusicPublic.repositories.LessonRequestRepository;
import ioMusicPublic.repositories.ProfilePhotoRepository;
import ioMusicPublic.repositories.StudentRepository;


@Controller
public class GenericController {
	
	///***Repository Instantiations***/

	//InstructorCandidate Repository
	@Autowired
	InstructorCandidateRepository candidateRepo;
	
	//Instructor Repository
	@Autowired
	InstructorRepository instructorRepo;
	
	//Student Repository
	@Autowired
	StudentRepository studentRepo;
	
	//InstructorInstrument Repository
	@Autowired
	InstructorInstrumentRepository instructorInstrumentRepo;
	
	//Instrument Repository
	@Autowired
	InstrumentRepository instrumentRepo;
	
	//Comment Repository
	@Autowired
	CommentRepository commentRepo;
	
	//Lesson request repo
	@Autowired
	LessonRequestRepository requestRepo;
	
	//Candidate Application Repository
	@Autowired
	CandidateApplicationRepository applicationRepo;
	
	//The method below will display how many things need actioning by the logged in user
	public void setUserNotifications(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			String role = authentication.getAuthorities().toString();
			if(role.equals("[admin]")) {
				//Get how many candidate applications need actioning
				Long amount = applicationRepo.countApplicationsPendingApproval();
				if(amount>=1) {
					model.addAttribute("amount", amount);
				}
			}
			if(role.equals("[student]")) {
				//Get how many lesson requests need actioning
				StudentDetails details = (StudentDetails) authentication.getPrincipal();
				long studentId = details.getStudentId();
				Long amount = requestRepo.countRequestsRequiringStudentAction(studentId);
				if(amount>=1) {
					model.addAttribute("amount", amount);
				}
			}
			if(role.equals("[instructor]")) {
				//Get how many lesson requests need actioning for the logged in instructor using their id
				InstructorDetails details = (InstructorDetails) authentication.getPrincipal();
				long instructorId = details.getInstructorId();
				Long amount = requestRepo.countRequestsRequiringInstructorAction(instructorId);
				if(amount>=1) {
					model.addAttribute("amount", amount);
				}
			}
		}
	}
	
	//The method below will display the web applications home page
	@GetMapping("/")
	public String displayHomePage(Model model, HttpServletRequest request) {
		//There will be 5 unique randomly selected instruments retrieved uisng the instruments from the instructor instrument repo
		List<String> instrumentList = new ArrayList<>();
		List<Short> instrumentIdList = instructorInstrumentRepo.getRandomInstruments();
		//For each instrument id retrieved add that instrument to the instrumentList
		for(Short i: instrumentIdList){
			instrumentList.add(instrumentRepo.getById(i).getName());
		}
		model.addAttribute("adminUser", request.getAuthType());
		if(instrumentList.size()>0) {
			model.addAttribute("instruments", instrumentList);
		}
		setUserNotifications(model);
		return "index";
	}
	
	
	//***LOGIN***
	
	//The method below will display the student log in page
	@GetMapping("/student/login")
	public String displayStudentLoginForm(Model model) {
		//Check if user is already logged in
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		//If the user is not logged in show the log in page
		if(authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return "loginPages/studentLogin";
		} else {
		//If the user is logged in, show the home page
			return "redirect:/";
		}
	}
	
	//The method below will display the log in page for instructor's and instructor candidates
	@GetMapping("/instructor/login")
	public String displayInstructorLoginForm(Model model) {
		//Check if user is already logged in
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		//If the user is not logged in show the log in page
		if(authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return "loginPages/instructorLogin";
		} else {
		//If the user is logged in, show the home page
			return "redirect:/";
		}
	}
	
	////The method below will display the admin log in page
	@GetMapping("/admin/login")
	public String displayAdminLoginForm(Model model) {
		//Check if user is already logged in
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		//If the user is not logged in show the log in page
		if(authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return "loginPages/adminLogin";
		} else {
		//If the user is logged in, show the home page
			return "redirect:/";
		}
	}
	
	//The method below will allow a instructor candidate to see their own application
	@GetMapping("/candidate/viewApplication")
	public String viewApplication(Model model, Authentication auth) {
		//Get the logged in candidate
		CandidateDetails details = (CandidateDetails) auth.getPrincipal();
		Integer candidateId = details.getId();
		System.out.println(candidateId);
		InstructorCandidate candidate = candidateRepo.getById(candidateId);
		model.addAttribute("candidate", candidate);
		return "viewApplication";
	}

	
	///***View Instructors**
	//The method below will display the page listing the instructors
	@GetMapping("/viewInstructors")
	public String viewInstructors(Model model, @RequestParam Optional<Integer> pageNumber) {
		//Show 3 instructors per page
		int instructorsPerPage = 5;
		int maximumPage;
		Page<Instructor> instructors = instructorRepo.findAll(PageRequest.of(pageNumber.orElse(1),instructorsPerPage));
		long amountOfInstructors = instructorRepo.count();
		//Get the amount of pages based on each page showing 5 instructors
		if(amountOfInstructors > 0 && amountOfInstructors % 2 == 0) {
			maximumPage = (int) (amountOfInstructors / instructorsPerPage);
		} else {
			maximumPage = (int) ((amountOfInstructors / instructorsPerPage) + 1);
		}
		//If the page number is out of bounds for the existing instructor amount redirect user to the error page
		if( pageNumber.isPresent() && (pageNumber.get() < 0|| pageNumber.get() > maximumPage)) {
			return "error";
		}
		if(instructors.isEmpty()) {
			model.addAttribute("instructors", "emptyRepo");
		} else {
			List<Instrument> instruments = new ArrayList<>();
			List<Short> instrumentIds = instructorInstrumentRepo.getAllInstruments();
			for(Short id: instrumentIds) {
				instruments.add(instrumentRepo.getById(id));
			}
			model.addAttribute("instruments", instruments);
			model.addAttribute("instructors", instructors);
			model.addAttribute("pageAmount", maximumPage);
			model.addAttribute("currentPage", pageNumber.get());
		}
		setUserNotifications(model);
		return "instructors";
	}
	
	//The method below will allow users to search for an instructor
	@PostMapping("viewInstructors")
	public String applySearchFilter(Model model, @RequestParam("searchQuery") Optional<String> searchQuery, @RequestParam("filterInstrumentId") Optional<Long> instrumentId) {
		String message;
		String instrumentName = "";
		List<Instructor> instructors = new ArrayList<>();
		int matchAmount;
		List<Instrument> instruments = new ArrayList<>();
		List<Short> instrumentIds = instructorInstrumentRepo.getAllInstruments();
		for(Short id: instrumentIds) {
			instruments.add(instrumentRepo.getById(id));
		}
		model.addAttribute("instruments", instruments);
		//If the user provides an instructor name to search with
		if(searchQuery.isPresent() && !searchQuery.get().equals("")) {
			System.out.println("Test: " + searchQuery.get());
			 instructors = instructorRepo.searchByQuery(searchQuery.get());
		} else if(instrumentId.isPresent()){
			//Get the instructor id's which have that instrument listed, traverse the list and for each loop value get the matching instructor id instance
			for(Long instructorId: instructorInstrumentRepo.getInstructorIdForInstrument(instrumentId.get())) {
				instructors.add(instructorRepo.getById(instructorId));
			}	
		}
		matchAmount = instructors.size();
		//If a match has been found
		if(matchAmount > 0) {
			model.addAttribute("instructors", instructors);
			if (matchAmount == 1) {
				message = matchAmount + " match found for your search";
			} else {
				message = matchAmount + " matches found for your search";
			}
		} else {
			message = "No matches found for your search";
		}
		model.addAttribute("searchResult", message);
		setUserNotifications(model);
		return "instructors";
	}
	
	//The method below will display a instructors profile page
	@GetMapping("viewInstructors/profile/{instructorId}")
	public String viewInstructorProfile(@PathVariable("instructorId") Long id, Model model) {
		//Get the Instructor's instance
		Instructor instructor = instructorRepo.getById(id);
		//Get the instructors comments
		List<Comment> comments = instructor.getComments();
		if(comments.isEmpty()) {
			model.addAttribute("comments", "emptyRepo");
		} else {
			model.addAttribute("comments", comments);
		}
		model.addAttribute("instructor", instructor);	
		setUserNotifications(model);
		return "instructorProfile";
	}	
	
	//The method below will create a comment for an instructor that has been left by a student
	@PostMapping("/postComment")
	public String postComment(Authentication auth, RedirectAttributes attributes, @RequestParam("instructorId") Long instructorId, 
								@RequestParam("comment") String comment, Model model) {
		String message;
		//Check that the user is a logged in student
		String role = auth.getAuthorities().toString();
		//If the user is a student, get their details, the instructors details and the comment they posted to create a Comment instance
		if(role.equals("[student]")) {
			try {
				//Get student and instructor details
				StudentDetails details = (StudentDetails) auth.getPrincipal();
				long studentId = details.getStudentId();
				Student student = studentRepo.getById(studentId);
				Instructor instructor = instructorRepo.getById(instructorId);
				//Instantiate a new comment and set it's variable values
				Comment newComment = new Comment();
				newComment.setCommentText(comment);
				newComment.setCreationDate(LocalDate.now());
				newComment.setInstructor(instructor);
				newComment.setStudent(student);
				commentRepo.save(newComment);
				student.addComment(newComment);
				instructor.addComment(newComment);;
				studentRepo.save(student);
				instructorRepo.save(instructor);
				message = "Your comment has been posted";
				attributes.addFlashAttribute("message", message);
				return "redirect:/viewInstructors/profile/" + instructorId;
			} catch(Exception e) {
				e.printStackTrace();
				return "index";
			}		
		} else {
			message = "You need to be logged in as a student ot post a comment";
			attributes.addFlashAttribute("message", message);
			return "redirect:/student/login";
		}
	}
}
