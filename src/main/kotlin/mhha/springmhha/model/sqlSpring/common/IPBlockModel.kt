package mhha.springmhha.model.sqlSpring.common

import jakarta.persistence.*

@Entity
data class IPBlockModel(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	var thisIndex: Long = 0,
	@Column(columnDefinition = "nvarchar(100)")
	var ipAddr: String = "255.255.255.255",
	@Column
	var isBlock: Boolean = false,
	@Column(columnDefinition = "nvarchar(300)")
	var etcData: String? = null,
)