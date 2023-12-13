fun main() {
    fun checkIfMirrored(pattern: List<String>, mid: Int): Boolean {
        var l = mid
        var r = mid+1
        while(l in pattern.indices && r in pattern.indices){
            if(pattern[l] != pattern[r]) return false
            l--
            r++
        }
        return true
    }

    fun solvePattern(pattern: List<String>, toIgnore: Int? = null): Int {
        for(i in pattern.indices) if(i!= pattern.lastIndex && checkIfMirrored(pattern, i) && i!=toIgnore){
            return i+1
        }
        return 0
    }

    fun transpose(pattern: List<String>): List<String>{
        val res = mutableListOf<String>()
        for(j in pattern[0].indices){
            val curr = mutableListOf<Char>()
            for(i in pattern.indices){
                curr.add(pattern[i][j])
            }
            res.add(curr.joinToString(""))
        }
        return res
    }

    fun part1(input: List<String>): Int {
        var res = 0
        val curr = mutableListOf<String>()
        for(i in input.indices){
            if(input[i].isNotBlank()){
                curr.add(input[i])
            }
            if(input[i].isBlank() || i==input.lastIndex){
                res += 100 * solvePattern(curr)
                res += solvePattern(transpose(curr))
                curr.clear()
            }
        }
        return res
    }

    fun part2(input: List<String>): Int {
        var res = 0
        val curr = mutableListOf<MutableList<Char>>()
        for(i in input.indices){
            if(input[i].isNotBlank()){
                curr.add(input[i].toMutableList())
            }
            if(input[i].isBlank() || i==input.lastIndex){
                val oldHorizontal = solvePattern(curr.map{it.joinToString("")})
                val oldVertical = solvePattern(transpose(curr.map{it.joinToString("")}))
                bruteforce@for(ii in curr.indices){
                    for(jj in curr[ii].indices){
                        if(curr[ii][jj] == '#') curr[ii][jj] = '.'
                        else if(curr[ii][jj] == '.') curr[ii][jj] = '#'
                        val horizontal = solvePattern(curr.map{it.joinToString("")}, oldHorizontal-1)
                        val vertical = solvePattern(transpose(curr.map{it.joinToString("")}), oldVertical-1)
                        if(horizontal!=0){
                            res += 100*horizontal
                            break@bruteforce
                        }
                        if(vertical!=0){
                            res += vertical
                            break@bruteforce
                        }
                        if(curr[ii][jj] == '#') curr[ii][jj] = '.'
                        else if(curr[ii][jj] == '.') curr[ii][jj] = '#'
                    }
                }
                curr.clear()
            }
        }
        return res
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day13_test")
    check(part2(testInput) == 400)

    val input = readInput("Day13")
    part1(input).println()
    part2(input).println()
}