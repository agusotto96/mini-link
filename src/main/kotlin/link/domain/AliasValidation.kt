package link.domain

private const val minLength = 5
private const val maxLength = 15
private val validCharacters = (('A'..'Z') + ('a'..'z') + ('0'..'9')).toSet()

fun validateAlias(alias: String): AliasError? {
    if (alias.length !in minLength..maxLength) return AliasError.InvalidLength
    if (!alias.all(validCharacters::contains)) return AliasError.InvalidCharacter
    return null
}

enum class AliasError {
    InvalidLength,
    InvalidCharacter
}