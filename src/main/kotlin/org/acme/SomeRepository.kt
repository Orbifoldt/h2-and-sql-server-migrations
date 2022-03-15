package org.acme

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase
import javax.enterprise.context.ApplicationScoped
import javax.transaction.Transactional

@ApplicationScoped
class SomeRepository : PanacheRepositoryBase<SomeEntity, Long> {

    @Transactional
    fun persistReturning(entity: SomeEntity) = entity.also { persist(entity) }
}