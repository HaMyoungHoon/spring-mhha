package mhha.springmhha.service

import mhha.springmhha.advice.exception.AuthenticationEntryPointException
import mhha.springmhha.config.security.JwtTokenProvider
import mhha.springmhha.model.MqttConnectModel
import mhha.springmhha.model.sqlASP.UserData
import mhha.springmhha.model.sqlASP.UserRole
import org.eclipse.paho.client.mqttv3.IMqttClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.integration.mqtt.core.MqttPahoClientFactory
import org.springframework.integration.support.MessageBuilder
import org.springframework.messaging.MessageChannel
import org.springframework.stereotype.Service
import java.lang.Exception
import java.nio.charset.StandardCharsets

@Service
class MqttService {
	@Value(value = "\${mqtt.brokerUrl1}") lateinit var brokerUrl1: String
	@Value(value = "\${mqtt.brokerUrl2}") lateinit var brokerUrl2: String
	@Value(value = "\${mqtt.clientId}") lateinit var clientId: String
	@Value(value = "\${mqtt.username}") lateinit var userName: String
	@Value(value = "\${mqtt.password}") lateinit var password: String
	@Autowired lateinit var mqttOutboundChannel: MessageChannel
	@Autowired lateinit var mqttClientFactory: MqttPahoClientFactory
	@Autowired lateinit var jwtTokenProvider: JwtTokenProvider

	fun getMqttConnectData(token: String? = null, guid: String? = null): MqttConnectModel =
		if (isAdmin(token, false)) MqttConnectModel(getBrokerUrl(), getLoginTopic(token!!, guid), this.userName, this.password)
		else MqttConnectModel(getBrokerUrl(), getDefaultTopic(guid), this.userName, this.password)
	fun sendMessage(token: String? = null, guid: String? = null, topic: String, payload: String) {
		val msg = if (isAdmin(token, false)) {
			MessageBuilder.withPayload(payload.toByteArray(StandardCharsets.UTF_8))
				.setHeader("mqtt_topic", topic)
				.build()
		} else {
			MessageBuilder.withPayload(payload.toByteArray(StandardCharsets.UTF_8))
				.setHeader("mqtt_topic", "test/${guid}")
				.build()
		}
		mqttOutboundChannel.send(msg)
	}

	fun getMqttClient(): IMqttClient = mqttClientFactory.getClientInstance(brokerUrl1, clientId)
	fun getMqttClient(brokerUrl: String? = null, clientId: String? = null): IMqttClient = mqttClientFactory.getClientInstance(brokerUrl ?: this.brokerUrl1, clientId ?: this.clientId)
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
	fun disconnectMqtt(brokerUrl: String? = null, clientId: String? = null) {
		try {
			getMqttClient(brokerUrl, clientId).let {
				if (it.isConnected) {
					it.disconnect()
				}
			}
		} catch (_: Exception) {
		}
	}

	fun getBrokerUrl(): MutableList<String> {
		val ret = mutableListOf<String>()
		ret.add(brokerUrl1)
		ret.add(brokerUrl2)
		return ret
	}
	fun getLoginTopic(token: String, guid: String? = null): MutableList<String> {
		val ret = mutableListOf<String>()
		val userData = jwtTokenProvider.getUserData(token)
		ret.add("notice")
		guid?.let { ret.add("test/${it}") }
		ret.add("private/${userData.thisIndex}")
		return ret
	}
	fun getDefaultTopic(guid: String? = null): MutableList<String> {
		val ret = mutableListOf<String>()
		ret.add("notice")
		guid?.let { ret.add("test/${it}") }
		return ret
	}

	fun isAdmin(token: String?, notAdminThrow: Boolean = true): Boolean =
		token?.let { x ->
			val user = jwtTokenProvider.getUserData(x)
			if (UserRole.fromFlag(user.role).contains(UserRole.Admin)) true
			else if (notAdminThrow) throw AuthenticationEntryPointException()
			else false
		} ?: if (notAdminThrow) throw AuthenticationEntryPointException() else false
}