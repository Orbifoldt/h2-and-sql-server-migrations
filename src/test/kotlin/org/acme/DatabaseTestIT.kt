package org.acme

import io.quarkus.test.junit.QuarkusTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.UUID
import javax.inject.Inject

@QuarkusTest
class DatabaseTestIT {

    @Inject
    lateinit var repo: SomeRepository

    @Test
    internal fun test(){
        val entity = SomeEntity(id = null, name = "abc", someUuid = UUID.randomUUID())

        repo.persistReturning(entity)
        println(entity.id)
        val id = entity.id

        val found = repo.find("id", id).firstResult<SomeEntity>()
        assertThat(found.id).isEqualTo(id)

    }
}