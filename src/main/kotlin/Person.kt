package examplejava


data class Person(val name: String, val age: Int)


fun main(args : Array<String>) {
    println(Person("John", 40))
    val (name, age) = Person("Sam", 30);

    println("Name is $name and age is $age")
}