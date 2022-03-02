package link.domain

import org.apache.commons.validator.routines.UrlValidator

private val urlValidator = UrlValidator()

fun isValidUrl(url: String): Boolean {
    return urlValidator.isValid(url)
}