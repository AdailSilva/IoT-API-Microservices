package com.adailsilva.iot.api.mqtt.eclipsemosquitto.configuration;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

@Configuration
public class EclipsePahoBeans {

	@Value("${eclipse.paho.uri}")
	private String uri;

	@Bean
	public MqttPahoClientFactory mqttClientFactory() {
		DefaultMqttPahoClientFactory defaultMqttPahoClientFactory = new DefaultMqttPahoClientFactory();
		MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
		mqttConnectOptions.setServerURIs(new String[] { uri });
		mqttConnectOptions.setUserName("adailsilva");
		mqttConnectOptions.setPassword("@Hacker101".toCharArray());
		mqttConnectOptions.setCleanSession(true);
		mqttConnectOptions.setAutomaticReconnect(true);

		defaultMqttPahoClientFactory.setConnectionOptions(mqttConnectOptions);

		return defaultMqttPahoClientFactory;
	}

	@Bean
	public MessageChannel mqttInboundChannel() {
		return new DirectChannel();
	}

	@Bean
	public MessageProducer mqttInbound() {
		MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter("serverIn",
				mqttClientFactory(), "#"); // Console exibe dados de acordo com este tópico.
		adapter.setCompletionTimeout(5000);
		adapter.setConverter(new DefaultPahoMessageConverter());
		adapter.setQos(2);
		adapter.setOutputChannel(mqttInboundChannel());
		return adapter;
	}

	@Bean
	@ServiceActivator(inputChannel = "mqttInboundChannel")
	public MessageHandler handler() {
		return new MessageHandler() {

			@Override
			public void handleMessage(Message<?> message) throws MessagingException {
				if (message.getHeaders().get(MqttHeaders.RECEIVED_TOPIC).toString().equals("/topic/adailsilva")) {
					System.out.println("### AdailSilva topic ###");
				}

				// Headers MQTT examples:
				// {mqtt_receivedRetained=false, mqtt_id=0, mqtt_duplicate=false,
				// id=132629bb-510f-e459-8bb0-6b1eeef13f0c,
				// mqtt_receivedTopic=/topic/energymeter, mqtt_receivedQos=0,
				// timestamp=1656168752122}
				// {mqtt_receivedRetained=false, mqtt_id=0, mqtt_duplicate=false,
				// id=80225cc8-b79b-4a72-7bb2-1c7113320fe4,
				// mqtt_receivedTopic=gateway/b827ebfffef518e3/event/stats, mqtt_receivedQos=0,
				// timestamp=1656168639664}
				// {mqtt_receivedRetained=false, mqtt_id=0, mqtt_duplicate=false,
				// id=4ffe8d57-2c37-9f03-f659-a2d8c7fafd5d,
				// mqtt_receivedTopic=gateway/b827ebfffe40dcc6/event/stats, mqtt_receivedQos=0,
				// timestamp=1656168759434}
				System.out.println("Headers:\n" + message.getHeaders() + "\n");
				System.out.println("Payload:\n" + message.getPayload() + "\n");

			}

		};
	}

	@Bean
	public MessageChannel mqttOutboundChannel() {
		return new DirectChannel();
	}

	@Bean
	@ServiceActivator(inputChannel = "mqttOutboundChannel")
	public MessageHandler mqttOutbound() {
		// clientId is generated using a random number
		MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler("serverOut", mqttClientFactory());
		messageHandler.setAsync(true);
		messageHandler.setDefaultTopic("#");
		messageHandler.setDefaultQos(2);
		messageHandler.setDefaultRetained(false);
		return messageHandler;
	}

}
