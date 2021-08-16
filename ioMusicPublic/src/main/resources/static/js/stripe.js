$(document).ready(function() {
	//Display card details that will work
	displayValidCardCredentials();
	//Get the Stripe publc key
	let stripe = Stripe("pk_test_hS6bA5ILNRPXhcLD2G7K5fla00z1Ld3RiU");
	let displayError = $("card-errors");
	//Create card input areas and handle errors as the user is typing input values
	let elements = stripe.elements();
	let elementStyle = {
		base: {
			"color": "white"
		}
	};
	let cardNumber = elements.create("cardNumber", {"style": elementStyle});
	cardNumber.mount("#cardNumber");
	cardNumber.on('change', ({ error }) => {
		displayError = $("#card-errors");
		if (error) {
			validInput = false;
			displayError.textContent = error.message;
		} else {
			validInput = true;
			displayError.textContent = '';
		}
	});
	//Card Expiry and error handling
	let cardExpiry = elements.create("cardExpiry", {"style": elementStyle});
	cardExpiry.mount('#cardExpiry');
	cardExpiry.on('change', ({ error }) => {
		let displayError = $("#card-errors");
		if (error) {
			validInput = false;
			displayError.textContent = error.message;
		} else {
			validInput = true;
			displayError.textContent = '';
		}
	});
	//Card CVC
	let cardCvc = elements.create('cardCvc', {"style": elementStyle});
	cardCvc.mount('#cardCVC');
	$("#payment-form").on("submit", function(event) {
		event.preventDefault();
		//Charge the card using the card dewtails when the form is submitted
		payWithCard(stripe, cardNumber, secretKey);
	});
});


function payWithCard(stripe, cardNumber, secretKey) {
	//Call the loading method to show signal payment has started 
	loading(true);
	let displayError = document.getElementById("card-errors")
	stripe.confirmCardPayment(secretKey, {
		payment_method: {
			card: cardNumber,
			billing_details: {
				name: $("#cardHolderName").val()
			}
		}
	}).then(function(result) {
		if (result.error) {
			displayError.textContent.textContent = result.error.message;
		} else {
			if (result.paymentIntent.status === 'succeeded') {
				paymentComplete();
			}
		}
	})
}

//This method is used to redirect the user to a page showing that their payment was successfull
function paymentComplete(){
	//Call the loading method to signal payment has been completed
	loading(false);
	document.getElementById("paymentComplete").textContent = "Payment Successfull";
	document.querySelector("button").disabled = true;
	successRedirect();
}

function loading(isLoading) {
	if (isLoading) {
		// Disable the button and show a spinner
		document.querySelector("button").disabled = true;
		document.querySelector("#spinner").classList.remove("hidden");
		document.querySelector("#button-text").classList.add("hidden");
	} else {
		document.querySelector("button").disabled = false;
		document.querySelector("#spinner").classList.add("hidden");
		document.querySelector("#button-text").classList.remove("hidden");
	}
}

//The method below is used to call the springboot controller after the payment successfull
function successRedirect(){
	//create a form
	let requestId = document.getElementById("requestId").value;
	let form = document.createElement("form");
	let formAction = requestId + "/success";
	form.setAttribute("action", formAction);
	form.setAttribute("method", "get");
	document.body.appendChild(form);
	form.submit();
}

//Display the card details that will be accepted in an alert
function displayValidCardCredentials(){
	alert("This project is a student project and not designed to take real payments.\n" +
	"The following fictional card details provided by Stripe payments will be processed as if a legitimate bank card was used.\n\n" + 
	"CardHolder Name: Any name can be used\nCard Number: 4242424242424242" + "\nCard Expiry: Any future date\n Card CVV: Any number");
}










