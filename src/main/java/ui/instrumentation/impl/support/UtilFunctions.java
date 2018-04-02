package ui.instrumentation.impl.support;

import javaslang.Tuple2;
import javaslang.control.Try;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Optional;


public class UtilFunctions {

    public static final String URL_FRAGMENT_DELIMITER = "#";
    public static final String EMPTY_URL_PATH = "";
    public static final String COMPONENT_DEF_URL_BASE64_ENCODED_PREFIX = "eyJjb21wb25lbnREZWYiOi";
    public  static String REDACTED_CMP_DEF = "%7B%22componentDef%22:%22$RedactedComponentDef%22%7D";

    public static Optional<String> sanitizeComponentDefUrl(String url){
        return extractURIPathAndFragment(url)
                .flatMap(UtilFunctions::executeComponentDefWhitelistRule)
                .filter(UtilFunctions::isValidSanitizedUrl)
                .flatMap(UtilFunctions::combinePathAndFragment);

    }

    private static  Optional<Tuple2<String,String>> executeComponentDefWhitelistRule(Tuple2<String,String> t){

        String path = t._1();
        String fragment = t._2();

        ComponentDefWhitelistRule componentDefWhitelistRule = new ComponentDefWhitelistRule();
        return Optional.ofNullable(componentDefWhitelistRule.getSafeVariant(fragment))
                .map(v -> new Tuple2<>(path,v));
    }

    private static Optional<String> combinePathAndFragment(Tuple2<String,String> t){
        String path = t._1();
        String fragment = t._2();
        StringBuilder result = new StringBuilder();
        if (!path.equals(EMPTY_URL_PATH)){
            result.append(path).append(URL_FRAGMENT_DELIMITER);
        }
        try {
            return Optional.of(result.append(URLEncoder.encode(fragment, "UTF-8")).toString());
        } catch (UnsupportedEncodingException e) {
            return Optional.empty();
        }
    }

    private static boolean isValidSanitizedUrl(Tuple2<String,String> t){
        return !t._2().isEmpty() && !t._2().equals(REDACTED_CMP_DEF);
    }

    /**
     * Extracts uri path and fragment from given url and returns as a Tuple.
     * @param url
     * @return
     */
    private static Optional<Tuple2<String,String>> extractURIPathAndFragment(String url){

        if (url.contains(URL_FRAGMENT_DELIMITER)) {
            return Try.of(() -> {
                URI uri = new URI(url);
                return new Tuple2<>(uri.getPath(), uri.getFragment());
            }).toJavaOptional();
        } else {
            if (url.startsWith(COMPONENT_DEF_URL_BASE64_ENCODED_PREFIX)){
                return Try.of(() -> new Tuple2<>(EMPTY_URL_PATH,URLDecoder.decode(url,"UTF-8"))).toJavaOptional();
            }else{
                return Optional.empty();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(sanitizeComponentDefUrl("/sObject/a07B0000004fT2lIAE/view"));
        System.out.println(sanitizeComponentDefUrl("https://gus.lightning.force.com/one/one.app#eyJjb21wb25lbnREZWYiOiJyZXBvcnRzOnJlcG9ydEJ1aWxkZXIiLCJhdHRyaWJ1dGVzIjp7InJlY29yZElkIjoiMDBPQjAwMDAwMDIyeGNYTUFRIiwibG9hZFN0b3JlZE1ldGFkYXRhIjp0cnVlLCJuZXdSZXBvcnRCdWlsZGVyIjp0cnVlfX0%3D"));
    }
}
