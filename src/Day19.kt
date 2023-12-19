fun main() {
    val letterToRating = mapOf("x" to 0, "m" to 1, "a" to 2, "s" to 3)
    fun dfs(curr: String, parts: List<Int>, graph: Map<String, String>): Boolean{
        if(curr == "A") return true
        if(curr == "R") return false
        for(args in graph[curr]!!.split(',')){
            val params = args.split(':')
            if(params.size == 1) return dfs(args, parts, graph)
            if('>' in args){
                val (arg1, arg2) = params[0].split('>')
                if(parts[letterToRating[arg1]!!] > arg2.toInt())return dfs(params[1], parts, graph)
            }
            if('<' in args){
                val (arg1, arg2) = params[0].split('<')
                if(parts[letterToRating[arg1]!!] < arg2.toInt())return dfs(params[1], parts, graph)
            }
        }
        return false
    }

    fun getGraph(input:List<String>): Map<String, String>{
        val graph = mutableMapOf<String, String>()
        for(line in input){
            if(line.isBlank()){
                break
            }
            val (name, args) = line.substring(0,line.lastIndex).split('{')
            graph[name] = args
        }
        return graph
    }

    fun part1(input: List<String>): Int {
        var res = 0
        val graph = getGraph(input)
        var flag = false
        for(line in input){
            if(line.isBlank()){
                flag = true
                continue
            }
            if(flag){
                val parts = line.substring(1, line.lastIndex).split(',').map {
                    it.split('=')[1].toInt()
                }
                if(dfs("in", parts, graph)){
                    res += parts.sum()
                }
            }
        }
        return res
    }

    fun dfs2(curr:String, partRanges: List<List<Long>>, graph:Map<String, String>): Long {
//        println(curr)
//        println(partRanges)
        if(curr == "A"){
            return partRanges.fold(1L)
            { acc, pair ->  acc*kotlin.math.max(0L, pair[1]-pair[0]+1)}
        }
        if(curr == "R") return 0L
        var res = 0L
        val newRanges = partRanges.toList().map { it.toMutableList() }
        for(args in graph[curr]!!.split(',')){
            val params = args.split(':')
            if(params.size == 1) res += dfs2(args, newRanges, graph)
            else if('>' in args){
                val (arg1, arg2) = params[0].split('>')
                val rating = letterToRating[arg1]!!
                val old = newRanges[rating][0]
                newRanges[rating][0] = kotlin.math.max(
                    old, arg2.toLong()+1
                )
                res += dfs2(params[1], newRanges, graph)
                newRanges[rating][0] = old
                newRanges[rating][1] = kotlin.math.min(
                    newRanges[rating][1], arg2.toLong()
                )
            }
            else{
                val (arg1, arg2) = params[0].split('<')
                val rating = letterToRating[arg1]!!
                val old = newRanges[rating][1]
                newRanges[rating][1] = kotlin.math.min(
                    old, arg2.toLong()-1
                )
                res += dfs2(params[1], newRanges, graph)
                newRanges[rating][1] = old
                newRanges[rating][0] = kotlin.math.max(
                    newRanges[rating][0], arg2.toLong()
                )
            }
        }
        return res
    }

    fun part2(input: List<String>): Long {
        val graph = getGraph(input)
        return dfs2("in", List(4) {mutableListOf(1L, 4000L)}, graph)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day19_test")
    check(part2(testInput) == 167409079868000L)

    val input = readInput("Day19")
    part1(input).println()
    part2(input).println()
}
