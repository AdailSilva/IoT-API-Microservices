package com.adailsilva.iot.api.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adailsilva.iot.api.mqtt.eclipsemosquitto.commons.Util;
import com.adailsilva.iot.api.mqtt.eclipsemosquitto.gateway.MqttGateway;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@RestController
@RequestMapping(path = "mqtt")
public class EclipseMosquittoMqttController {

	@Autowired
	private MqttGateway mqttGateway;

	@PostMapping("/sendMessage")
	public ResponseEntity<?> publish(@RequestBody String mqttMessage) {

		try {
			JsonObject convertObject = new Gson().fromJson(mqttMessage, JsonObject.class);
			mqttGateway.sendToMqtt(Util.removeDoubleQuotes(convertObject.get("topic").toString()),
					convertObject.get("message").toString());
			return ResponseEntity.ok("Success");
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.ok("Fail");
		}

	}

}
