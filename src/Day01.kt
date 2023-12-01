fun main() {
    fun part1(input: List<String>): Int {
        var ans = 0
        for(line in input) {
            val digits = line.replace("[a-z]+".toRegex(), "")
            ans += digits.first().digitToInt()*10 + digits.last().digitToInt()
        }
        return ans
    }

    fun part2(input: List<String>): Int {
        var ans = 0
        val wordToDigit = mapOf(
            "one" to "1", "two" to "2", "three" to "3",
            "four" to "4", "five" to "5", "six" to "6",
            "seven" to "7", "eight" to "8", "nine" to "9",
        )
        val wordToDigitReversed = mapOf(
            "eno" to "1", "owt" to "2", "eerht" to "3",
            "ruof" to "4", "evif" to "5", "xis" to "6",
            "neves" to "7", "thgie" to "8", "enin" to "9",
        )
        for(line in input) {

            val replacedFirst = line.replace(wordToDigit.keys.joinToString("|").toRegex()) {
                wordToDigit[it.value]!!
            }.replace("[a-z]+".toRegex(), "")

            val replacedLast = line.reversed().replace(wordToDigitReversed.keys.joinToString("|").toRegex()) {
                wordToDigitReversed[it.value]!!
            }.reversed().replace("[a-z]+".toRegex(), "")
            ans += replacedFirst.first().digitToInt()*10 + replacedLast.last().digitToInt()
        }
        return ans
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part2(testInput) == 1)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
