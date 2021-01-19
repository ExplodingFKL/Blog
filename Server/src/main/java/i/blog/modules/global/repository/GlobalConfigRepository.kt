package i.blog.modules.global.repository

import i.blog.modules.global.models.entities.GlobalConfigEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface GlobalConfigRepository : CrudRepository<GlobalConfigEntity, String> {
}