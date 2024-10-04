document.addEventListener('DOMContentLoaded', async () => {
	// Load the publishable key from the server. The publishable key
	// is set in your .env file.
	const {publishableKey} = await fetch('api/config').then((r) => r.json());
	if (!publishableKey) {
		addMessage(
			'No publishable key returned from the server. Please check `.env` and try again'
		);
		alert('Please set your Stripe publishable API key in the .env file');
	}

	const stripe = Stripe(publishableKey, {
		apiVersion: '2020-08-27',
	});

	const url = new URL(window.location);
	const clientSecret = url.searchParams.get('payment_intent_client_secret');

	const {error, paymentIntent} = await stripe.retrievePaymentIntent(
		clientSecret
	);
	if (error) {
		addMessage(error.message);
	}
	addMessage(`Payment ${paymentIntent.status}: ${paymentIntent.id}`);
});


//const {publishableKey} = await fetch('api/config').then((r) => r.json());


/*
var stripe = Stripe("pk_test_51KhK4uIgeljxaKEYSCw8g9EQO2dmh6lj0X3dMNGlUxU00GdlzOdbBQe45uAAqbu0Iq4XUNyQJ9IzZLIc86WYoF0n00rzjQojA3");
var elements = stripe.elements();
var style = {
		base: {
			fontSize: '16px',
			iconColor: '#c4f0ff',
	        color: '#024065',
		},
		invalid: {
			iconColor: '##FF0000',
	        color: '##FF0000',
		},
};
var card = elements.create('card');

card.mount("#card-element");

var clientSecret = $("#sec-key").val();
var remember = $("#remember").val();

var spinner = $('#loader');

function paywithcard(){
	spinner.show();




	stripe.handleCardPayment(clientSecret,card)
		.then(function(result){
			console.log("result"+result);
			if(result.error){
				spinner.hide();
				var errorElement = document.getElementById('card-errors');
				errorElement.textContent = result.error.message;
			}else{
				debugger
				console.log("Payment ID >> "+result.paymentIntent.id);
				var url = "paywithcard";
				$.ajax({
					type:"POST",
					url : url,
					data:{
						remember : remember,
						paymentId : result.paymentIntent.id,
					},
					success : function(response){
						debugger
						checkStatus(result.paymentIntent.status);
						spinner.hide();
					},
					error: function(response){
						spinner.hide();
						console.log(response);
					}
				});
			}
		});
	}
function checkStatus(status){
	debugger
	switch(status){
	case "succeeded":
		showmessage("Payment succeeded!");
		break;
	case "processing":
		showmessage("Your payment is processing.");
    	break;
    case "requires_payment_method":
    	showmessage("Your payment was not successful, please try again.");
    	break;
    default:
    	showmessage("Something went wrong.");
      	break;
	}
}
function showmessage(text){
	debugger
	var messageContainer = document.querySelector("#payment-message");
	messageContainer.classList.remove("hidden");
	messageContainer.textContent = text;
	setTimeout(function () {
		messageContainer.classList.add("hidden");
	    text.textContent = "";
    }, 4000);
}

	*/
