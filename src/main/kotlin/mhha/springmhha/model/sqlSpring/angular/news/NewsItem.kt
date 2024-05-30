package mhha.springmhha.model.sqlSpring.angular.news

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class NewsItem(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	var thisIndex: Long = 0,
	@Column(columnDefinition = "nvarchar(100)")
	var content: String,
	@Column(columnDefinition = "nvarchar(300)")
	var linkHref: String,
	@Column(columnDefinition = "nvarchar(100)")
	var linkText: String,
) {

}
