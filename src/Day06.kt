fun main() {
    fun check(timeHoldingButton: Long, timeTotal: Long, distanceRecord: Long): Boolean {
        return (timeTotal - timeHoldingButton) * timeHoldingButton > distanceRecord
    }

    fun solve(races: Iterable<Pair<Long, Long>>): Long {
        return races.fold(1L) { prod, (t, d) -> prod * (1..t)
            .count() { check(it, t, d) } }
    }

    fun part1(input: List<String>): Long {
        val races = input[0].replace("\\s+".toRegex(), " ").replace("Time: ", "")
            .split(" ").map { it.toLong() }.zip(
                input[1].replace("\\s+".toRegex(), " ").replace("Distance: ", "")
                    .split(" ").map {it.toLong() }
            )
        return solve(races)
    }

    fun part2(input: List<String>): Long {
        val race = input[0].replace("\\s+".toRegex(), "").replace("Time:", "")
            .toLong() to (
                input[1].replace("\\s+".toRegex(), "").replace("Distance:", "")
                    .toLong()
            )

        return solve(listOf(race))
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part2(testInput) == 71503L)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}