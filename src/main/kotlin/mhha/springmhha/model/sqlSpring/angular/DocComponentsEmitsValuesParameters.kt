package mhha.springmhha.model.sqlSpring.angular

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*

@Entity(name = "DocComponentsEmitsValuesParameters")
data class DocComponentsEmitsValuesParameters(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    var thisIndex: Long = 0,
    @Column(columnDefinition = "nvarchar(100)")
    var name: String = "",
    @Column(columnDefinition = "nvarchar(100)")
    var type: String = "",
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn
    @JsonBackReference
    var docComponentsEmitsValues: DocComponentsEmitsValues? = null
) {
  fun setChild(): DocComponentsEmitsValuesParameters {
      return this
  }
}