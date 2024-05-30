package mhha.springmhha.model.sqlSpring.angular

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*

@Entity(name = "DocComponentsEmitsValues")
data class DocComponentsEmitsValues(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  var thisIndex: Long = 0,
  @Column(columnDefinition = "nvarchar(100)")
  var name: String = "",
  @Column(columnDefinition = "nvarchar(300)")
  var description: String = "",
  @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
  @JoinColumn
  @JsonManagedReference
  var docComponentsEmitsValuesParameters: MutableList<DocComponentsEmitsValuesParameters>? = null,
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn
  @JsonBackReference
  var docComponentsEmits: DocComponentsEmits? = null
) {
  fun setChild(): DocComponentsEmitsValues {
    docComponentsEmitsValuesParameters?.forEach {
      it.docComponentsEmitsValues = this
      it.setChild()
    }
    return this
  }
}