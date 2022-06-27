package com.adailsilva.iot.api.feignclients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.adailsilva.iot.api.entities.Worker;

/* For class 02-07 - hard code url */
//@FeignClient(name = "hr-worker", url = "localhost:8001", path = "/workers")
// url = "localhost:8001" -> When only one customer.

/* For class 02-08 - url should be on two ports - defined on application.properties when using ribbon.
* But, since for springboot 2.6.6 ribbon was deprecated, the application was configured to use 
* Spring LoadBalancer. Check classes on package config */

/* For class 03-05, Hystrix should be replaced with CircuitBreaker, since it was deprecated on Netflix.
* Using reference  https://senoritadeveloper.medium.com/spring-boot-feign-client-load-balancer-and-circuit-breaker-733d243f0957 
* for construction of fallback (annotation and implementation) */

@Component
//@FeignClient(name = "hr-worker", url = "localhost:8001", path = "/workers", fallback = WorkerFeignClientFallback.class)
@FeignClient(name = "hr-worker", path = "/workers", fallback = WorkerFeignClientFallback.class)
public interface WorkerFeignClient {

	@GetMapping(value = "/names")
	List<String> allNames();

	@GetMapping(value = "/all")
	ResponseEntity<List<Worker>> findAll();

	@GetMapping(value = "/worker/{id}")
	ResponseEntity<Worker> findById(@PathVariable(value = "id") Long id);

}
