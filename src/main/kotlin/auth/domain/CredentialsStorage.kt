package auth.domain

fun findCredentialsByUsername(username: String): Credentials? {
    TODO("Not yet implemented")
}

fun saveCredentials(credentials: Credentials): Boolean {
    TODO("Not yet implemented")
}

data class Credentials(val username: String, val hash: String, val salt: String)