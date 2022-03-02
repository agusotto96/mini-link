package auth.domain

private const val minLength = 5
private const val maxLength = 15
private val validCharacters = (('A'..'Z') + ('a'..'z') + ('0'..'9')).toSet()

fun validateUsername(username: String): UsernameError? {
    if (username.length !in minLength..maxLength) return UsernameError.InvalidLength
    if (!username.all(validCharacters::contains)) return UsernameError.InvalidCharacter
    return null
}

enum class UsernameError {
    InvalidLength,
    InvalidCharacter
}