fun main() {
    fun hash(input: String): Int {
        return input.fold(0) {acc, ch -> ((acc + ch.code) * 17) % 256}
    }
    class Library{
        val boxes = MutableList(1000){mutableListOf<Pair<String, Int>>()}
        fun erase(label:String){
            val boxNumber = hash(label)
            boxes[boxNumber].removeAll {it.first== label}
        }
        fun add(label:String, num: Int){
            val boxNumber = hash(label)
            for(i in boxes[boxNumber].indices) if(boxes[boxNumber][i].first == label) {
                boxes[boxNumber][i] = label to num
                return
            }
            boxes[boxNumber].add(label to num)
        }
        fun getAns(): Int{
            var ans = 0
            for(i in boxes.indices){
                for(j in boxes[i].indices){
                    ans += (i+1)*(j+1)*boxes[i][j].second
                }
            }
            return ans
        }
    }

    fun part1(input: List<String>): Int {
        return input[0].split(',').sumOf { hash(it) }
    }

    fun part2(input: List<String>): Int {
        val lib = Library()
        for(code in input[0].split(',')){
            if(code.last() == '-'){
                lib.erase(code.substring(0, code.length - 1))
            }
            else{
                val (label, num) = code.split('=')
                lib.add(label, num.toInt())
            }
        }
        return lib.getAns()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day15_test")
    check(part2(testInput) == 145)

    val input = readInput("Day15")
    part1(input).println()
    part2(input).println()
}