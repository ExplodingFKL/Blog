package i.blog.utils

import com.github.openEdgn.logger4k.getLogger
import com.github.open_edgn.security4k.asymmetric.rsa.RsaKeyGenerator
import org.junit.jupiter.api.Test

class RsaCreate {
    val logger = getLogger()

    @Test
    fun create() {
        val generator = RsaKeyGenerator(2048)
        logger.info("公钥:\r\n{}", generator.publicKeyText.replace(Regex("(\r|\n)"),""))
            .info("私钥:\r\n{}", generator.privateKeyText.replace(Regex("(\r|\n)"),""))
    }
}