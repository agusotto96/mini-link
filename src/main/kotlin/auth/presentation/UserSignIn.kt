package auth.presentation

import auth.application.SignInError
import auth.application.signInUser
import io.javalin.http.ContentType
import io.javalin.http.Context
import io.javalin.http.HttpCode
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.put

fun signInUser(context: Context) {
    val request = deserialize(context)
    if (request == null) {
        context.status(HttpCode.BAD_REQUEST)
    } else {
        val (username, password) = request
        val signInError = signInUser(username, password)
        if (signInError == null) {
            context.status(HttpCode.CREATED)
        } else {
            val json = serialize(signInError)
            context.result(json)
            context.contentType(ContentType.JSON)
            context.status(HttpCode.UNPROCESSABLE_ENTITY)
        }
    }
}

private fun deserialize(context: Context): SignInUserRequest? {
    return try {
        val jsonElement = Json.parseToJsonElement(context.body())
        val username = jsonElement.jsonObject["username"]!!.jsonPrimitive.content
        val password = jsonElement.jsonObject["password"]!!.jsonPrimitive.content
        SignInUserRequest(username, password)
    } catch (exception: Throwable) {
        null
    }
}

private fun serialize(signInError: SignInError): String {
    val message = when (signInError) {
        SignInError.InvalidUsernameLength -> "Invalid Username Length"
        SignInError.InvalidUsernameCharacter -> "Invalid Username Character"
        SignInError.InvalidPasswordLength -> "Invalid Password Length"
        SignInError.InvalidPasswordCharacter -> "Invalid Password Character"
        SignInError.UsernameAlreadyExists -> "Username Already Exists"
    }
    return buildJsonObject {
        put("message", message)
    }.toString()
}

data class SignInUserRequest(val username: String, val password: String)