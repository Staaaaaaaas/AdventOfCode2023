fun main() {

    fun checkNum(input: List<String>, iS: Int, match: MatchResult,
                 cnt:Array<Array<MutableList<Int>>>?=null): Boolean {
        for(j in match.range) {
            for(i in iS-1..iS+1) if (i in input.indices){
                if (j-1 in input[i].indices && input[i][j-1] !in "0123456789."){
                    if(cnt != null) cnt[i][j-1].add(match.value.toInt())
                    return true
                }

                if (input[i][j] !in "0123456789."){
                    if(cnt != null) cnt[i][j].add(match.value.toInt())
                    return true
                }

                if (j+1 in input[i].indices && input[i][j+1] !in "0123456789."){
                    if(cnt != null) cnt[i][j+1].add(match.value.toInt())
                    return true
                }
            }
        }
        return false
    }

    fun part1(input: List<String>): Int {
        var ans = 0
        for(i in input.indices) {
            for(match in "\\d+".toRegex().findAll(input[i])) if(checkNum(input, i, match)){
                ans += match.value.toInt()
            }
        }
        return ans
    }

    fun part2(input: List<String>): Int {
        val cnt = Array(input.size) {Array(input[0].length) {mutableListOf<Int>()} }
        var ans = 0
        for(i in input.indices) {
            for(match in "\\d+".toRegex().findAll(input[i])) {
                checkNum(input, i, match, cnt)
            }
        }
        for(i in input.indices) {
            for(j in input[i].indices) {
                if(cnt[i][j].size == 2) ans += cnt[i][j][0] * cnt[i][j][1]
            }
        }
        return ans
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part2(testInput) == 467835)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}