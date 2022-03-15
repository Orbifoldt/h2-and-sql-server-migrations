package org.acme

import com.github.database.rider.cdi.api.DBUnitInterceptor
import com.github.database.rider.core.api.dataset.DataSet
import io.quarkus.test.junit.QuarkusTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.UUID
import javax.inject.Inject

@QuarkusTest
@DBUnitInterceptor
@DataSet("datasets/sampleData.yml")
class DatabaseTestIT {

    @Inject
    lateinit var repo: SomeRepository

    @Test
    internal fun `fetching test data`(){
        val id = 1L
        val found: SomeEntity? = repo.find("id", id).firstResult()
        assertThat(found?.name).isEqualTo("blabla")
    }

    @Test
    internal fun `fetching test data that has children`(){
        val id = 2L
        val found: SomeEntity? = repo.find("id", id).firstResult()

        assertThat(found).isNotNull
        found!!
        assertThat(found.name).isEqualTo("theSecond")
        assertThat(found.children).hasSize(3)
        assertThat(found.children.find { it.id == 1L }?.name).isEqualTo("child1")
    }

    @Test
    internal fun `Inserting and then fetching data`(){
        val entity = SomeEntity(id = null, name = "abc", someUuid = UUID.randomUUID())

        repo.persistReturning(entity)
        val id = entity.id

        val found = repo.find("id", id).firstResult<SomeEntity>()
        assertThat(found.id).isEqualTo(id)
    }

    @Test
    internal fun `inserting data which contains children and fetching it again`(){
        val entity = SomeEntity(id = null, name = "abc", someUuid = UUID.randomUUID(), children = listOf(
            ChildEntity(id = null, name = "barn-ett"),
            ChildEntity(id = null, name = "barn-två")
        ))

        repo.persistReturning(entity)
        val id = entity.id

        val found = repo.find("id", id).firstResult<SomeEntity>()

        assertThat(found).isNotNull
        assertThat(found.id).isEqualTo(id)
        assertThat(found.children).hasSize(2)
        assertThat(found.children[0].name).isEqualTo("barn-ett")
        assertThat(found.children[1].name).isEqualTo("barn-två")
    }
}