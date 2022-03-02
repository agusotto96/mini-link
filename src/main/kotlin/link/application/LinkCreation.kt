package link.application

import auth.domain.decodeToken
import link.domain.Link
import link.domain.isValidUrl
import link.domain.saveLink
import link.domain.validateAlias

fun createLink(token: String, alias: String, url: String): LinkCreationError? {
    val username = decodeToken(token) ?: return LinkCreationError.InvalidToken
    if (validateAlias(alias) != null) return LinkCreationError.InvalidAlias
    if (!isValidUrl(url)) return LinkCreationError.InvalidUrl
    val link = Link(username, alias, url, true)
    if (!saveLink(link)) return LinkCreationError.LinkAlreadyExists
    return null
}

enum class LinkCreationError {
    InvalidToken,
    InvalidAlias,
    InvalidUrl,
    LinkAlreadyExists
}