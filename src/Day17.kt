fun main() {
    data class State(val i: Int, val j: Int, val dir: Int, val streak: Int, val curr: Int)

    fun bfs(input: List<String>, mn:Int, mx:Int): Int{
        val vis = Array(input.size) {Array(input[0].length) {Array(4) { Array(mx+1) {Int.MAX_VALUE} } } }
        vis[0][1][2][1] = input[0][1].digitToInt()
        val pq = mutableSetOf(State(0, 1, 2, 1, input[0][1].digitToInt()))
        while(pq.isNotEmpty()){
            val top = pq.minBy{vis[it.i][it.j][it.dir][it.streak]}
            pq.remove(top)
            for(k in 0..1) if(top.streak >= mn){
                val newDir = (top.dir - 1 + 2*k + 4)%4
                val newI = top.i + dirI[newDir]
                val newJ = top.j + dirJ[newDir]
                if(newI in input.indices && newJ in input[0].indices) {
                    val len = input[newI][newJ].digitToInt()
                    val prev = vis[newI][newJ][newDir][1]
                    if (top.curr + len < prev) {
                        pq.remove(State(newI, newJ, newDir, 1, prev))
                        vis[newI][newJ][newDir][1] = top.curr + len
                        pq.add(State(newI, newJ, newDir, 1, top.curr + len))
                    }
                }
            }
            val newDir = top.dir
            val newI = top.i + dirI[newDir]
            val newJ = top.j + dirJ[newDir]
            if(newI in input.indices && newJ in input[0].indices && top.streak < mx) {
                val len = input[newI][newJ].digitToInt()
                val prev = vis[newI][newJ][newDir][top.streak + 1]
                if (top.curr + len < prev) {
                    pq.remove(State(newI, newJ, newDir, top.streak + 1, prev))
                    vis[newI][newJ][newDir][top.streak + 1] = top.curr + len
                    pq.add(State(newI, newJ, newDir, top.streak + 1, top.curr + len))
                }
            }
        }
        var res = Int.MAX_VALUE
        for(i in vis.last().last().indices){
            for(j in vis.last().last()[i].indices) if(j>=mn){
                res = kotlin.math.min(res, vis.last().last()[i][j])
            }
        }
        return res
    }

    fun part1(input: List<String>): Int {
        return bfs(input, 0, 3)
    }

    fun part2(input: List<String>): Int {
        return bfs(input, 4, 10)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day17_test")
    check(part2(testInput) == 94)

    val input = readInput("Day17")
    part1(input).println()
    part2(input).println()
}