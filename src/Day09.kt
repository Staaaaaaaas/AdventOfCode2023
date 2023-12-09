fun main() {
    fun solveNumbers(numbers: List<Int>, pt2: Boolean = false): Int{
        if(numbers.first() == numbers.last() && numbers.first()==0){
            return 0
        }
        val newNumbers = mutableListOf<Int>()
        for(i in numbers.indices) if(i!=0){
            newNumbers.add(numbers[i]-numbers[i-1])
        }
        if(pt2) return numbers.first() - solveNumbers(newNumbers, true)
        return solveNumbers(newNumbers) + numbers.last()
    }

    fun part1(input: List<String>): Int {
        return input.sumOf { solveNumbers(it.split(' ')
            .map { str -> str.toInt()  }) }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { solveNumbers(it.split(' ')
            .map { str -> str.toInt()  }, true) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part2(testInput) == 2)

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}
