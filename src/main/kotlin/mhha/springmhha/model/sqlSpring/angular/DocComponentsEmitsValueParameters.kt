package mhha.springmhha.model.sqlSpring.angular

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*

@Entity(name = "DocComponentsEmitsValueParameters")
data class DocComponentsEmitsValueParameters(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "this_index")
    @get:JsonProperty("this_index")
    var thisIndex: Long = 0,
    @Column(columnDefinition = "nvarchar(100)")
    var name: String = "",
    @Column(columnDefinition = "nvarchar(100)")
    var type: String = "",
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn
    @JsonBackReference
    var docComponentsEmitsValue: DocComponentsEmitsValue? = null
) {
  fun setChild() {

  }
}