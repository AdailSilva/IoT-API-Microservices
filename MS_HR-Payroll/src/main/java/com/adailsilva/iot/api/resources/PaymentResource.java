package com.adailsilva.iot.api.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adailsilva.iot.api.entities.Payment;
import com.adailsilva.iot.api.services.PaymentService;

@RestController
@RequestMapping(value = "/payments")
public class PaymentResource {

	private static Logger logger = LoggerFactory.getLogger(PaymentResource.class);

	@Autowired
	private Environment environment;

	@Autowired
	private PaymentService paymentService;

//	@HystrixCommand(fallbackMethod = "getPaymentAlternative")
	@GetMapping(value = "/{workerId}/days/{days}")
	public ResponseEntity<Payment> getPayment(@PathVariable Long workerId, @PathVariable Integer days) {

		ResponseEntity<Payment> payment = null;
//		ResponseEntity<Payment> paymentRestTemplate = null;

		try {
			payment = paymentService.getPayment(workerId, days);
//			With RestTemplate
//			paymentRestTemplate = paymentService.getPaymentRestTemplate(workerId, days);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

		if (payment.getStatusCodeValue() == 404) {
			return ResponseEntity.notFound().build();
		}

		logger.info("PORT = " + environment.getProperty("local.server.port"));

		return ResponseEntity.ok(payment.getBody());
//		return ResponseEntity.ok(paymentRestTemplate.getBody());
	}

	// Hystrix Fault Tolerance
	// This method is called when there is some failure,
	// For example a request timed out.
	public ResponseEntity<Payment> getPaymentAlternative(@PathVariable Long workerId, @PathVariable Integer days) {
		Payment payment = new Payment("Test Client to call Alternative Method (Hystrix Detour)", 1.0, days);

		logger.info("PORT = " + environment.getProperty("local.server.port"));

		return ResponseEntity.ok(payment);
	}

}
