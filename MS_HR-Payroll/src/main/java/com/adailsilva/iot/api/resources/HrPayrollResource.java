package com.adailsilva.iot.api.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RefreshScope // For all classes that have access to some custom configuration
@RestController
@RequestMapping(value = "/hr-payroll")
public class HrPayrollResource {

	private static Logger logger = LoggerFactory.getLogger(HrPayrollResource.class);

	@Autowired
	private Environment environment;

//	@Value("${adailsilva.config}")
	private String remoteConfiguration;

	// Reading the configuration contained in the configuration server
	@GetMapping(value = "/configs")
	public ResponseEntity<Void> getConfigs() {

		logger.info("PORT = " + environment.getProperty("local.server.port"));

		logger.info("[ Remote Configuration ] --> " + remoteConfiguration);
		return ResponseEntity.noContent().build();
	}

}
