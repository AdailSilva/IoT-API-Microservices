package com.adailsilva.iot.api.feignclients;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import com.adailsilva.iot.api.entities.Worker;

/* For class 03-05, Hystrix should be replaced with CircuitBreaker, since it was deprecated on Netflix.
 * Using reference  https://senoritadeveloper.medium.com/spring-boot-feign-client-load-balancer-and-circuit-breaker-733d243f0957
 * for construction of fallback (annotation and implementation) */

@Component
public class WorkerFeignClientFallback implements WorkerFeignClient {

	@Override
	@GetMapping(value = "/names")
	public List<String> allNames() {
		return new ArrayList<String>();
	}

	@Override
	@GetMapping(value = "/all")
	public ResponseEntity<List<Worker>> findAll() {
		return ResponseEntity.ok(new ArrayList<Worker>());
	}

	@Override
	@GetMapping(value = "/worker/{id}")
	public ResponseEntity<Worker> findById(Long id) {
		return ResponseEntity.ok(new Worker(1L, "CircuitBreaker Fallback", 0.0));
	}

}
