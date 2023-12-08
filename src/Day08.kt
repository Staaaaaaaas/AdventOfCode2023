class Node (val left:String, val right:String)

fun main() {
    fun getDistance(directions: String, start:String,
                    tree:MutableMap<String, Node>): Int {
        var curr = start
        var ans = 0
        while(curr.last()!='Z') {
            val dir = directions[(ans++)%directions.length]
            curr = if(dir == 'R') tree[curr]!!.right
            else tree[curr]!!.left
        }
        return ans
    }

    fun part1(input: List<String>): Int {
        val tree = mutableMapOf<String, Node>()
        for(i in 2..<input.size) {
            val (name, left, right) =
                input[i].replace(" = (", ",")
                .replace(", ", ",")
                .dropLast(1).split(',')
            tree[name] = Node(left, right)
        }
        return getDistance(input[0], "AAA", tree)
    }

    fun part2(input: List<String>): Long {
        val startPositions = mutableListOf<String>()
        val tree = mutableMapOf<String, Node>()
        for(i in 2..<input.size) {
            val (name, left, right) =
                input[i].replace(" = (", ",")
                    .replace(", ", ",")
                    .dropLast(1).split(',')
            if(name.last() == 'A') startPositions.add(name)
            tree[name] = Node(left, right)
        }
        val distances = mutableListOf<Long>()
        for(pos in startPositions){
            distances.add(getDistance(input[0], pos, tree).toLong())
        }
        // Analyzing the test shows us that the length of a cycle is
        // equal to the length of the initial distance, so we can calculate
        // the answer by taking the LCM of all distances:
        return distances.fold(1L) { acc, curr -> acc*curr/gcd(curr, acc) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part2(testInput) == 6L)

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
