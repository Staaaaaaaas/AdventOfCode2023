fun main() {
    fun moveNorth(platform: List<MutableList<Char>>){
        for(i in platform.indices){
            for(j in platform.indices)if(platform[i][j]=='O'){
                var newI = i
                platform[i][j] = '.'
                while(newI > 0 && platform[newI-1][j] == '.'){
                    newI--
                }
                platform[newI][j] = 'O'
            }
        }
    }

    fun moveSouth(platform: List<MutableList<Char>>){
        for(i in platform.indices.reversed()){
            for(j in platform.indices)if(platform[i][j]=='O'){
                var newI = i
                platform[i][j] = '.'
                while(newI < platform.size-1 && platform[newI+1][j] == '.'){
                    newI++
                }
                platform[newI][j] = 'O'
            }
        }
    }

    fun moveWest(platform: List<MutableList<Char>>){
        for(i in platform.indices){
            for(j in platform.indices)if(platform[i][j]=='O'){
                var newJ = j
                platform[i][j] = '.'
                while(newJ > 0 && platform[i][newJ-1] == '.'){
                    newJ--
                }
                platform[i][newJ] = 'O'
            }
        }
    }

    fun moveEast(platform: List<MutableList<Char>>){
        for(i in platform.indices){
            for(j in platform.indices.reversed())if(platform[i][j]=='O'){
                var newJ = j
                platform[i][j] = '.'
                while(newJ < platform[i].size - 1 && platform[i][newJ+1] == '.'){
                    newJ++
                }
                platform[i][newJ] = 'O'
            }
        }
    }

    fun getLoad(platform: List<MutableList<Char>>): Int{
        var res = 0
        for(i in platform.indices) for(field in platform[i]) if(field == 'O'){
            res += platform.size-i
        }
        return res
    }

    fun part1(input: List<String>): Int {
        val platform = input.map { it.toMutableList() }
        moveNorth(platform)
        return getLoad(platform)
    }

    fun part2(input: List<String>): Int {
        val platform = input.map { it.toMutableList() }
        // Analyzing the output lets us see that after a thousand
        // steps there exists a cycle of loads which length divisible
        // by 10, meaning that we can just run the bruteforce
        // for 1000 cycles, and get the same result
        for(step in 1..1000){
            moveNorth(platform)
            moveWest(platform)
            moveSouth(platform)
            moveEast(platform)

        }
        return getLoad(platform)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day14_test")
    check(part2(testInput) == 64)

    val input = readInput("Day14")
    part1(input).println()
    part2(input).println()
}