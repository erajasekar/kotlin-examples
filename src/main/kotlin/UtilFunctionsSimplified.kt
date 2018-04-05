package examplejava

import javaslang.control.Try
import ui.instrumentation.impl.support.ComponentDefWhitelistRule
import ui.instrumentation.impl.support.UtilFunctions.*
import java.io.UnsupportedEncodingException
import java.net.URI
import java.net.URLDecoder
import java.net.URLEncoder

data class PathFragment(val path : String, val fragment: String)


fun sanitizeComponentDefUrl(url: String): String? {

    val pf : PathFragment? = executeComponentDefWhitelistRule(extractURIPathAndFragment(url))

    return if(isValidSanitizedUrl(pf)) combinePathAndFragment(pf) else null

}


private fun executeComponentDefWhitelistRule(pf: PathFragment?): PathFragment? {
    return pf?.let {
        val (path, fragment ) = it
        PathFragment(path , ComponentDefWhitelistRule().getSafeVariant(fragment))
    }
}

private fun combinePathAndFragment(pf: PathFragment?): String? {

    return pf?.let {
        val (path, fragment) = it;
        val result = StringBuilder()
        if (path != EMPTY_URL_PATH) {
            result.append(path).append(URL_FRAGMENT_DELIMITER)
        }
        try {
            return result.append(URLEncoder.encode(fragment, "UTF-8")).toString()
        } catch (e: UnsupportedEncodingException) {
            return null
        }
    }

}

private fun isValidSanitizedUrl(pf: PathFragment?): Boolean {
    return !(pf?.fragment?.isEmpty() ?:false || pf?.fragment == REDACTED_CMP_DEF)
}

/**
 * Extracts uri path and fragment from given url and returns as a Tuple.
 * @param url
 * @return
 */
private fun extractURIPathAndFragment(url: String): PathFragment? {

    if (url.contains(URL_FRAGMENT_DELIMITER)) {
        return Try.of {
            val uri = URI(url)
            PathFragment(uri.path, uri.fragment)
        }.getOrElse { null }
    } else {
        if (url.startsWith(COMPONENT_DEF_URL_BASE64_ENCODED_PREFIX)) {
            return Try.of({PathFragment(EMPTY_URL_PATH,URLDecoder.decode(url,"UTF-8"))}).getOrElse { null }
        } else {
            return null
        }
    }
}

fun main(args : Array<String>) {
    println(sanitizeComponentDefUrl("/sObject/a07B0000004fT2lIAE/view"))
    println(sanitizeComponentDefUrl("https://gus.lightning.force.com/one/one.app#eyJjb21wb25lbnREZWYiOiJyZXBvcnRzOnJlcG9ydEJ1aWxkZXIiLCJhdHRyaWJ1dGVzIjp7InJlY29yZElkIjoiMDBPQjAwMDAwMDIyeGNYTUFRIiwibG9hZFN0b3JlZE1ldGFkYXRhIjp0cnVlLCJuZXdSZXBvcnRCdWlsZGVyIjp0cnVlfX0%3D"))
}

