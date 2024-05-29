package mhha.springmhha.model.sqlSpring.angular

import com.fasterxml.jackson.annotation.JsonManagedReference
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*

@Entity(name = "DocComponentsTab")
data class DocComponentsTab(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "this_index")
    @get:JsonProperty("this_index")
    var thisIndex: Long = 0,
    @Column(columnDefinition = "nvarchar(100)")
    var name: String = "",
    @Column(columnDefinition = "nvarchar(300)")
    var description: String = "",
    @OneToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn
    @JsonManagedReference
    var docComponentsEmits: DocComponentsEmits? = null,
    @OneToOne(fetch = FetchType.EAGER,cascade = [CascadeType.ALL])
    @JoinColumn
    @JsonManagedReference
    var docComponentsProps: DocComponentsProps? = null
) {
  fun setChild() {
    docComponentsEmits?.docComponentsTab = this
    docComponentsProps?.docComponentsTab = this
    docComponentsEmits?.setChild()
    docComponentsProps?.setChild()
  }
}