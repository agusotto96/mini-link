package auth.domain

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.Date

private const val secret = "secret"
private val algorithm: Algorithm = Algorithm.HMAC256(secret)
private val verifier: JWTVerifier = JWT.require(algorithm).build()
private const val tokenMinutesDuration = 30L

fun encodeToken(username: String): String {
    return JWT.create()
        .withSubject(username)
        .withExpiresAt(Date.from(Instant.now().plus(tokenMinutesDuration, ChronoUnit.MINUTES)))
        .sign(algorithm)
}

fun decodeToken(token: String): String? {
    return try {
        val decodedToken = verifier.verify(token)
        decodedToken.subject
    } catch (exception: JWTVerificationException) {
        null
    }
}