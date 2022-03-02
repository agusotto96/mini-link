package auth.application

import auth.domain.Credentials
import auth.domain.PasswordError
import auth.domain.UsernameError
import auth.domain.generateSalt
import auth.domain.hashPassword
import auth.domain.saveCredentials
import auth.domain.validatePassword
import auth.domain.validateUsername

fun signInUser(username: String, password: String): SignInError? {
    val usernameError = validateUsername(username)
    if (usernameError != null) {
        return when (usernameError) {
            UsernameError.InvalidLength -> SignInError.InvalidUsernameLength
            UsernameError.InvalidCharacter -> SignInError.InvalidUsernameCharacter
        }
    }
    val passwordError = validatePassword(password)
    if (passwordError != null) {
        return when (passwordError) {
            PasswordError.InvalidLength -> SignInError.InvalidPasswordLength
            PasswordError.InvalidCharacter -> SignInError.InvalidPasswordCharacter
        }
    }
    val salt = generateSalt()
    val hash = hashPassword(password, salt)
    val credentials = Credentials(username, hash, salt)
    return when (saveCredentials(credentials)) {
        false -> SignInError.UsernameAlreadyExists
        true -> null
    }
}

enum class SignInError {
    InvalidUsernameLength,
    InvalidUsernameCharacter,
    InvalidPasswordLength,
    InvalidPasswordCharacter,
    UsernameAlreadyExists
}