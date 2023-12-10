class State(val i: Int, val j: Int, val prevI: Int, val prevJ: Int, var len: Int)


fun main() {
    val pipes = mapOf(
        '|' to arrayOf(-1, 0, 1, 0),
        '-' to arrayOf(0, -1, 0, 1),
        'L' to arrayOf(-1, 0, 0, 1),
        'J' to arrayOf(-1, 0, 0, -1),
        '7' to arrayOf(1, 0, 0, -1),
        'F' to arrayOf(1, 0, 0, 1)
    )

    val dirI = arrayOf(0, -1, 0, 1)
    val dirJ = arrayOf(-1, 0, 1, 0)

    fun bfs(bfsQueue:ArrayDeque<State>, input: List<String>, vis: Array<Array<Int>>): Int{
        while(bfsQueue.isNotEmpty()) {
            val top = bfsQueue.removeFirst()
            if(vis[top.i][top.j]==1){
                return top.len
            }
            vis[top.i][top.j] = 1
            val canGoTo = pipes[input[top.i][top.j]]!!
            for(i in 0..1) {
                val nxtI = top.i+canGoTo[2*i]
                val nxtJ = top.j+canGoTo[2*i+1]
                if(nxtI == top.prevI && nxtJ == top.prevJ) continue
                if(!(nxtI in input.indices && nxtJ in input[0].indices)) continue
                if(canGoTo[2*i] ==1 && input[nxtI][nxtJ] in "JL|")
                    bfsQueue.addLast(State(nxtI, nxtJ, top.i, top.j, top.len+1))
                if(canGoTo[2*i] ==-1 && input[nxtI][nxtJ] in "F7|")
                    bfsQueue.addLast(State(nxtI, nxtJ, top.i, top.j, top.len+1))
                if(canGoTo[2*i+1] ==1 && input[nxtI][nxtJ] in "J7-")
                    bfsQueue.addLast(State(nxtI, nxtJ, top.i, top.j, top.len+1))
                if(canGoTo[2*i+1] ==-1 && input[nxtI][nxtJ] in "LF-")
                    bfsQueue.addLast(State(nxtI, nxtJ, top.i, top.j, top.len+1))
            }

        }
        return 0
    }

    fun setupQueue(input: List<String>, startI: Int, startJ: Int): ArrayDeque<State> {
        val bfsQueue = ArrayDeque<State>()
        for(i in 0..3) if(startI+dirI[i] in input.indices &&
            startJ+dirJ[i] in input[0].indices &&
            input[startI+dirI[i]][startJ+dirJ[i]] !='.'){
            if(dirI[i]==1 && input[startI+dirI[i]][startJ+dirJ[i]] in "JL|"){
                bfsQueue.addLast(State(startI+dirI[i], startJ+dirJ[i], startI, startJ, 1))
            }
            if(dirI[i]==-1 && input[startI+dirI[i]][startJ+dirJ[i]] in "F7|"){
                bfsQueue.addLast(State(startI+dirI[i], startJ+dirJ[i], startI, startJ, 1))
            }
            if(dirJ[i]==1 && input[startI+dirI[i]][startJ+dirJ[i]] in "J7-"){
                bfsQueue.addLast(State(startI+dirI[i], startJ+dirJ[i], startI, startJ, 1))
            }
            if(dirJ[i]==-1 && input[startI+dirI[i]][startJ+dirJ[i]] in "LF-"){
                bfsQueue.addLast(State(startI+dirI[i], startJ+dirJ[i], startI, startJ, 1))
            }
        }
        return bfsQueue
    }

    fun getStart(input: List<String>): Pair<Int, Int> {
        loop@for(i in input.indices){
            for(j in input[0].indices) if(input[i][j] == 'S'){
                return i to j
            }
        }
        return -1 to -1
    }

    fun getVis(input: List<String>, startI:Int, startJ: Int): Array<Array<Int>> {
        val vis = Array(input.size) {Array(input[0].length) {0} }
        if(startI in input.indices)vis[startI][startJ] = 1
        return vis
    }

    fun part1(input: List<String>): Int {
        val (startI, startJ) = getStart(input)
        val vis = getVis(input, startI, startJ)
        return bfs(setupQueue(input, startI, startJ), input, vis)
    }

    fun dfs(input: List<String>, vis: Array<Array<Int>>, i: Int, j:Int, fill: Int) {
        if(i !in vis.indices || j !in vis[0].indices) return
        if(vis[i][j] != 0) return
        vis[i][j] = fill
        for(k in 0..3){
            dfs(input, vis, i+dirI[k], j+dirJ[k], fill)
        }
    }

    fun part2(input: List<String>): Int {
        val (startI, startJ) = getStart(input)
        val vis = getVis(input, startI, startJ)
        val len = bfs(setupQueue(input, startI, startJ), input, vis)
        var prevDir = 0
        var i = startI
        var j = startJ
        repeat(2*len) {
            for(k in 0..3) {
                val newI = i+dirI[k]
                val newJ = j+dirJ[k]
                if (kotlin.math.abs(k-prevDir)!=2 && newI in vis.indices
                    && newJ in vis[0].indices && vis[newI][newJ] == 1){
                    when(k) {
                        0 -> {
                            if(input[i][j]!='S' && input[i][j] !in "J7-" || input[newI][newJ] !in "LF-") continue
                            dfs(input, vis, i + 1, j-1, 2)
                            dfs(input, vis, i + 1, j, 2)
                            dfs(input, vis, i + 1, j+1, 2)
                            dfs(input, vis, i - 1, j-1, 3)
                            dfs(input, vis, i - 1, j, 3)
                            dfs(input, vis, i - 1, j+1, 3)
                        }
                        1 -> {
                            if(input[i][j]!='S' && input[i][j] !in "JL|" || input[newI][newJ] !in "F7|") continue
                            dfs(input, vis, i-1, j-1, 2)
                            dfs(input, vis, i, j-1, 2)
                            dfs(input, vis, i+1, j-1, 2)
                            dfs(input, vis, i-1, j+1, 3)
                            dfs(input, vis, i, j+1, 3)
                            dfs(input, vis, i+1, j+1, 3)
                        }
                        2 -> {
                            if(input[i][j]!='S' && input[i][j] !in "LF-" || input[newI][newJ] !in "J7-") continue
                            dfs(input, vis, i + 1, j-1, 3)
                            dfs(input, vis, i + 1, j, 3)
                            dfs(input, vis, i + 1, j+1, 3)
                            dfs(input, vis, i - 1, j-1, 2)
                            dfs(input, vis, i - 1, j, 2)
                            dfs(input, vis, i - 1, j+1, 2)
                        }
                        3 -> {
                            if(input[i][j]!='S' && input[i][j] !in "F7|" || input[newI][newJ] !in "JL|") continue
                            dfs(input, vis, i-1, j-1, 3)
                            dfs(input, vis, i, j-1, 3)
                            dfs(input, vis, i+1, j-1, 3)
                            dfs(input, vis, i-1, j+1, 2)
                            dfs(input, vis, i, j+1, 2)
                            dfs(input, vis, i+1, j+1, 2)
                        }
                    }
                    prevDir = k
                    i += dirI[k]
                    j += dirJ[k]
                    break
                }
            }
        }
        var clr = 2
        loop@for(ii in vis.indices){
            for(jj in vis[0].indices) if(ii==0 || jj==0|| ii==vis.lastIndex || jj==vis[0].lastIndex){
                if(clr == vis[ii][jj]){
                    clr = 3
                    break@loop
                }
            }
        }
        return vis.sumOf { it.count{vl -> vl==clr} }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part2(testInput) == 4)

    val input = readInput("Day10")
    part1(input).println()
    part2(input).println()
}
