fun main() {
    val dirI = arrayOf(0, -1, 0, 1)
    val dirJ = arrayOf(-1, 0, 1, 0)

    fun dfs(i: Int, j: Int, direction: Int, input: List<String>, vis: Array<Array<Array<Int>>>){
        if(i !in input.indices || j !in input[i].indices) return
        if(vis[i][j][direction] == 1) return
        vis[i][j][direction] = 1
        when(input[i][j]) {
            '.' -> dfs(i+dirI[direction], j+dirJ[direction], direction, input, vis)
            '/'->{
                val newDirection = when (direction) {
                    0 -> 3
                    1 -> 2
                    2 -> 1
                    else -> 0
                }
                dfs(i+dirI[newDirection], j+dirJ[newDirection], newDirection, input, vis)
            }
            '\\'->{
                val newDirection = when (direction) {
                    0 -> 1
                    1 -> 0
                    2 -> 3
                    else -> 2
                }
                dfs(i+dirI[newDirection], j+dirJ[newDirection], newDirection, input, vis)
            }
            '-'->{
                if(direction == 0 || direction == 2){
                    dfs(i+dirI[direction], j+dirJ[direction], direction, input, vis)
                }
                else{
                    dfs(i+dirI[0], j+dirJ[0], 0, input, vis)
                    dfs(i+dirI[2], j+dirJ[2], 2, input, vis)
                }
            }
            '|'->{
                if(direction == 1 || direction == 3){
                    dfs(i+dirI[direction], j+dirJ[direction], direction, input, vis)
                }
                else{
                    dfs(i+dirI[1], j+dirJ[1], 1, input, vis)
                    dfs(i+dirI[3], j+dirJ[3], 3, input, vis)
                }
            }
        }
    }

    fun getAns(i: Int, j: Int, direction: Int, input: List<String>): Int{
        val vis = Array(input.size) {Array(input[0].length) { arrayOf(0, 0, 0, 0) } }
        dfs(i, j, direction, input, vis)
        return vis.sumOf { it.count{ el -> el[0]+el[1]+el[2]+el[3] > 0 } }
    }

    fun part1(input: List<String>): Int {
        return getAns(0, 0, 2, input)
    }

    fun part2(input: List<String>): Int {
        var res = 0
        for(i in input.indices) {
            res = kotlin.math.max(res, getAns(i, 0, 2, input))
            res = kotlin.math.max(res, getAns(i, input[i].lastIndex, 0, input))
        }
        for(j in input[0].indices) {
            res = kotlin.math.max(res, getAns(0, j, 3, input))
            res = kotlin.math.max(res, getAns(input.lastIndex, j, 1, input))
        }
        return res
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day16_test")
    check(part2(testInput) == 51)

    val input = readInput("Day16")
    part1(input).println()
    part2(input).println()
}
