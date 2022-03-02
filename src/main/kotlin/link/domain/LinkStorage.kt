package link.domain

fun findLinkByAlias(alias: String): Link? {
    TODO("Not yet implemented")
}

fun saveLink(link: Link): Boolean {
    TODO("Not yet implemented")
}

fun findLinksByUsername(alias: String): List<Link> {
    TODO("Not yet implemented")
}

data class Link(val username: String, val alias: String, val url: String, val active: Boolean)