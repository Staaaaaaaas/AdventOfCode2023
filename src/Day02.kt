import kotlin.math.max

fun main() {
    fun getCubes(line: String): Sequence<Pair<String, Int>> {
        return "(\\d+) (red|green|blue)".toRegex().findAll(line).map{
            it.groupValues[2] to it.groupValues[1].toInt()
        }
    }

    fun solve1(line: String): Int {
        val bag = mutableMapOf("red" to 12, "green" to 13, "blue" to 14)
        if(line.isBlank()) return 0
        for((k, v) in getCubes(line)) if(bag[k]!! < v) return 0
        return "Game (\\d+)".toRegex().find(line)!!.groupValues[1].toInt()
    }

    fun solve2(line: String): Int {
        val bag = mutableMapOf("red" to 0, "green" to 0, "blue" to 0)
        for((k, v) in getCubes(line)) bag[k] = max(bag[k]!!, v)
        return bag.values.fold(1) { total, next -> total * next }
    }

    fun part1(input: List<String>): Int {
        return input.sumOf { solve1(it) }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { solve2(it) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}