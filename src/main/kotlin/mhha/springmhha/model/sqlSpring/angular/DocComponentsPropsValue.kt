package mhha.springmhha.model.sqlSpring.angular

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*

@Entity(name = "DocComponentsPropsValue")
data class DocComponentsPropsValue(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "this_index")
    @get:JsonProperty("this_index")
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
    fun setChild() {

    }
}