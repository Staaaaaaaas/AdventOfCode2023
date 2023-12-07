fun main() {
    val typesOfCards = (('2'..'9') + listOf('T', 'J', 'Q', 'K', 'A'))
        .zip((2..14).map { it.toChar() }).toMap()

    val typesOfCardsPt2 = (('J'..'J') + ('2'..'9') + listOf('T', 'Q', 'K', 'A'))
        .zip((2..14).map { it.toChar() }).toMap()

    fun getRank(cards: String): Int {
        val cntOfCards = cards.map { (it to cards.count { ch -> ch == it })}.toMap()
        if(5 in cntOfCards.values) return 6
        if(4 in cntOfCards.values) return 5
        if(3 in cntOfCards.values && 2 in cntOfCards.values) return 4
        if(3 in cntOfCards.values) return 3
        if(cntOfCards.values.count {it == 2} == 2) return 2
        if(2 in cntOfCards.values) return 1
        return 0
    }

    fun getRankPt2(cards: String): Int {
        val cntOfCards = cards.map { (it to if(it!='J')
            cards.count { ch -> ch == it } else 0)}.toMap()
        val cntJokers = cards.count { it == 'J'}
        if(5-cntJokers in cntOfCards.values) return 6
        if(4-cntJokers in cntOfCards.values) return 5
        if(3 in cntOfCards.values && 2 in cntOfCards.values ||
            cntOfCards.values.count {it == 2} == 2 && cntJokers==1) return 4
        if(3-cntJokers in cntOfCards.values) return 3
        if(cntOfCards.values.count {it == 2} == 2) return 2
        if(2-cntJokers in cntOfCards.values) return 1
        return 0
    }

    fun convert(cards: String, pt2: Boolean=false): String {
        if(pt2) return getRankPt2(cards).toString() + cards.map { typesOfCardsPt2[it]!! }.toString()
        return getRank(cards).toString() + cards.map { typesOfCards[it]!! }.toString()
    }

    fun part1(input: List<String>): Long {
        val bids = input.map { it.split(' ') }
        return bids.sortedBy { convert(it[0]) }.zip(bids.indices)
            .sumOf { it.first[1].toLong() * (it.second+1L) }
    }

    fun part2(input: List<String>): Long {
        val bids = input.map { it.split(' ') }
        return bids.sortedBy { convert(it[0], true) }.zip(bids.indices)
            .sumOf { it.first[1].toLong() * (it.second+1L) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part2(testInput) == 5905L)

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}