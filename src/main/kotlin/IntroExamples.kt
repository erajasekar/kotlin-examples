package example

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.stream.Stream

fun nullSafety(){

    var name = "Kotlin"
  //  name = null // Compilation Error : Null can not be a value of a non-null type String

    var nullableName : String? = null

    var nameLength = name.length // Works

   // var nameLength = nullableName.length // Compilation Error : Only safe (?.) or non-null asserted (!!.) calls are allowed on a nullable receiver of type String?

    //Use safe call operator ?. to call methods
    // and Elvis operation ?: to assign default value
    nameLength = nullableName?.length ?: 0
}

fun typeInference(){

    var number = 10
   // number = "10" // Compilator Error: Type Mismatch. Required Int, Found String.

    val name = "Kotlin"
    //name = "Hello" // Compilation Error: val can't be reassigned.
}

fun stringTempletes(){
    var name = "Kotlin"

    println("Hello $name")

    println("Length is ${name.length} !") //Use {} to wrap expressions
}

fun mapSimplification(){

    val numbers = listOf(1,2,3,4,5)
    val colors = setOf("Blue", "Green", "Red")

    val numberWords = mapOf( 1 to "One", 2 to "Two" , 3 to "Three")

    for ( ( number , word) in numberWords){
        println("$number = $word")
    }
}

fun ranges(){

    val numbers = 1..100;//Closed Range
    val numbersUpTo10 = 1 until 10 //Exclude boundaries
    val evenNumbers = 0 until 10 step 2


    val n = 50
    //User in operator to check if something in range.
    if (n in 1..100){
        println("$n is between 1 and 100")
    }
    //You can make ranges work for any of you custom object using operation overloading.
}

fun expressions(){

    fun readNumber(str: String){

        //Try is an expression, you can directly assign value to variable
        val number = try {
            Integer.parseInt(str)
        }catch (e: NumberFormatException){
            null
        }
    }

    //When (Equivalent of switch in Java) is also an expression.
    fun evenOrOdd(i : Int) = when{
        i % 2 == 0 -> "Even"
        else -> "Odd"
    }

    println(evenOrOdd(10))//prints "Even"
}

fun defaultAndNamedParameters(){

    data class Person(val firstName: String, val email: String, val age: Int = 0)

    val person = Person(firstName = "John", email = "john@company.com", age = 0)
    println(person) //Person(firstName=John, email=john@company.com, age=0)


    fun formatDate(date: LocalDate,format : String = "MM/dd/YY") : String{
        return DateTimeFormatter.ofPattern(format).format(date)
    }

    println(formatDate(LocalDate.now())) // Prints 04/06/18
    println(formatDate(LocalDate.now(), "dd/MM/YY")) // Prints 06/04/18

}

fun extensionFunctions(){

    fun Int.isEven() : Boolean = this % 2 == 0;

    println(2.isEven())//Prints true

}

fun dataClasses(){

    data class Person(val name:String, val age:Int)

    val person = Person("John", 20)
    println(person) // Prints Person(name=John, age=20)

    val (name, age) = person

    println("Age of $name is $age") //Age of John is 20

}

fun easyDelegation(){

    class Headers<T>(val delegate : Map<String, T>) : Map<String, T> by delegate

    val headers = Headers(mapOf("H1" to "Val1", "H2" to 5))

    println(headers.get("H2"))//Prints 5.

}

//This is a singleton class.
object Config {

    val configProperties = loadConfig()

    fun loadConfig():Map<String, String>{
        //Ideally initialize from external file
        return mapOf("app.name" to "kotlin-example")
    }

 }

fun anonymousClassFinalVariable(){

    var localVarSum = 0
    Stream.of(1,2,3,4).forEach{n ->

        //Non-final local variable can be modified inside lambda
        localVarSum += n
    }
    println(localVarSum) // Prints 10
}

fun easySingleton(){
    println(Config)
}

fun withExample(){

    val date = with(GregorianCalendar()){
        //You don't have to repeat cal. for every method call.
        set(Calendar.YEAR, 2018)
        set(Calendar.DATE, 10)
        set(Calendar.MONTH, 1)
        toZonedDateTime()
    }

    println(date)
}

fun applyExample(){

    val date = GregorianCalendar().apply{
        //You don't have to repeat cal. for every method call.
        set(Calendar.YEAR, 2018)
        set(Calendar.DATE, 10)
        set(Calendar.MONTH, 1)
        toZonedDateTime()
    }

    println(date)
}

fun letExample(){
    data class Person(val name:String, val email:String)

    val person : Person? = null

    person?.email.let { email ->
        //Send email only if email is not-null
        sendEmail(email)
    }
}

fun operatorOverloading(){
    data class Point(val x: Int, val y: Int) {

        //plus is a special method you should implement to overload + operator
        //You can also overload -, *, / , %, += , -+, *=, /=, !, ++, --, comparison operators, in (contains), rangeTo,iterator
        operator fun plus(other: Point): Point {
            return Point(x + other.x, y + other.y)
        }

        operator fun minus(other: Point): Point {
            return Point(x - other.x, y - other.y)
        }
    }

    val p1 = Point(10, 20);
    val p2 = Point(30,40)

    println(p1 + p2) // Prints Point(x=40, y=60)
    println(p1 - p2)

}

fun lazyExample(){

    data class Rectangle(val length: Int, val width : Int){
        val area by lazy {
            println("Computing area");
            length * width
        }
    }

    val rect = Rectangle(10,20)
    println(rect)//Rectangle(length=10, width=20)

    println(rect.area) //Prints Computing area followed by 200
    println(rect.area) //Prints only 200
}

interface Result {

}
//Classes Success and Failure both implement Result interface
//and have different fields.
class Success(val message: String) : Result
class Failure(val error: Throwable) : Result

fun smartCastExample() {

    val results = listOf(Success("completed"), Failure(RuntimeException("some thing went wrong")))

    results.forEach { result ->
        when {

            result is Success -> println(result.message)  //Automatically converts result to type Success
            result is Failure -> println(result.error)  //Automatically converts result to type Failure
        }
        //Prints completed
        //java.lang.RuntimeException: some thing went wrong
    }
}

fun javaInterOp() {

    val numbers: List<Int> = listOf(100, 20, 30, 4)

    //Can pass kotin's Collection<Int> to Java SDK that expects Java List
    java.util.Collections.sort(numbers)
    println(numbers) //Prints [4, 20, 30, 100]
}


fun sendEmail(it: String?) = {

}


fun main(args: Array<String>) {
    stringTempletes();
    mapSimplification();
    smartCastExample()
    ranges()
    expressions();
    defaultAndNamedParameters();
    extensionFunctions();
    dataClasses();
    easyDelegation();
    anonymousClassFinalVariable();
    withExample()
    operatorOverloading()
    lazyExample()
    javaInterOp()

}

