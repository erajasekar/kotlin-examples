import examplejava.OptionalExample
import examplejava.OptionalExample.javaMethodWithNullable
import java.util.*

fun main(args: Array<String>) {

    //Creating Optionals
    val name : String? = "Hello"

    //Transforming
    name?.toUpperCase();

    //Filter non empty from list of Optionals
    println(listOf(name, null).filterNotNull()) //prints [Hello]

    //Do something only if not null
    name.let { println(it) }

    //Default values in case of null
    var length = name?.length ?: 0

    //Throw exception in case of null
    length = name?.length  ?: throw IllegalArgumentException("Can't be null")

    callJavaNullableFunction();
}

fun callJavaNullableFunction(){

    //Will throw NPE since we are passing null value to Java parameter that is not annotated.
    var result = javaMethodWithNullable(null, "hello", "world")

    //Compilation error Null can't be value for non-null type String since we are passing null to Java parameter annotated as Not Null
    //result = javaMethodWithNullable("Hello", null, "world")

    //Compiles fine as we are passing null to Java parameter annotated as Nullable. But will result in NPE at runtime.
    result = javaMethodWithNullable("Hello", "World", null)

    println(result)
}