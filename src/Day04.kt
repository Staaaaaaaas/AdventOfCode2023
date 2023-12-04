import kotlin.math.min

fun main() {
    fun solveLine(line: String): Int{
        val lists = line.replace("Card \\d+:\\s+".toRegex(), "").replace("\\s+".toRegex(), " ")
            .replace(" | ", "|")
        val (winning, have) = lists.split("|")
        return winning.split(" ").intersect(have.split(" ").toSet()).size
    }

    fun part1(input: List<String>): Int {
        return input.sumOf { val t = solveLine(it); if(t==0) 0 else 1 shl (solveLine(it) - 1) }
    }

    fun part2(input: List<String>): Int {
        var ans = input.size
        val dp = IntArray(input.size) { 0 }
        for(i in input.indices.reversed()) {
            val intersect = solveLine(input[i])
            for(j in i+1..min(i+intersect, input.size-1)) {
                dp[i] += dp[j] + 1
            }
            ans += dp[i]
        }
        return ans
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
