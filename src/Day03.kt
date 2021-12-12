import kotlin.math.ceil

fun main() {
  fun getBit(value: Int, position: Int): Int {
    return (value shr position) and 1
  }

  fun getLargerValueForBit(input: List<String>, position: Int): Int =
    if ((input
        .map { it.toInt(2) }
        .map { getBit(it, position) }
        .reduce { acc, i -> i + acc })
      >= ceil(input.size.toDouble() / 2.toDouble())
    ) 1 else 0

  fun getSmallerValueForBit(input: List<String>, position: Int): Int =
    if (getLargerValueForBit(input, position) == 1) 0 else 1

  fun getLeftBitIndex(input: List<String>): Int = input.first().length - 1

  // ------------------------------------------------------------------------
  fun part1(input: List<String>): Int {
    val gammaRate = (getLeftBitIndex(input) downTo 0)
      .joinToString(separator = "") { "${getLargerValueForBit(input, it)}" }
      .toInt(2)

    val epsilonRate = (getLeftBitIndex(input) downTo 0)
      .joinToString(separator = "") { "${getSmallerValueForBit(input, it)}" }
      .toInt(2)
    return gammaRate * epsilonRate
  }

  fun part2(input: List<String>): Int {
    var oxyList = input.toMutableList()
    val oxygenGeneratorRate = (getLeftBitIndex(input) downTo 0)
      .joinToString(separator = "") { position ->
        if (oxyList.size == 1) return@joinToString "${getBit(oxyList.first().toInt(2), position)}"
        val gammaBit = getLargerValueForBit(oxyList, position)
        oxyList = oxyList.filter { getBit(it.toInt(2), position) == gammaBit }.toMutableList()
        "$gammaBit"
      }.toInt(2)

    var co2List = input.toMutableList()
    val co2ScrubberRate = (getLeftBitIndex(input) downTo 0)
      .joinToString(separator = "") { position ->
        if (co2List.size == 1) return@joinToString "${getBit(co2List.first().toInt(2), position)}"
        val epsilonBit = getSmallerValueForBit(co2List, position)
        co2List = co2List.filter { getBit(it.toInt(2), position) == epsilonBit }.toMutableList()
        "$epsilonBit"
      }.toInt(2)

    return oxygenGeneratorRate * co2ScrubberRate
  }

// test if implementation meets criteria from the description, like:
  val testInput = readInput("Day03_test")
  println(part1(testInput))
  check(part1(testInput) == 198)
  println(part2(testInput))
  check(part2(testInput) == 230)

  val input = readInput("Day03")
  println(part1(input))
//  2035764
  println(part2(input))
  // 2817661
}
