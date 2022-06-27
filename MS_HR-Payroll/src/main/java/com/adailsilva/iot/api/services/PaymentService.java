package com.adailsilva.iot.api.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.adailsilva.iot.api.entities.Payment;
import com.adailsilva.iot.api.entities.Worker;
import com.adailsilva.iot.api.feignclients.WorkerFeignClient;

@Service
public class PaymentService {

	private static Logger logger = LoggerFactory.getLogger(PaymentService.class);

	@Autowired
	private WorkerFeignClient workerFeignClient;

	public ResponseEntity<Payment> getPayment(Long workerId, Integer days) {

		try {
			// HTTP Call with Feign (REST)
			Worker worker = workerFeignClient.findById(workerId).getBody();
			if (worker == null) {
				logger.error("Worker not found: " + worker);
				throw new IllegalAccessException("Worker not found");
			}

			Payment payment = new Payment(worker.getName(), worker.getDailyIncome(), days);
			logger.info("Worker found: " + worker);
			return ResponseEntity.ok(payment);
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}

	}

//	@Value("${hr-worker.host}")
//	private String host;
//
//	@Autowired
//	private RestTemplate restTemplate;
//	
//	// MOCK
//	public ResponseEntity<Payment> getPayment(Long workerId, Integer days) {
//		return ResponseEntity.ok(new Payment("AdailSilva", 10.0, 30));
//	}
//
//	// MOCK with RestTemplate, call HR-Worker project
//	public ResponseEntity<Payment> getPaymentRestTemplate(Long workerId, int days) {
//		Map<String, String> uriVariables = new HashMap<>();
//		uriVariables.put("id", "" + workerId);
//		// REST call
//		Worker worker = restTemplate.getForObject(host + "/workers/worker/{id}", Worker.class, uriVariables);	
//		return ResponseEntity.ok(new Payment(worker.getName(), worker.getDailyIncome(), days));
//	}

}
