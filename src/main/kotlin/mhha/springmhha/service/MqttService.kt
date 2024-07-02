package mhha.springmhha.service

import mhha.springmhha.model.MqttConnectModel
import org.eclipse.paho.client.mqttv3.IMqttClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.integration.mqtt.core.MqttPahoClientFactory
import org.springframework.integration.support.MessageBuilder
import org.springframework.messaging.MessageChannel
import org.springframework.stereotype.Service
import java.lang.Exception

@Service
class MqttService {
	@Value(value = "\${mqtt.brokerUrl}") lateinit var brokerUrl: String
	@Value(value = "\${mqtt.clientId}") lateinit var clientId: String
	@Value(value = "\${mqtt.username}") lateinit var userName: String
	@Value(value = "\${mqtt.password}") lateinit var password: String
	@Autowired lateinit var mqttOutboundChannel: MessageChannel
	@Autowired lateinit var mqttClientFactory: MqttPahoClientFactory

	fun getMqttConnectData(): MqttConnectModel {
		return MqttConnectModel(brokerUrl, userName, password)
	}
	fun sendMessage(topic: String, payload: String) {
		val msg = MessageBuilder.withPayload(payload)
			.setHeader("mqtt_topic", topic)
			.build()
		mqttOutboundChannel.send(msg)
	}

	fun getMqttClient(): IMqttClient = mqttClientFactory.getClientInstance(brokerUrl, clientId)
	fun disconnectMqtt() {
		try {
			getMqttClient().let {
				if (it.isConnected) {
					it.disconnect()
				}
			}
		} catch (_: Exception) {

		}
	}
}