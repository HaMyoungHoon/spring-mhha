package mhha.springmhha.model.sqlSpring.angular

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*

@Entity(name = "DocComponentsEmitsValue")
data class DocComponentsEmitsValue(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "this_index")
    @get:JsonProperty("this_index")
    var thisIndex: Long = 0,
    @Column(columnDefinition = "nvarchar(100)")
    var name: String = "",
    @Column(columnDefinition = "nvarchar(300)")
    var description: String = "",
    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn
    @JsonManagedReference
    var docComponentsEmitsValueParameters: MutableList<DocComponentsEmitsValueParameters>? = null,
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn
    @JsonBackReference
    var docComponentsEmits: DocComponentsEmits? = null
) {
  fun setChild() {
    docComponentsEmitsValueParameters?.forEach {
      it.docComponentsEmitsValue = this
      it.setChild()
    }
  }
}