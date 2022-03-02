package link.presentation

import io.javalin.core.util.Header
import io.javalin.http.Context
import io.javalin.http.HttpCode
import link.application.visitLink
import javax.servlet.http.HttpServletResponse

fun visitLink(context: Context) {
    val (alias, ip, userAgent, acceptLanguage) = deserialize(context)
    val url = visitLink(alias, ip, userAgent, acceptLanguage)
    if (url == null) {
        context.status(HttpCode.NOT_FOUND)
    } else {
        context.redirect(url, HttpServletResponse.SC_MOVED_PERMANENTLY)
    }
}

private fun deserialize(context: Context): VisitLinkRequest {
    val alias = context.pathParam("alias")
    val ip = context.ip()
    val userAgent = context.userAgent()
    val acceptLanguage = context.header(Header.ACCEPT_LANGUAGE)
    return VisitLinkRequest(alias, ip, userAgent, acceptLanguage)
}

data class VisitLinkRequest(val alias: String, val ip: String, val userAgent: String?, val acceptLanguage: String?)