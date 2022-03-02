package auth.domain

import java.security.SecureRandom
import java.util.Base64

private val encoder = Base64.getEncoder()
private const val saltSize = 16

fun generateSalt(): String {
    val bytes = ByteArray(saltSize)
    SecureRandom().nextBytes(bytes)
    return encoder.encodeToString(bytes)
}