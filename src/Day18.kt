fun main() {
    val letterToDir = mapOf(
        "L" to 0,
        "U" to 1,
        "R" to 2,
        "D" to 3
    )

    fun getPoints(input: List<String>, pt2: Boolean = false): Map<Int, List<Int>> {
        val res = mutableMapOf<Int, MutableList<Int>>()
        var i = 0
        var j = 0
        var firstDir = -1
        for(line in input){
            val(letterTemp, distTemp, clr) = line.split(" ")
            val dir: Int
            val dist: Int
            if(!pt2){
                dir = letterToDir[letterTemp]!!
                dist = distTemp.toInt()
            }
            else{
                dir = (clr[7].digitToInt() + 2) % 4
                dist = clr.substring(2, 7).toInt(radix = 16)
            }
            if(firstDir == -1) firstDir = dir
            i += dist * dirI[dir]
            j += dist * dirJ[dir]
            if(j !in res.keys){
                res[j] = mutableListOf()
            }
            res[j]!!.add(i)
        }
        if(letterToDir[input.last().split(" ")[0]]!! == firstDir){
            res[j]!!.removeLast()
        }
        return res
    }

    fun calculate(points: Map<Int, List<Int>>): Long{
        var res = 0L
        var prev = 0
        val currentPoints = sortedSetOf<Int>()
        for(x in points.keys.sorted()){
            var acc: Int? = null
            for(p in currentPoints){
                if(acc == null){
                    acc = -p
                }
                else{
                    res += (acc+p+1).toLong()*(x-prev-1).toLong()
                    acc = null
                }
            }
            val newPoints = sortedSetOf<Int>()
            val pairs = mutableListOf<Pair<Int, Int>>()

            val copyCurr = currentPoints.toSortedSet()
            val toAdd = points[x]!!.toSortedSet()

            while(copyCurr.isNotEmpty()){
                val first = copyCurr.first()
                copyCurr.remove(first)
                val second = copyCurr.first()
                copyCurr.remove(second)
                pairs.add(first to second)
            }

            while(toAdd.isNotEmpty()){
                val first = toAdd.first()
                toAdd.remove(first)
                val second = toAdd.first()
                toAdd.remove(second)
                pairs.add(first to second)
            }
            for(pair in pairs.sortedWith(compareBy({it.first}, {-it.second}))){
                if(newPoints.isEmpty() || newPoints.max() < pair.first) {
                    newPoints.add(pair.first)
                    newPoints.add(pair.second)
                }
                else if(newPoints.last() == pair.first){
                    newPoints.remove(pair.first)
                    newPoints.add(pair.second)
                }
            }
            acc = null
            for(p in newPoints){
                if(acc == null){
                    acc = -p
                }
                else{
                    res += (acc+p+1).toLong()
                    acc = null
                }
            }
            for(p in points[x]!!){
                if(p in currentPoints) currentPoints.remove(p)
                else currentPoints.add(p)
            }
            prev = x
        }
        return res
    }

    fun part1(input: List<String>): Long {
        return calculate(getPoints(input))
    }

    fun part2(input: List<String>): Long {
        return calculate(getPoints(input, true))
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day18_test")
    check(part2(testInput) == 952408144115L)

    val input = readInput("Day18")
    part1(input).println()
    part2(input).println()
}
