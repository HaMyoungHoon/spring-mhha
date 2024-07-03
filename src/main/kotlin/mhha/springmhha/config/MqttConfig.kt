package mhha.springmhha.config

import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.springframework.messaging.MessageHandler
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.integration.channel.DirectChannel
import org.springframework.integration.dsl.IntegrationFlow
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler

@Configuration
class MqttConfig {
	@Value(value = "\${mqtt.brokerUrl1}") lateinit var brokerUrl1: String
	@Value(value = "\${mqtt.clientId}") lateinit var clientId: String
	@Value(value = "\${mqtt.username}") lateinit var userName: String
	@Value(value = "\${mqtt.password}") lateinit var password: String

	@Bean
	fun mqttInputChannel() = DirectChannel()
	@Bean
	fun mqttOutboundChannel() = DirectChannel()

	@Bean
	fun mqttConnectOptions() = MqttConnectOptions().apply {
		this.userName = this@MqttConfig.userName
		this.password = this@MqttConfig.password.toCharArray()
	}
	@Bean
	fun mqttClientFactory() = DefaultMqttPahoClientFactory().apply {
		connectionOptions = mqttConnectOptions()
	}
//	@Bean
//	fun mqttInbound() = MqttPahoMessageDrivenChannelAdapter(brokerUrl, clientId, mqttClientFactory()).apply {
//		outputChannel = mqttInputChannel()
//	}
//	@Bean
//	@ServiceActivator(inputChannel = "mqttInputChannel")
//	fun handler(): MessageHandler = MessageHandler { x ->
//		println(x.payload)
//	}
	@Bean
	@ServiceActivator(inputChannel = "mqttOutboundChannel")
	fun mqttOutbound() = MqttPahoMessageHandler(brokerUrl1, clientId, mqttClientFactory()).apply {
		setAsync(true)
	}
	@Bean
	fun sendMessageFlow() = IntegrationFlow.from(mqttOutboundChannel())
		.handle(mqttOutbound())
		.get()
}