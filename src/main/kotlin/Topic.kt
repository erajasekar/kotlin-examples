
package examplejava

//Flexible enums or super power enums with Kotlin sealed classes
sealed class Topic(val keyCode: String, val isLegacy:Boolean) {
    object NEW1: Topic("NEW1", false)
    object NEW2: Topic("NEW2", false)
    class LEGACY(legacyName:String) : Topic(legacyName, true)
}


fun main(args: Array<String>) {


    val topics = listOf(Topic.NEW1, Topic.NEW2, Topic.LEGACY("old"))

    topics.forEach {t ->
        when(t){
            is Topic.NEW1 -> println("Work with NEW1")
            is Topic.NEW2 -> println("Work with NEW2")
            else ->  println("${t.keyCode}  => ${t.isLegacy}" )
        }
    }


}
