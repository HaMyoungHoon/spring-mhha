package mhha.springmhha.model.sqlSpring.angular

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*

@Entity(name = "DocComponentsEmits")
data class DocComponentsEmits(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    var thisIndex: Long = 0,
    @Column(columnDefinition = "nvarchar(300)")
    var description: String = "",
    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn
    @JsonManagedReference
    var docComponentsEmitsValues: MutableList<DocComponentsEmitsValues>? = null,
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn
    @JsonBackReference
    var docComponents: DocComponents? = null
) {
    fun setChild(): DocComponentsEmits {
        this.docComponentsEmitsValues?.forEach {
            it.docComponentsEmits = this
            it.setChild()
        }
        return this
    }
}