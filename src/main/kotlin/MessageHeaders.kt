package example5

data class MessageHeaders<T>(val delegate : Map<String, T>) : Map<String, T> by delegate{

}

fun main(args: Array<String>) {
    val headers = MessageHeaders(mapOf("H1" to "Val1", "H2" to 5))

    println(headers.get("H2"))

}