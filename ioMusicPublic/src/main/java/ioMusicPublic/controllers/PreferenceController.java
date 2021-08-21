//This controller deals with functions that allow genres, instruments and artists to the database

package ioMusicPublic.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import formHelpers.ArtistList;
import formHelpers.S3Client;
import formHelpers.VideoToolList;
import ioMusicPublic.authentication.instructor.InstructorDetails;
import ioMusicPublic.models.Artist;
import ioMusicPublic.models.Comment;
import ioMusicPublic.models.Genre;
import ioMusicPublic.models.Instrument;
import ioMusicPublic.models.LessonRequest;
import ioMusicPublic.models.VideoTool;
import ioMusicPublic.models.instructor.Instructor;
import ioMusicPublic.models.instructor.InstructorFavouriteArtist;
import ioMusicPublic.models.instructor.InstructorInstrument;
import ioMusicPublic.models.instructor.InstructorVideoTools;
import ioMusicPublic.models.instructor.ProfilePhoto;
import ioMusicPublic.repositories.ArtistRepository;
import ioMusicPublic.repositories.CommentRepository;
import ioMusicPublic.repositories.GenreRepository;
import ioMusicPublic.repositories.InstructorFavouriteArtistRepository;
import ioMusicPublic.repositories.InstructorInstrumentRepository;
import ioMusicPublic.repositories.InstructorRepository;
import ioMusicPublic.repositories.InstructorVideoToolsRepository;
import ioMusicPublic.repositories.InstrumentRepository;
import ioMusicPublic.repositories.LessonRequestRepository;
import ioMusicPublic.repositories.ProfilePhotoRepository;
import ioMusicPublic.repositories.StatusRepository;
import ioMusicPublic.repositories.VideoToolRepository;

@Controller
public class PreferenceController {
	
	///***REPOSITORIES***
	
	//Genre Repository
	@Autowired
	GenreRepository genreRepo;
	
	//Instrument Repository
	@Autowired
	InstrumentRepository instrumentRepo;
	
	//Artist Repository
	@Autowired
	ArtistRepository artistRepo;
	
	//Instructor Favourite Artists Repository
	@Autowired
	InstructorFavouriteArtistRepository instructorArtistRepo;
	
	//VideoTools Repository
	@Autowired
	VideoToolRepository videoToolRepo;
	
	//Instructor VideoTools Repository
	@Autowired
	InstructorVideoToolsRepository instructorVideoToolsRepo;
	
	//Instructor Favourite Artists Repository
	@Autowired
	InstructorInstrumentRepository instructorInstrumentRepo;
	
	//Instructor Repository
	@Autowired
	InstructorRepository instructorRepo;
	
	//ProfilePhoto Repository
	@Autowired
	ProfilePhotoRepository photoRepo;
	
	//ProfilePhoto Repository
	@Autowired
	StatusRepository statusRepo;
	
	//LessonRequest Repository
	@Autowired
	LessonRequestRepository requestRepo;
	
	//Comment Repository
	@Autowired
	CommentRepository commentRepo;
	
	//AWS S3 Variables
	@Value("${S3Endpoint}")
	private String endpoint;
	
	@Value("${S3BucketName}")
	private String bucketName;
	
	@Value("${S3AccessKey}")
	private String accessKey;
	
	@Value("${S3SecretKey}")
	private String secretKey;
	
