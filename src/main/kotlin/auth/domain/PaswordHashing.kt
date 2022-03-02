package auth.domain

import java.util.Base64
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

private val encoder = Base64.getEncoder()
private val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
private const val iterationCount = 65536
private const val keyLength = 128

fun hashPassword(password: String, salt: String): String {
    val spec = PBEKeySpec(password.toCharArray(), salt.toByteArray(), iterationCount, keyLength)
    val key = factory.generateSecret(spec)
    return encoder.encodeToString(key.encoded)
}