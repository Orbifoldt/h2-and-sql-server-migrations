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
class DatabaseTestIT {

    @Inject
    lateinit var repo: SomeRepository

    @Test
    @DataSet("datasets/sampleData.yml")
    internal fun `fetching test data`(){
        val id = 1001L
        val found: SomeEntity? = repo.find("id", id).firstResult()
        assertThat(found?.name).isEqualTo("blabla")
    }

    @Test
    @DataSet("datasets/sampleData.yml")
    internal fun `fetching test data that has children`(){
        val id = 1002L
        val found: SomeEntity? = repo.find("id", id).firstResult()

        assertThat(found).isNotNull
        found!!
        assertThat(found.name).isEqualTo("theSecond")
        assertThat(found.children).hasSize(3)
        assertThat(found.children.find { it.id == 101L }?.name).isEqualTo("child1")
    }

    @Test
    internal fun `Inserting and then fetching data`(){
        val uuid = UUID.randomUUID()
        println(uuid)
        val entity = SomeEntity(id = null, name = "abc", someUuid = uuid)

        repo.persistReturning(entity)
        val id = entity.id

        val found = repo.find("id", id).firstResult<SomeEntity>()
        assertThat(found.id).isEqualTo(id)
        assertThat(found.someUuid).isEqualTo(uuid)
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