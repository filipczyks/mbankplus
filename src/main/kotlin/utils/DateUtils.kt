package utils

class DateUtils {
    companion object {
        fun pad(n: Int): String {
            return if (n < 10) "0$n" else n.toString()
        }
    }
}