//This controller deals with functions that allow candidates to be approved or declined by admin users

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ioMusicPublic.models.Comment;
import ioMusicPublic.models.instructor.Instructor;
import ioMusicPublic.models.instructor.InstructorFavouriteArtist;
import ioMusicPublic.models.instructor.InstructorInstrument;
import ioMusicPublic.models.instructor.InstructorVideoTools;
import ioMusicPublic.models.instructorCandidate.CandidateApplication;
import ioMusicPublic.models.instructorCandidate.CandidateFavouriteArtist;
import ioMusicPublic.models.instructorCandidate.CandidateInstrument;
import ioMusicPublic.models.instructorCandidate.CandidateVideoTools;
import ioMusicPublic.models.instructorCandidate.InstructorCandidate;
import ioMusicPublic.repositories.CandidateApplicationRepository;
import ioMusicPublic.repositories.CandidateFavouriteArtistRepository;
import ioMusicPublic.repositories.CandidateInstrumentRepository;
import ioMusicPublic.repositories.CandidateVideoToolsRepository;
import ioMusicPublic.repositories.CommentRepository;
import ioMusicPublic.repositories.InstructorCandidateRepository;
import ioMusicPublic.repositories.InstructorFavouriteArtistRepository;
import ioMusicPublic.repositories.InstructorInstrumentRepository;
import ioMusicPublic.repositories.InstructorRepository;
import ioMusicPublic.repositories.InstructorVideoToolsRepository;
import ioMusicPublic.repositories.StatusRepository;

@Controller
public class AdminController {

	/// ***REPOSITORIES***

	// CandidateApplication Repository
	@Autowired
	CandidateApplicationRepository candidateApplicationRepo;

	// InstructorCandidate Repository
	@Autowired
	InstructorCandidateRepository candidateRepo;

	// Status Repository
	@Autowired
	StatusRepository statusRepo;

	// Instructor Repository
	@Autowired
	InstructorRepository instructorRepo;

	// CandidateVideoTools Repository
	@Autowired
	CandidateVideoToolsRepository candidateVideoToolsRepo;

	// InstructorVideoTools Repository
	@Autowired
	InstructorVideoToolsRepository instructorVideoToolsRepo;

	// CandidateFavourieArtist Repository
	@Autowired
	CandidateFavouriteArtistRepository candidateFavouriteArtistRepo;

	// InstructorFavourieArtist Repository
	@Autowired
	InstructorFavouriteArtistRepository instructorFavouriteArtistRepo;

	// CandidateInstrument Repository
	@Autowired
	CandidateInstrumentRepository candidateInstrumentRepo;

	// InstructorInstrument Repository
	@Autowired
	InstructorInstrumentRepository instructorInstrumentRepo;
	
	//COmment Repository
	@Autowired
	CommentRepository commentRepo;
	
