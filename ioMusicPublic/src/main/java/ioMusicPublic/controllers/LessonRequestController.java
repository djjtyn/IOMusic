package ioMusicPublic.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import formHelpers.TimeFormatter;
import ioMusicPublic.authentication.instructor.InstructorDetails;
import ioMusicPublic.authentication.student.StudentDetails;
import ioMusicPublic.models.Lesson;
import ioMusicPublic.models.LessonRequest;
import ioMusicPublic.models.Student;
import ioMusicPublic.models.instructor.Instructor;
import ioMusicPublic.repositories.CandidateApplicationRepository;
import ioMusicPublic.repositories.InstructorRepository;
import ioMusicPublic.repositories.InstrumentRepository;
import ioMusicPublic.repositories.LessonRepository;
import ioMusicPublic.repositories.LessonRequestRepository;
import ioMusicPublic.repositories.StatusRepository;
import ioMusicPublic.repositories.StudentRepository;
import ioMusicPublic.repositories.VideoToolRepository;

@Controller
public class LessonRequestController {
	
	/***Repository Instantiations***/
	
	//Student Repository
	@Autowired
	StudentRepository studentRepo;
	
	//Instructor Repository
	@Autowired
	InstructorRepository instructorRepo;
	
	//LessonRequest Repository
	@Autowired
	LessonRequestRepository requestRepo;
	
	//Status Repository
	@Autowired
	StatusRepository statusRepo;
	
	//Lesson Repository
	@Autowired
	LessonRepository lessonRepo;
	
	// Instrument Repository
	@Autowired
	InstrumentRepository instrumentRepo;
	
	// VideoTools Repository
	@Autowired
	VideoToolRepository videoToolRepo;
	
