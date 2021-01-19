package i.design.handlers.jwt

import com.github.open_edgn.security4k.asymmetric.rsa.RsaPrivate
import com.github.open_edgn.security4k.asymmetric.rsa.RsaPublic
import org.springframework.stereotype.Component
import javax.annotation.Resource

@Component
class RsaUtils {
    @Resource
    private lateinit var rsaKeyPair: RSAKeyPair

    val rsaPublic by lazy {
        RsaPublic(rsaKeyPair.publicKey)
    }

    val rsaPrivate by lazy {
        RsaPrivate(rsaKeyPair.privateKey)
    }

}