	////The method below will display how many candidate applications the admins have as Pending Approval
	public void setUserNotifications(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			String role = authentication.getAuthorities().toString();
			if(role.equals("[admin]")) {
				Long amount = candidateApplicationRepo.countApplicationsPendingApproval();
				System.out.println(amount);
				model.addAttribute("amount", amount);
			}
		}
	}

	// The method below will display the web applications instructor candidates
	@GetMapping("/admin/viewInstructorCandidates")
	public String viewInstructorCandidates(Model model, Authentication auth, RedirectAttributes attributes) {
		//Retrieve all candidate applications if the user is a logged in user
		String role = auth.getAuthorities().toString();
		String message;
		if(role.equals("[admin]")) {
			List<CandidateApplication> candidateApplications = candidateApplicationRepo.findAll();
			// Check if the candidateAPplicationRepo is empty
			if (candidateApplications.isEmpty()) {
				model.addAttribute("candidateApplications", "emptyRepo");
			} else {
				setUserNotifications(model);
				model.addAttribute("candidateApplications", candidateApplications);
			}
			return "instructorCandidates";
		} else {
			message = "The page your looking for may not exist. Please log in and try again";
			attributes.addFlashAttribute("message", message);
			return "redirect:/";
		}
	}

	// The method below will allow an admin to approve an instructor candidate
	@GetMapping("/admin/instructorCandidate/approve/{candidateId}")
	public String approveCandidate(@PathVariable("candidateId") Integer id, Authentication auth, RedirectAttributes attributes) {
		//Check that the user is a logged in admin
		String role = auth.getAuthorities().toString();
		if(role.equals("[admin]")) {
			String message;
			try {
				// Create an new Instructor instance
				Instructor instructor = new Instructor();
				// Get the approved candidate
				InstructorCandidate candidate = candidateRepo.getById(id);
				// Get the candidates application
				CandidateApplication application = candidate.getApplication();
				// Set instructor first name
				instructor.setFirstName(candidate.getFirstName());
				// Set instructor last name
				instructor.setLastName(candidate.getLastName());
				// Set instructor password
				instructor.setPassword(candidate.getPassword());
				// Set instructor email
				instructor.setEmail(candidate.getEmail());
				// Set instructor description
				instructor.setDescription(candidate.getDescription());
				// Set instructor hourly rate
				instructor.setHourlyRate(candidate.getHourlyRate());
				// Set instructor Genre
				instructor.setGenreId(candidate.getGenreId());
				// Set instructor time zone
				instructor.setUserTimeZone(candidate.getUserTimeZone());
				instructorRepo.save(instructor);
				// The arraylist's below will be used to map records from the InstructorFavouriteArtist, InstructorVideoTools and InstructorInstrument to the Instructor
				List<InstructorVideoTools> videoToolsList = new ArrayList<>();
				List<InstructorFavouriteArtist> artistList = new ArrayList<>();
				List<InstructorInstrument> instrumentList = new ArrayList<>();
				// Set instructor video tools
				for (CandidateVideoTools videoTool : candidate.getVideoTools()) {
					// Create a new InstructorVideoTools instance
					InstructorVideoTools instructorVideoTools = new InstructorVideoTools();
					instructorVideoTools.setVideoTool(videoTool.getVideoTool());
					instructorVideoTools.setInstructor(instructor);
					videoToolsList.add(instructorVideoTools);
					// Add the record to the instructorVideoTools repo and remove from the CandidateVideoTools repo
					instructorVideoToolsRepo.save(instructorVideoTools);
					candidateVideoToolsRepo.delete(videoTool);
				}
				instructor.setVideoTools(videoToolsList);
				// Set instructor favourite artist
				for (CandidateFavouriteArtist artist : candidate.getFavouriteArtists()) {
					// Create a new InstructorFavouriteArtist instance
					InstructorFavouriteArtist favouriteArtists = new InstructorFavouriteArtist();
					favouriteArtists.setArtist(artist.getArtist());
					favouriteArtists.setInstructor(instructor);
					artistList.add(favouriteArtists);
					// Add the record to the instructorFavouriteArtist repo and remove from the CandidateFavouriteArtist repo
					instructorFavouriteArtistRepo.save(favouriteArtists);
					candidateFavouriteArtistRepo.delete(artist);
				}
				instructor.setFavouriteArtists(artistList);
				// Set instructor instruments
				for (CandidateInstrument instrument : candidate.getInstruments()) {
					// Create a new InstructorInstrument instance
					InstructorInstrument instruments = new InstructorInstrument();
					instruments.setInstrument(instrument.getInstrument());
					instruments.setInstructor(instructor);
					instrumentList.add(instruments);
					// Add the record to the instructorInstrument repo and remove from the CandidateInstrument repo
					instructorInstrumentRepo.save(instruments);
					candidateInstrumentRepo.delete(instrument);
				}
				instructor.setInstruments(instrumentList);
				// Remove the application from the application repo
				candidateApplicationRepo.delete(application);
				// Remove the candidate from the candidate repo
				candidateRepo.delete(candidate);
				message = "You have successfully approved " + instructor.getFirstName();
			} catch (Exception e) {
				e.printStackTrace();
				message = "There was an issue approving this candidate";
			}
			attributes.addFlashAttribute("message", message);
			return "redirect:/admin/viewInstructorCandidates";
		}else {
			return "error";
		}
	}

	// The method below will allow an admin to decline an instructor candidate
	@GetMapping("/admin/instructorCandidate/decline/{candidateId}")
	public String declineCandidate(@PathVariable("candidateId") Integer id, Authentication auth, RedirectAttributes attributes) {
		//Check that the user is a logged in admin
		String role = auth.getAuthorities().toString();
		if(role.equals("[admin]")) {
			// Find the candidates application
			CandidateApplication application = candidateApplicationRepo.getByCandidate(candidateRepo.getById(id));
			// Change the application status to declined
			application.setStatus(statusRepo.getById((short) 3));
			candidateApplicationRepo.save(application);
			String message = "You have declined " + application.getCandidate().getFirstName() + "'s application";
			attributes.addFlashAttribute("message", message);
			return "redirect:/admin/viewInstructorCandidates";
		}else {
			return "error";
		}
	}
	
	//The method below will allow an admin to delete an instructors comment
	@GetMapping("instructor/profile/deleteComment/{commentId}")
	public String deleteComment(Authentication auth, @PathVariable("commentId") Short commentId, RedirectAttributes attributes) {
		//Only allow if the logged in user is an admin
		String message;
		//Check that the user is a logged in admin
		String role = auth.getAuthorities().toString();
		Comment comment = commentRepo.getById(commentId);
		//Get the instructor Id to redirect back to the instructors profile
		Long instructorId = comment.getInstructor().getInstructorId();
		if(role.equals("[admin]")) {
			try {
				commentRepo.delete(comment);	
				message = "Comment has been deleted";	
				attributes.addFlashAttribute("message", message);
				return "redirect:/viewInstructors/profile/" + instructorId;
			} catch (Exception e) {
				e.printStackTrace();
				message = "This comment was unable to be deleted";
				attributes.addFlashAttribute("message", message);
				return "redirect:/viewInstructors/profile/" + instructorId;
			}
		} else {
			return "redirect:/viewInstructors/profile/" + instructorId;
		}
	}
}
