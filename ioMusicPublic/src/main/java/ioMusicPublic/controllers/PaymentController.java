package ioMusicPublic.controllers;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

import formHelpers.PaymentResponse;
import ioMusicPublic.authentication.student.StudentDetails;
import ioMusicPublic.models.LessonRequest;
import ioMusicPublic.models.Transaction;
import ioMusicPublic.repositories.LessonRequestRepository;
import ioMusicPublic.repositories.StatusRepository;
import ioMusicPublic.repositories.TransactionRepository;

@Controller
public class PaymentController {
	
	/***Repository Instantiations***/
	
	//LessonRequest Repository
	@Autowired
	LessonRequestRepository requestRepo;
	
	//Status Repository
	@Autowired
	StatusRepository statusRepo;
	
	//Transaction Repository
	@Autowired
	TransactionRepository transactionRepo;

	// Get stripe secret key from environment variable value
	@Value("${StripeSecretKey}")
	private String stripeSecretKey;
	
	public void setUserNotifications(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			String role = authentication.getAuthorities().toString();
			if(role.equals("[student]")) {
				//Get how many lesson requests need actioning for the logged in student using their studentId
				StudentDetails details = (StudentDetails) authentication.getPrincipal();
				long studentId = details.getStudentId();
				Long amount = requestRepo.countRequestsRequiringStudentAction(studentId);
				if(amount>=1) {
					model.addAttribute("amount", amount);
				}
			}
		}
	}
	
	//Method below will create a payment intent using the details of the lesson request and redirect the user to the payment screen
	@PostMapping("/lessonRequest/createPaymentIntent/{requestId}")
	public String createPaymentIntent(@PathVariable("requestId") Long id, Model model) throws StripeException {
		// Get the Stripe secret key
		Stripe.apiKey = stripeSecretKey;
		// Create a payment intent using custom parameters
		try {
			// Get the lesson request
			LessonRequest request = requestRepo.getById(id);
			model.addAttribute("request", request);
			PaymentIntentCreateParams customParams = new PaymentIntentCreateParams.Builder()
					.setCurrency("eur")
					.setAmount((long) request.getCost() * 100)
					.setDescription(request.getDuration() + " hour lesson with "
							+ request.getInstructor().getFirstName() + " " + request.getInstructor().getLastName())
					.build();
			PaymentIntent intent = PaymentIntent.create(customParams);
			// Get the client secret key from the payment intent,instantiate a
			// PaymentResponse instance with it and add it to the model
			PaymentResponse response = new PaymentResponse(intent.getClientSecret());
			model.addAttribute("response", response);
		} catch (StripeException e) {
			throw e;
		}
		setUserNotifications(model);
		return "paymentForm";
	}
	
	//Method below is for when the student successfully makes a payment
	@GetMapping("/lessonRequest/createPaymentIntent/{requestId}/success")
	public String paymentSuccess(@PathVariable("requestId") Long id, Model model, RedirectAttributes attributes) {
		try {
			//Get the lesson request and change its status to paid
			LessonRequest request = requestRepo.getById(id);
			request.setStatus(statusRepo.getById((short) 4));
			requestRepo.save(request);
			//Create a new Transaction instance using the payment details and add it to the transaction repo
			Transaction transaction = new Transaction();
			transaction.setRequest(request);
			transaction.setAmount(request.getCost());
			transaction.setDate(LocalDate.now());
			transactionRepo.save(transaction);
			//Display a message to the user showing that the payment was successfull
			String message = "Your payment was successfull!!";
			attributes.addFlashAttribute("message", message);
		} catch(Exception e) {
			e.printStackTrace();
			//Display a message to the user showing that the payment was unsuccessfull
			String message = "Your payment was unsuccessfull!!";
			attributes.addFlashAttribute("message", message);
		}
		return "redirect:/lessonRequests";	
	}
}
