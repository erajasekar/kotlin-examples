package examplejava

class Mod(internal var mod: Int) {

    fun apply(n: Int): Int {
        return Math.floorMod(n, mod)
    }

    fun apply(n: Double): Double {
        return if (n < 0) {
            n % mod + mod
        } else {
            n % mod
        }
    }
}
