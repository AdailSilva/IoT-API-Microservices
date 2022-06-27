package com.adailsilva.iot.api.resources;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adailsilva.iot.api.entities.Worker;
import com.adailsilva.iot.api.repositories.WorkerRepository;

@RestController
@RequestMapping(value = "/workers")
public class WorkerResource {

	private static Logger logger = LoggerFactory.getLogger(WorkerResource.class);

	@Autowired
	private Environment environment;

	@Autowired
	private WorkerRepository workerRepository;

	@GetMapping(value = "/names")
	public List<String> allNames() {

		logger.info("PORT = " + environment.getProperty("local.server.port"));

		return Arrays.asList("Adail Silva", "Ágatha Silva", "Davi Miguel");
	}

	@GetMapping(value = "/all")
	public ResponseEntity<List<Worker>> findAll() {
		List<Worker> workers = workerRepository.findAll();

		logger.info("PORT = " + environment.getProperty("local.server.port"));

		return ResponseEntity.ok(workers);
	}

	@GetMapping(value = "/worker/{id}")
	public ResponseEntity<Worker> findById(@PathVariable(value = "id") Long id) {

		// Hystrix Fault Tolerance
		// Requisition timeout simulation:
//		try {
//			Thread.sleep(3000L);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		logger.info("PORT = " + environment.getProperty("local.server.port"));

		return workerRepository.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

}
