package mhha.springmhha.model

data class MqttConnectModel(
	var brokerUrl: String = "",
	var userName: String = "",
	var password: String = "",
) {
}