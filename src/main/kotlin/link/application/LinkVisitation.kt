package link.application

import link.domain.findLinkByAlias

fun visitLink(alias: String, ip: String, userAgent: String?, acceptLanguage: String?): String? {
    val (_, _, url) = findLinkByAlias(alias) ?: return null
    return url
}