	//The method below will display how many things need actioning by the logged in user
	public void setUserNotifications(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			String role = authentication.getAuthorities().toString();
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
		
	//The method below will display a page allowing a user to add a genre
	@GetMapping("/addGenre")
	public String displayAddGenrePage(Model model) {
		setUserNotifications(model);
		//Get list of current genres
		List<Genre> genreList = genreRepo.findAll();
		//Check if there are genre's in the genre repo
		if(genreList.isEmpty()) {
			model.addAttribute("genreList", "emptyRepo");
		}else {
			model.addAttribute("genreList", genreList);
		}
		return "addGenre";
	}
		
	//The method below will add a genre to the genre repository using the add genre form
	@PostMapping("/addGenre")
	public String addGenre(@RequestParam("genre") String genre, RedirectAttributes attributes) {
		String message;
		//Try to create a new Genre instance with the form's genre value and save it to the genre repo if it doesn't already exist
		if(genreRepo.findByGenreName(genre) == null) {
			try {
				Genre newGenre = new Genre();
				newGenre.setGenreName(genre);
				genreRepo.save(newGenre);
				//Display a message to the user showing that the genre was added
				message = genre + " was added to our list of genres";
			} catch(Exception e) {
				e.printStackTrace();
				//Display a message to the user showing that the genre wasnt added
				message = genre + " was unable to be added to our list of genres";
			}
		}else {
			message = genre + " is already in our database";
		}
		attributes.addFlashAttribute("message", message);
		return "redirect:/register/instructor";
	}
		
	//The method below will display a page allowing a user to add an instrument
	@GetMapping("/addInstrument")
	public String displayAddInstrumentPage(Model model) {
		setUserNotifications(model);
		model.addAttribute("instrument", new Instrument());
		//Get list of current genres
		List<Instrument> instrumentList = instrumentRepo.findAll();
		//Check if there are genre's in the genre repo
		if(instrumentList.isEmpty()) {
			model.addAttribute("instrumentList", "emptyRepo");
		}else {
			model.addAttribute("instrumentList", instrumentList);
		}
		return "addInstrument";
	}
		
	//The method below will add a instrument to the instrument repository using the add instrument form
	@PostMapping("/addInstrument")
	public String addInstrument(@RequestParam("instrument") String instrument, RedirectAttributes attributes) {
		String message;
		//Try to create a new Instrument instance with the form's instrument value and save it to the instrument repo if it doesn't already exist
		if(instrumentRepo.findByName(instrument) == null) {
			try {
				Instrument newInstrument = new Instrument();
				newInstrument.setName(instrument);
				instrumentRepo.save(newInstrument);
				//Display a message to the user showing that the instrument was added
				message = instrument + " was added to our list of instruments";
	
			} catch(Exception e) {
				e.printStackTrace();
				//Display a message to the user showing that the instrument was unable to be added
				message = instrument + " was unable to be added to our list of instruments";
			}
		}else {
			message = instrument + " is already in our database";
		}
		attributes.addFlashAttribute("message", message);
		return "redirect:/register/instructor";
	}
		
	//The method below will display a page allowing a user to add an Artist
	@GetMapping("/addArtist")
	public String displayAddArtistPage(Model model) {
		setUserNotifications(model);
		//Get list of current artists
		List<Artist> artistList = artistRepo.findAll();
		//Check if there are artists in the artist repo
		if(artistList.isEmpty()) {
			model.addAttribute("artistList", "emptyRepo");
		}else {
			model.addAttribute("artistList", artistList);
		}
		return "addArtist";
	}
		
	//The method below will add an artist to the artist repository using the add artist form
	@PostMapping("/addArtist")
	public String addArtist(@RequestParam("artist") String artist, RedirectAttributes attributes) {
		String message;
		//Try to create a new Artist instance with the form's artist value and save it to the artist repo if it doesn't exist already
		if(artistRepo.findByArtistName(artist) == null) {
			try {
				Artist newArtist = new Artist();
				newArtist.setArtistName(artist);
				artistRepo.save(newArtist);
				//Display a message to the user showing that the artist was added
				message = artist + " was added to our list of artists";
			} catch (Exception e) {
				e.printStackTrace();
				//Display a message to the user showing that the artist was added
				message = artist + " was unable to be added to our list of artists";
			}	
		} else {
			message = artist + " is already in our database";
		}
		attributes.addFlashAttribute("message", message);
		return "redirect:/register/instructor";
	}	
	
	//The method below will display a page allowing a user to add a video platform
	@GetMapping("/addVideoPlatform")
	public String displayAddVideoPlatformPage(Model model) {
		setUserNotifications(model);
		//Get list of current platforms
		List<VideoTool> toolList = videoToolRepo.findAll();
		//Check if there are video tools in the video tool repo
		if(toolList.isEmpty()) {
			model.addAttribute("toolList", "emptyRepo");
		}else {
			model.addAttribute("toolList", toolList);
		}
		return "addVideoPlatform";
	}
		
	//The method below will add a video calling platform to the video tools repository using the add VideoPlatform form
	@PostMapping("/addVideoPlatform")
	public String addVideoPlatform(@RequestParam("videoTool") String videoTool, RedirectAttributes attributes) {
		String message;
		//Try to create a new VideoTool instance with the form's videotool value and save it to the video tool repo if it doesn't exist
		if(videoToolRepo.findByName(videoTool) == null) {
			try {
				VideoTool platform = new VideoTool();
				platform.setName(videoTool);
				videoToolRepo.save(platform);
				message = videoTool + " was added to our list of video calling platforms";
			} catch(Exception e) {
				e.printStackTrace();
				message = videoTool + " was unable to be added to our list of calling platforms";
			}
		}else {
			message = videoTool + " is already in our database";
		}
		attributes.addFlashAttribute("message", message);
		return "redirect:/register/instructor";
	}
	
	//The method below will display an instructors own profile page
	@GetMapping("/myProfile")
	public String viewOwnProfile(Authentication auth, Model model) {
		//Get the logged in instructors details
		InstructorDetails details = (InstructorDetails) auth.getPrincipal();
		long instructorId = details.getInstructorId();
		Instructor instructor = instructorRepo.getById(instructorId);
		setUserNotifications(model);
		model.addAttribute("instructor", instructor);
		return "instructorProfile";
	}
	
	//***EDIT PROFILE***
	
	//The method below will display a page to a instructor which allows them to edit their email address
	@GetMapping("/editProfile/email")
	public String displayEmailEditForm(Authentication auth, Model model) {
		//Make sure the user is a logged in instructor
		String role = auth.getAuthorities().toString();
		if(role.equals("[instructor]")) {
			setUserNotifications(model);
			return "editProfilePages/editEmail";
		} else {
			return "/";
		}
	}
	
	//The method below will save the instructors updated email address
	@PostMapping("/editProfile/email")
	public String updateEmail(Authentication auth, @RequestParam("email") String email, RedirectAttributes attributes) {
		//Get the logged in instructor using their id
		InstructorDetails details = (InstructorDetails) auth.getPrincipal();
		long instructorId = details.getInstructorId();
		Instructor instructor = instructorRepo.getById(instructorId); 
		instructor.setEmail(email);
		instructorRepo.save(instructor);
		String message = "Your email address has been changed to " + instructor.getEmail();
		attributes.addFlashAttribute("message", message);
		return "redirect:/myProfile";
	}
	
	//The method below will display a page to a instructor which allows them to change their password
	@GetMapping("/editProfile/password")
	public String displayChangePasswordForm(Authentication auth, Model model) {
		//Make sure the user is a logged in instructor
		String role = auth.getAuthorities().toString();
		if(role.equals("[instructor]")) {
			setUserNotifications(model);
			return "editProfilePages/editPassword";
		} else {
			return "/";
		}
	}
	
	//The method below will save the instructors updated password
	@PostMapping("/editProfile/password")
	public String updatePassword(Authentication auth, @RequestParam("password") String password, RedirectAttributes attributes) {
		//Get the logged in instructor using their id
		InstructorDetails details = (InstructorDetails) auth.getPrincipal();
		long instructorId = details.getInstructorId();
		Instructor instructor = instructorRepo.getById(instructorId);
		//Encrypt the new password
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		password = passwordEncoder.encode(password);
		instructor.setPassword(password);
		instructorRepo.save(instructor);
		String message = "Your password has been updated";
		attributes.addFlashAttribute("message", message);
		return "redirect:/myProfile";
	}
	
	//The method below will display a page to a instructor which allows them to change their description
	@GetMapping("/editProfile/description")
	public String displayChangeDescriptionForm(Authentication auth, Model model) {
		//Make sure the user is a logged in instructor
		String role = auth.getAuthorities().toString();
		if(role.equals("[instructor]")) {
			setUserNotifications(model);
			//Use the instructors current description as the description placeholder text
			InstructorDetails details = (InstructorDetails) auth.getPrincipal();
			long instructorId = details.getInstructorId();
			String description = instructorRepo.getById(instructorId).getDescription();
			model.addAttribute("description", description);
			return "editProfilePages/editDescription";
		} else {
			return "/";
		}
	}
	
	//The method below will save the instructors updated description
	@PostMapping("/editProfile/description")
	public String updateDescription(Authentication auth, @RequestParam("description") String description, RedirectAttributes attributes) {
		//Get the logged in instructor using their id
		InstructorDetails details = (InstructorDetails) auth.getPrincipal();
		long instructorId = details.getInstructorId();
		Instructor instructor = instructorRepo.getById(instructorId);
		instructor.setDescription(description);
		instructorRepo.save(instructor);
		String message = "Your description has been updated";
		attributes.addFlashAttribute("message", message);
		return "redirect:/myProfile";
	}
	
	//The method below will display a page to a instructor which allows them to change their rate
	@GetMapping("/editProfile/rate")
	public String displayChangeRateForm(Authentication auth, Model model) {
		//Make sure the user is a logged in instructor
		String role = auth.getAuthorities().toString();
		if(role.equals("[instructor]")) {
			setUserNotifications(model);
			//Use the instructors current rate as the rate placeholder text
			InstructorDetails details = (InstructorDetails) auth.getPrincipal();
			long instructorId = details.getInstructorId();
			float rate = instructorRepo.getById(instructorId).getHourlyRate();
			model.addAttribute("rate", rate);
			return "editProfilePages/editRate";
		} else {
			return "/";
		}
	}
	
	//The method below will save the instructors updated rate
	@PostMapping("/editProfile/rate")
	public String updateRate(Authentication auth, @RequestParam("rate") float newRate, RedirectAttributes attributes) {
		String message;
		//Get the logged in instructor using their id
		InstructorDetails details = (InstructorDetails) auth.getPrincipal();
		long instructorId = details.getInstructorId();
		Instructor instructor = instructorRepo.getById(instructorId);
		//Check if there is a difference between the new and old rates
		float currentRate = instructor.getHourlyRate();
		if (currentRate != newRate) {
			instructor.setHourlyRate(newRate);
			instructorRepo.save(instructor);
			message = "Your hourly rate has been changed to \u20ac" + newRate;
		} else {
			message = "Your hourly rate will remain at \u20ac" + currentRate;
		}
		attributes.addFlashAttribute("message", message);
		return "redirect:/myProfile";
	}
	
	//The method below will display a page to a instructor which allows them to change their video platform tools
	@GetMapping("/editProfile/videoPlatform")
	public String displayChangeVideoPlatformForm(Authentication auth, Model model) {
		//Make sure the user is a logged in instructor
		String role = auth.getAuthorities().toString();
		if(role.equals("[instructor]")) {
			setUserNotifications(model);
			//List the instructors currently chosen platforms
			InstructorDetails details = (InstructorDetails) auth.getPrincipal();
			long instructorId = details.getInstructorId();
			Instructor instructor = instructorRepo.getById(instructorId);
			model.addAttribute("instructor", instructor);
			//List all the video tools in the repo
			List<VideoTool> videoTools = videoToolRepo.findAll();
			model.addAttribute("videoTools", videoTools);
			model.addAttribute("videoToolList", new VideoToolList());
			return "editProfilePages/editVideoPlatform";
		} else {
			return "/";
		}
	}
	
	//The method below will save an instructors updated video platform tools
	@PostMapping("/editProfile/videoPlatform")
	public String updateVideoPlatforms(Authentication auth, VideoToolList videoTools, RedirectAttributes attributes) {
		//Get the logged in instructor using their id
		InstructorDetails details = (InstructorDetails) auth.getPrincipal();
		long instructorId = details.getInstructorId();
		Instructor instructor = instructorRepo.getById(instructorId);
		//Remove their current video platforms
		for(InstructorVideoTools platform: instructor.getVideoTools()) {
			instructorVideoToolsRepo.delete(platform);
		}
		//Create records for the updated video tools
		for (VideoTool videoTool : videoTools) {
			InstructorVideoTools selectedVideoTool = new InstructorVideoTools();
			selectedVideoTool.setVideoTool(videoTool);
			selectedVideoTool.setInstructor(instructor);
			// Save each video tool to the candidate video tools repo
			instructorVideoToolsRepo.save(selectedVideoTool);
		}
		String message = "Your video calling platforms have been updated";
		attributes.addFlashAttribute("message", message);
		return "redirect:/myProfile";
	}
	
	//The method below will display a page to a instructor which allows them to change their favourite Artists
	@GetMapping("/editProfile/artist")
	public String displayChangeFavouriteArtistsForm(Authentication auth, Model model) {
		//Make sure the user is a logged in instructor
		String role = auth.getAuthorities().toString();
		if(role.equals("[instructor]")) {
			setUserNotifications(model);
			//List the instructors currently chosen artists
			InstructorDetails details = (InstructorDetails) auth.getPrincipal();
			long instructorId = details.getInstructorId();
			Instructor instructor = instructorRepo.getById(instructorId);
			model.addAttribute("instructor", instructor);
			//List all the artists in the repo
			List<Artist> artists = artistRepo.findAll();
			model.addAttribute("artists", artists);
			model.addAttribute("artistList", new ArtistList());
			return "editProfilePages/editArtists";
		} else {
			return "/";
		}
	}
		
	//The method below will save an instructors updated favourite Artists
	@PostMapping("/editProfile/artist")
	public String updateFavouriteArtists(Authentication auth, ArtistList artistList, RedirectAttributes attributes) {
		//Get the logged in instructor using their id
		InstructorDetails details = (InstructorDetails) auth.getPrincipal();
		long instructorId = details.getInstructorId();
		Instructor instructor = instructorRepo.getById(instructorId);
		//Remove their current favourite artists
		for(InstructorFavouriteArtist artist: instructor.getFavouriteArtists()) {
			instructorArtistRepo.delete(artist);
		}
		//Create records for the updated artist list
		for (Artist artist : artistList) {
			InstructorFavouriteArtist selectedArtist = new InstructorFavouriteArtist();
			selectedArtist.setArtist(artist);
			selectedArtist.setInstructor(instructor);
			// Save each video tool to the candidate video tools repo
			instructorArtistRepo.save(selectedArtist);
		}
		String message = "Your favourite artists have been updated";
		attributes.addFlashAttribute("message", message);
		return "redirect:/myProfile";
	}
	
	//The method below will display a form allowing a instructor to upload a profile picture
	@GetMapping("editProfile/photo")
	public String displayPhotoUploadForm(Authentication auth, Model model) {
		//Make sure the user is a logged in instructor
		String role = auth.getAuthorities().toString();
		if(role.equals("[instructor]")) {
			setUserNotifications(model);
			return "editProfilePages/addPhoto";
		} else {
			return "error";
		}
	}
	
	@PostMapping("editProfile/photo")
	public String uploadPhoto(@RequestParam("image") MultipartFile imageFile, Authentication auth, RedirectAttributes attributes) throws IOException {
		String message;
		//Determine what file extensions can be used and if the file uploaded is of a valid type
		List<String> validExtensions = Arrays.asList("jpeg", "jpg", "png");
		String fileExtension = imageFile.getOriginalFilename();
		fileExtension = fileExtension.substring(fileExtension.lastIndexOf(".") + 1);
		InstructorDetails details = (InstructorDetails) auth.getPrincipal();
		Instructor instructor = details.getInstructor();
		if(validExtensions.contains(fileExtension)) {
			//Instantiate an S3Client object and upload the photo
			S3Client s3Client= new S3Client(endpoint, bucketName, accessKey, secretKey);
			s3Client.createConnection();
			//Convert the image from multi part file to File
			File convertedFile = new File(imageFile.getOriginalFilename());
	        FileOutputStream fileOutputStream = new FileOutputStream(convertedFile);
	        fileOutputStream.write(imageFile.getBytes());
	        fileOutputStream.close();
			String fileName = "instructorID" + instructor.getInstructorId() + "ProfilePhoto";
			s3Client.getAmazonS3().putObject(new PutObjectRequest(bucketName,fileName, convertedFile).withCannedAcl(CannedAccessControlList.PublicRead));
			String photoUrl = s3Client.getEndpoint() + "/" + fileName;
			if(photoRepo.findByInstructor(instructor) == null) {
				//Create a ProfilePhoto instance and use the instructor and photo url details
				ProfilePhoto photoReference = new ProfilePhoto();
				photoReference.setImageUrl(photoUrl);
				photoReference.setInstructor(instructor);
				photoRepo.save(photoReference);
				instructor.setProfilePhoto(photoReference);
				//Create a success message
				message = "Your photo has been successfully uploaded";
			} else {
				//If the instructor already has a photo, update the photoRepo with the url for the new photo
				ProfilePhoto photoReference = photoRepo.findByInstructor(instructor);
				photoReference.setImageUrl(photoUrl);
				message = "Your photo has been updated";
			}
			instructorRepo.save(instructor);
		} else {
			message = "Your photo is unable to be uploaded. We only allow file types of jpeg, jpg and png. You have tried to upload a file of type: " + fileExtension;
		}
		attributes.addFlashAttribute("message", message);	
		return "redirect:/myProfile";	
	}
	
	//The method below will delete an instructors account
	@GetMapping("/deleteAccount")
	public String deleteAccount(Authentication auth, RedirectAttributes attributes) {
		String message;
		//Get the logged in instructor using their id
		InstructorDetails details = (InstructorDetails) auth.getPrincipal();
		long instructorId = details.getInstructorId();
		Instructor instructor = instructorRepo.getById(instructorId);
		//Remove their favourite artists
		for(InstructorFavouriteArtist artist: instructor.getFavouriteArtists()) {
			instructorArtistRepo.delete(artist);
		}
		for(InstructorInstrument instrument: instructor.getInstruments()) {
			instructorInstrumentRepo.delete(instrument);
		}
		for(InstructorVideoTools videoTool: instructor.getVideoTools()) {
			instructorVideoToolsRepo.delete(videoTool);
		}
		for(Comment comment: instructor.getComments()) {
			commentRepo.delete(comment);
		}
		if(instructor.getProfilePhoto() != null) {
			photoRepo.delete(instructor.getProfilePhoto());
		}
		for(LessonRequest lessonRequest: instructor.getLessonRequests()) {
			requestRepo.delete(lessonRequest);
		}
		//Log the instructor out and delete their profile
		SecurityContextHolder.clearContext();
		instructorRepo.delete(instructor);
		message = "Your Profile has been deleted";
		attributes.addFlashAttribute("message", message);
		return "redirect:/";
	}
}