	//Candidate Application Repository
	@Autowired
	CandidateApplicationRepository applicationRepo;
	
	
	////If the user is an admin, the method below will display how many candidate applications they have as Pending Approval
	public void setUserNotifications(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			String role = authentication.getAuthorities().toString();
			if(role.equals("[admin]")) {
				Long amount = applicationRepo.countApplicationsPendingApproval();
				System.out.println(amount);
				model.addAttribute("amount", amount);
			}
			if(role.equals("[student]")) {
				//Get how many lesson requests need actioning for the logged in student using their studentId
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
	//The method below will display the request lesson form
	@GetMapping("/student/instructor/{instructorId}/requestLesson")
	public String requestLesson(@PathVariable("instructorId") Long id, Model model, Authentication auth, RedirectAttributes attributes) {
		try {
			//Get the logged in student's details
			StudentDetails details = (StudentDetails) auth.getPrincipal();
			long studentId = details.getStudentId();
			Student student = studentRepo.getById(studentId);
			model.addAttribute("student", student);
			//Get the instructor details and add to the model
			Instructor instructor = instructorRepo.getById(id);
			model.addAttribute("instructor", instructor);
			setUserNotifications(model);
			return "requestLesson";
		} catch(Exception e) {
			String message = "You must be logged into a student account to request a lesson";
			attributes.addFlashAttribute("message", message);
			return "redirect:/student/login";
		}
	}
	
	//The method below creates a Lesson Request with the details entered by the student in the lesson request form
	@PostMapping("/student/instructor/{instructorId}/requestLesson")
	public String requestLesson(@PathVariable("instructorId") Long id, Authentication auth, @RequestParam("dateTime") String dateTime, 
			@RequestParam("duration") Short duration, @RequestParam("instrument") Short instrumentId, @RequestParam("videoTool") Short videoToolId,
			RedirectAttributes attributes) {
		//Get the logged in student's details
		StudentDetails details = (StudentDetails) auth.getPrincipal();
		long studentId = details.getStudentId();
		Student student = studentRepo.getById(studentId);
		//Get the instructor details and add to the model
		Instructor instructor = instructorRepo.getById(id);
		TimeFormatter formatter = new TimeFormatter();
		//Try to create a Lesson Request instance using the student's preferences
		try {
			LessonRequest request = new LessonRequest();
			request.setStudent(student);
			request.setInstructor(instructor);
			request.setDuration(duration);
			request.setCost(request.getDuration() * request.getInstructor().getHourlyRate());
			request.setInstrument(instrumentRepo.getById(instrumentId));
			request.setStatus(statusRepo.getById((short) 1));
			request.setDateTime(formatter.convertStringToDate(dateTime));
			request.setVideoTool(videoToolRepo.getById(videoToolId));
			requestRepo.save(request);
			//Assign the request to the instructor and students list of lesson requests
			student.addRequest(request);
			instructor.addRequest(request);
			//Display the request has been logged
			String message = "Your request has been logged!!";
			attributes.addFlashAttribute("message", message);
		}catch(Exception e) {
			e.printStackTrace();
			//Display the request has not been logged
			String message = "There was a problemt creating that lesson request";
			attributes.addFlashAttribute("message", message);
		}
		return "redirect:/lessonRequests";	
	}
	
	//The method below will display lesson requests
	@GetMapping("lessonRequests")
	public String viewLessonRequests(Model model, Authentication auth) {
		//Determine if the logged in user is a student or instructor
		String role = auth.getAuthorities().toString();
		List<LessonRequest> lessonRequest = new ArrayList<>();
		//If the user is a student
		if(role.equals("[student]")) {
			//Get the logged in STUDENT and their lesson request's
			StudentDetails details = (StudentDetails) auth.getPrincipal();
			long studentId = details.getStudentId();
			Student student = studentRepo.getById(studentId);
			lessonRequest = requestRepo.findByStudent(student);
		}
		//If the user is an instructor
		if(role.equals("[instructor]")) {
			//Get the logged in INSTRUCTOR and their lesson request's
			InstructorDetails details = (InstructorDetails) auth.getPrincipal();
			long instructorId = details.getInstructorId();
			Instructor instructor = instructorRepo.getById(instructorId);
			lessonRequest = requestRepo.findByInstructor(instructor);
		} 	
		//If the user is an admin
		if(role.equals("[admin]")) {
			//Get all lesson requests
			lessonRequest = requestRepo.findAll();
		} 	
		//Check if there are lesson requests
		if(lessonRequest.isEmpty()) {
			model.addAttribute("lessonRequest", "empty");
		}else {
			setUserNotifications(model);
			model.addAttribute("lessonRequest", lessonRequest);
		}
		return "viewRequests";
	}
	
	//The method below deals with an instructor approving a lesson request
	@GetMapping("/lessonRequest/{requestId}/approve")
	public String approveRequest(@PathVariable("requestId") Long id, RedirectAttributes attributes) {
		//Get the lesson request and adjust it's status
		LessonRequest request = requestRepo.getById(id);
		request.setStatus(statusRepo.getById((short) 2));
		requestRepo.save(request);
		String message = "You have approved this request!!";
		attributes.addFlashAttribute("message", message);
		return "redirect:/lessonRequests";
	}
	
	//The method below deals with an instructor declining a lesson request
	@GetMapping("/lessonRequest/{requestId}/decline")
	public String declineRequest(@PathVariable("requestId") Long id, RedirectAttributes attributes) {
		//Get the lesson request and adjust it's status
		LessonRequest request = requestRepo.getById(id);
		request.setStatus(statusRepo.getById((short) 3));
		requestRepo.save(request);
		String message = "You have declined this request!!";
		attributes.addFlashAttribute("message", message);
		return "redirect:/lessonRequests";
	}
	
	//The method below deals with an instructor attaching a video link url to the paid request
	@GetMapping("/lessonRequest/{requestId}/attachVideoLink")
	public String attachVideoLink(@PathVariable("requestId") Long id, Model model, Authentication auth) {
		Instructor instructor;
		//Make sure only the instructor which the lesson has been requested for can access the page
		try {
			InstructorDetails details = (InstructorDetails) auth.getPrincipal();
			instructor = instructorRepo.getById(details.getInstructorId());
		} catch(Exception e) {
			return "/";
		}
		//Get the lesson request and add it to the model if the instructor matches the logged in instructor
		LessonRequest request = requestRepo.getById(id);
		if(instructor == request.getInstructor()) {
			model.addAttribute("request", request);
			setUserNotifications(model);
			return "attachVideoLink";
		} else {
			return "/";
		}
	}
	
	//The method below creates a Lesson using the video call url link attached by the instructor 
	@PostMapping("/lessonRequest/{requestId}/attachVideoLink")
	public String createLessonRecord(@PathVariable("requestId") Long id, @RequestParam("videoLink") String videoLink, RedirectAttributes attributes) {
		String message;
		if(lessonRepo.findByRequest(requestRepo.getById(id)) == null)
			try {
				//Try to create a new Lesson instance using the url link submitted by the instructor
				LessonRequest request = requestRepo.getById(id);
				Lesson lesson = new Lesson();
				lesson.setRequest(request);
				lesson.setVideoCallLink(videoLink);
				lessonRepo.save(lesson);
				request.setLesson(lesson);
				request.setStatus(statusRepo.getById((short) 5));
				requestRepo.save(request);
				 message = "Your video link has been added for this lesson";
			} catch(Exception e) {
				e.printStackTrace();
				message = "Your video link was unable to be added for this lesson";
			}
		else {
			message = "You have already attached a link for this lesson";
		}
		attributes.addFlashAttribute(message);
		return "redirect:/lessonRequests";
	}
}
