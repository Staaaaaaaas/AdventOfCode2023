fun main() {
    fun unfold(line: String): String {
        return Array(5) {line}.joinToString("?")
    }


    fun solveLine(line: String, pt2: Boolean=false): Long {
        val (recordTemp, groupsTemp) = line.split(' ')
        val record = if(pt2)"${unfold(recordTemp)}." else "$recordTemp."
        val groups = if(pt2){
            List(5){groupsTemp.split(',').map{it.toInt()}}.flatten()
        } else {
            groupsTemp.split(',').map{it.toInt()}
        }

        val dp = Array(record.length+10) {Array (record.length+10) {Array(record.length+10) {0L} } }
        dp[0][0][0] = 1L
        for(i in record.indices){
            for(j in 0..groups.size){
                for(k in record.indices){
                    if(record[i] in "#?"){
                        dp[i+1][j][k+1] += dp[i][j][k]
                    }
                    if(record[i] in ".?"){
                        if(k==0) dp[i+1][j][k] += dp[i][j][k]
                        else if(j in groups.indices && k==groups[j]){
                            dp[i+1][j+1][0] += dp[i][j][k]
                        }
                    }
                }
            }
        }
        return dp[record.length][groups.size][0]
    }

    fun part1(input: List<String>): Long {
        return input.sumOf { solveLine(it) }
    }



    fun part2(input: List<String>): Long {

        return input.sumOf { solveLine(it, true) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day12_test")
    check(part2(testInput) == 525152L)

    val input = readInput("Day12")
    part1(input).println()
    part2(input).println()
}