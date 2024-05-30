package mhha.springmhha.model.sqlSpring.angular.doc

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*

@Entity(name = "DocComponentsPropsValues")
data class DocComponentsPropsValues(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    var thisIndex: Long = 0,
    @Column(columnDefinition = "nvarchar(100)")
    var name: String = "",
    var optional: Boolean = false,
    var readonly: Boolean = false,
    @Column(columnDefinition = "nvarchar(100)")
    var type: String = "",
    @Column(columnDefinition = "nvarchar(300)")
    var description: String = "",
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn
    @JsonBackReference
    var docComponentsProps: DocComponentsProps? = null
) {
    fun setChild(): DocComponentsPropsValues {
        return this
    }
}