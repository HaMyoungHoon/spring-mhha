package mhha.springmhha.model.sqlSpring.angular

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*

@Entity(name = "DocComponentsProps")
data class DocComponentsProps(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "this_index")
    @get:JsonProperty("this_index")
    var thisIndex: Long = 0,
    @Column(columnDefinition = "nvarchar(300)")
    var description: String = "",
    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn
    @JsonManagedReference
    var docComponentsPropsValue: MutableList<DocComponentsPropsValue>? = null,
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn
    @JsonBackReference
    var docComponentsTab: DocComponentsTab? = null
) {
  fun setChild() {
    docComponentsPropsValue?.forEach {
      it.docComponentsProps = this
      it.setChild()
    }
  }
}