import java.util.*

fun main(args: Array<String>) {

    //Creating Optionals
    val name : String? = "Hello"

    //Transforming
    name?.toUpperCase();

    //Filter non null
    name?.isNotEmpty()

    //Do something only if not null
    name.let { println(it) }

    //Default values in case of null
    var length = name?.length ?: 0
    length = name?.length  ?: throw IllegalArgumentException("Can't be null")
}