package auth.domain

private const val minLength = 5
private const val maxLength = 15
private val validCharacters = (('A'..'Z') + ('a'..'z') + ('0'..'9')).toSet()

fun validatePassword(password: String): PasswordError? {
    if (password.length !in minLength..maxLength) return PasswordError.InvalidLength
    if (!password.all(validCharacters::contains)) return PasswordError.InvalidCharacter
    return null
}

enum class PasswordError {
    InvalidLength,
    InvalidCharacter
}