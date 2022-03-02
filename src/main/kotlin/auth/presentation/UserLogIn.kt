package auth.presentation

import auth.application.logInUser
import io.javalin.http.ContentType
import io.javalin.http.Context
import io.javalin.http.HttpCode
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.put

fun logInUser(context: Context) {
    val request = deserialize(context)
    if (request == null) {
        context.status(HttpCode.BAD_REQUEST)
    } else {
        val (username, password) = request
        val token = logInUser(username, password)
        if (token == null) {
            context.status(HttpCode.UNAUTHORIZED)
        } else {
            val json = serialize(token)
            context.result(json)
            context.contentType(ContentType.JSON)
            context.status(HttpCode.OK)
        }
    }
}

private fun deserialize(context: Context): LogInUserRequest? {
    return try {
        val jsonElement = Json.parseToJsonElement(context.body())
        val username = jsonElement.jsonObject["username"]!!.jsonPrimitive.content
        val password = jsonElement.jsonObject["password"]!!.jsonPrimitive.content
        LogInUserRequest(username, password)
    } catch (exception: Throwable) {
        null
    }
}

private fun serialize(token: String): String {
    return buildJsonObject {
        put("token", token)
    }.toString()
}

data class LogInUserRequest(val username: String, val password: String)