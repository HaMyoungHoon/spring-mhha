package mhha.springmhha.model.sqlSpring.angular.doc

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*

@Entity(name = "DocComponents")
data class DocComponents(
  @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    var thisIndex: Long = 0,
  @Column(columnDefinition = "nvarchar(100)")
    var name: String = "",
  @Column(columnDefinition = "nvarchar(300)")
    var description: String = "",
  @OneToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn
    @JsonManagedReference
    var docComponentsEmits: DocComponentsEmits? = null,
  @OneToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn
    @JsonManagedReference
    var docComponentsProps: DocComponentsProps? = null,
  @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn
    @JsonBackReference
    var docPage: DocPage? = null
) {
  fun setChild(): DocComponents {
    docComponentsEmits?.docComponents = this
    docComponentsProps?.docComponents = this
    docComponentsEmits?.setChild()
    docComponentsProps?.setChild()
    return this
  }
}