import auth.presentation.logInUser
import auth.presentation.signInUser
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.get
import io.javalin.apibuilder.ApiBuilder.post
import link.presentation.createLink
import link.presentation.visitLink

fun main() {
    Javalin.create()
        .start(8080)
        .routes {
            post("users", ::signInUser)
            post("users", ::logInUser)
            get("links/{alias}", ::visitLink)
            post("links", ::createLink)
        }
}