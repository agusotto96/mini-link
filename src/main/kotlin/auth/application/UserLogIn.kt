package auth.application

import auth.domain.encodeToken
import auth.domain.findCredentialsByUsername
import auth.domain.hashPassword

fun logInUser(username: String, password: String): String? {
    val (_, hash, salt) = findCredentialsByUsername(username) ?: return null
    if (hash != hashPassword(password, salt)) return null
    return encodeToken(username)
}