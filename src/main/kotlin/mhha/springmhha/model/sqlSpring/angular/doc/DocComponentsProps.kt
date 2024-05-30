package mhha.springmhha.model.sqlSpring.angular.doc

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*

@Entity(name = "DocComponentsProps")
data class DocComponentsProps(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  var thisIndex: Long = 0,
  @Column(columnDefinition = "nvarchar(300)")
  var description: String = "",
  @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
  @JoinColumn
  @JsonManagedReference
  var docComponentsPropsValues: MutableList<DocComponentsPropsValues>? = null,
  @OneToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn
  @JsonBackReference
  var docComponents: DocComponents? = null
) {
  fun setChild(): DocComponentsProps {
    docComponentsPropsValues?.forEach {
      it.docComponentsProps = this
      it.setChild()
    }
    return this
  }
}