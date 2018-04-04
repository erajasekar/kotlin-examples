package example3

import java.text.NumberFormat

fun Double.toDegrees() = Degrees.fromDecimal(this)
infix fun Double.modulus(base: Int) = Mod(base).apply(this)

data class Degrees(val deg: Int, val min: Int, val sec: Int) {

    val decimal by lazy { toDecimal(this) }

    companion object {

        fun fromDecimal(value: Double) : Degrees {
            val deg = value.toInt()
            val decimalMin = (value - deg) * 60
            val min = decimalMin.toInt()
            val sec = ((decimalMin - min) * 60).toInt()
            return Degrees(deg, min, sec)
        }
    }

    operator fun plus(other: Degrees) : Degrees{
        return ((this.decimal + other.decimal) modulus 360 ).toDegrees()
    }

    fun format(delimer : String = ":", ignoreSign: Boolean = true): String {
        val prefix = if (decimal < 0 && !ignoreSign) "-" else ""
        return "%s%03d %s %02d %s %02d".format(prefix ,deg, delimer, min, delimer, sec)
    }

    private fun toDecimal(degrees: Degrees) =  ((deg * 60 + min) * 60 + sec)/3600.0;
}

fun main(args: Array<String>) {
    val d1 =   Degrees(12,12,10)

    val d2 =   Degrees(72,50,10)


    println(d1.decimal)

    val decimal = 12.2;
    println(Degrees.fromDecimal(decimal))

    println(decimal.toDegrees().format("|"))

    println(d1 + d2)

}