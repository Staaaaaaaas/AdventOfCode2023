class State2(val i: Int, val j: Int, val len: Long)

fun main() {

    val dirI = arrayOf(0, -1, 0, 1)
    val dirJ = arrayOf(-1, 0, 1, 0)

    fun bfs(i: Int, j:Int, tgtI: Int, tgtJ: Int, expanded: List<String>, duplicates: Long): Long{
        val vis = Array(expanded.size) {Array(expanded[0].length) {false} }
        val bfsQueue = ArrayDeque<State2>()
        val weightsVertical = Array(expanded.size) {0L}
        val weightsHorizontal = Array(expanded.size) {0L}
        for(ii in expanded.indices){
            weightsVertical[ii] = if (expanded[ii].count{it=='.'} == expanded[ii].length) duplicates else 1L
        }
        for(jj in expanded.indices){
            weightsHorizontal[jj] = if (expanded.count{it[jj] == '.'} == expanded.size) duplicates else 1L
        }
        bfsQueue.add(State2(i, j, 0L))
        while(bfsQueue.isNotEmpty()){
            val top = bfsQueue.removeFirst()

            if(top.i !in vis.indices || top.j !in vis[0].indices) continue
            if(vis[top.i][top.j])continue
            if(top.i==tgtI && top.j==tgtJ){
                return top.len
            }
            vis[top.i][top.j] = true
            for(k in 0..3){
                val toAdd =
                if(k == 1 || k == 3){
                   weightsVertical[top.i]
                }
                else weightsHorizontal[top.j]
                bfsQueue.add(State2(top.i+dirI[k], top.j+dirJ[k], top.len+toAdd))
            }
        }
        return Long.MAX_VALUE
    }

    fun solve(input: List<String>, weight: Long): Long{
        var ans = 0L
        val holes = mutableListOf<Pair<Int,Int>>()
        for(i in input.indices){
            for(j in input[i].indices) if(input[i][j] == '#'){
                holes.add(i to j)
            }
        }
        val total = holes.size*(holes.size-1)/2
        var done = 0
        println("Total: $total")
        for(i in holes.indices){
            for(j in i+1..holes.lastIndex){
                ans += bfs(holes[i].first, holes[i].second, holes[j].first, holes[j].second, input, weight)
                if((++done)%10000==0)println("Done: ${done} out of $total")
            }
        }
        return ans
    }

    fun part1(input: List<String>): Long {
        return solve(input, 2L)
    }

    fun part2(input: List<String>): Long {
        return solve(input, 1000000L)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    check(solve(testInput, 100L) == 8410L)

    val input = readInput("Day11")
    part1(input).println()
    part2(input).println()
}