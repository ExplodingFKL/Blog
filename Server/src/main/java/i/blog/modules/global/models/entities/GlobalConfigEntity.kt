package i.blog.modules.global.models.entities

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity(name = "t_global_config")
data class GlobalConfigEntity(
    @Id
    var k: String,
    @Column(nullable = false,length = 8192)
    val v: String
)