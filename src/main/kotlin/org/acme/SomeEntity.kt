package org.acme

import org.hibernate.annotations.Type
import java.util.UUID
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name="myTable")
data class SomeEntity(
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    val name: String?,
    @Column(columnDefinition = "uniqueidentifier")
    @Type(type = "uuid-char")
    val someUuid: UUID = UUID.randomUUID(),

    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "parentId")
    val children: List<ChildEntity> = listOf(),
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SomeEntity

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }

    override fun toString(): String {
        return "SomeEntity(id=$id, name=$name, someUuid=$someUuid)"
    }
}