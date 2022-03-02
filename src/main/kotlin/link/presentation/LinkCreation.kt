package link.presentation

import io.javalin.http.ContentType
import io.javalin.http.Context
import io.javalin.http.HttpCode
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.put
import link.application.LinkCreationError
import link.application.createLink

fun createLink(context: Context) {
    val request = deserialize(context)
    if (request == null) {
        context.status(HttpCode.BAD_REQUEST)
    } else {
        val (token, alias, url) = request
        val linkCreationError = createLink(token, alias, url)
        if (linkCreationError == null) {
            context.status(HttpCode.CREATED)
        } else {
            val json = serialize(linkCreationError)
            context.result(json)
            context.contentType(ContentType.JSON)
            val status = when (linkCreationError) {
                LinkCreationError.InvalidToken -> HttpCode.UNAUTHORIZED
                LinkCreationError.InvalidAlias -> HttpCode.UNPROCESSABLE_ENTITY
                LinkCreationError.InvalidUrl -> HttpCode.UNPROCESSABLE_ENTITY
                LinkCreationError.LinkAlreadyExists -> HttpCode.UNPROCESSABLE_ENTITY
            }
            context.status(status)
        }
    }
}

private fun deserialize(context: Context): CreateLinkRequest? {
    val header = context.header("Authorization") ?: return null
    val token = header.removePrefix("Bearer ")
    return try {
        val jsonElement = Json.parseToJsonElement(context.body())
        val alias = jsonElement.jsonObject["alias"]!!.jsonPrimitive.content
        val url = jsonElement.jsonObject["url"]!!.jsonPrimitive.content
        return CreateLinkRequest(token, alias, url)
    } catch (exception: Throwable) {
        null
    }
}

private fun serialize(linkCreationError: LinkCreationError): String {
    val message = when (linkCreationError) {
        LinkCreationError.InvalidToken -> "Invalid Token"
        LinkCreationError.InvalidAlias -> "Invalid Alias"
        LinkCreationError.InvalidUrl -> "Invalid Url"
        LinkCreationError.LinkAlreadyExists -> "Link Already Exists"
    }
    return buildJsonObject {
        put("message", message)
    }.toString()
}

data class CreateLinkRequest(val token: String, val alias: String, val url: String)