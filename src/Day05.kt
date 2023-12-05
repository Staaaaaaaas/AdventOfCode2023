fun main() {

    fun mapIt(old: Long, currMap: MutableList<List<Long>>): Long {
        for(j in currMap.indices) if (old in currMap[j][1]..<currMap[j][1] + currMap[j][2]) {
            return old + currMap[j][0] - currMap[j][1]
        }
        return old
    }

    fun parseInput(input: List<String>): MutableList<MutableList<List<Long>>> {
        val res = mutableListOf<MutableList<List<Long>>>()
        for(i in 2..<input.size)if(input[i].isNotBlank()){
            if("map" in input[i]) {
                if(res.isNotEmpty()) res.last().sortBy { it[1] }
                res.add(mutableListOf())
                continue
            }
            res.last().add(input[i].split(" ").map{ it.toLong() })
        }
        return res
    }

    fun part1(input: List<String>): Long {
        var ans = Long.MAX_VALUE
        val seeds = input[0].replace("seeds: ", "")
            .split(" ").map{ it.toLong() }
        val maps = parseInput(input)
        for(seed in seeds){
            var number = seed
            for(i in maps.indices){
                number = mapIt(number, maps[i])
            }
            ans = kotlin.math.min(ans, number)
        }
        return ans
    }

    fun solve2(maps: MutableList<MutableList<List<Long>>> ,
               depth: Int, left: Long, right: Long): Long {
        if(depth == maps.size) return left
        var prev = left
        var ans = Long.MAX_VALUE
        for(i in maps[depth].indices) if(maps[depth][i][1] >= prev){
            val next = kotlin.math.min(right, maps[depth][i][1]-1)
            ans = kotlin.math.min(ans, solve2(maps, depth+1,
                mapIt(prev, maps[depth]), mapIt(next, maps[depth])))
            prev = next+1
            if(prev > right) break
        }
        if(prev <= right) ans = kotlin.math.min(ans, solve2(maps, depth+1,
            mapIt(prev, maps[depth]), mapIt(right, maps[depth])))
        return ans
    }

    fun part2(input: List<String>): Long {
        val maps = parseInput(input)
        val seeds = input[0].replace("seeds: ", "")
            .split(" ").map{ it.toLong() }
        var ans = Long.MAX_VALUE
        for(i in seeds.indices step 2){
            ans = kotlin.math.min(ans, solve2(maps, 0, seeds[i], seeds[i]+seeds[i+1]-1))
        }
        return ans
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part2(testInput) == 46L)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
