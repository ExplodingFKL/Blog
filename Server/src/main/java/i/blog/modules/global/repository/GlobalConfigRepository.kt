package i.design.modules.global.repository

import i.design.modules.global.models.entities.GlobalConfigEntity
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface GlobalConfigRepository : CrudRepository<GlobalConfigEntity, String> {
}