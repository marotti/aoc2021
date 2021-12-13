const val BABY_START_CYCLE = 8
const val ADULT_START_CYCLE = 6
const val SPAWN_TIME = 0

data class Ocean(var fishies: MutableMap<Int, Long>) {
  fun oceanCycle() {
    val nextCycle = fishies.filter { it.key != 0 }.mapKeys { it.key - 1 }.toMutableMap()
    nextCycle[ADULT_START_CYCLE] = (nextCycle[ADULT_START_CYCLE] ?: 0) + (fishies[SPAWN_TIME] ?: 0)
    nextCycle[BABY_START_CYCLE] = fishies[SPAWN_TIME] ?: 0
    fishies = nextCycle
  }

  companion object {
    fun loadInitialFishies(input: List<String>) =
      // cycle -> num of fish in a cycle
      Ocean(
        input
          .first()
          .split(",")
          .map { it.toInt() }
          .groupingBy { it }
          .eachCount()
          .mapValues { it.value.toLong() }
          .toMutableMap()
      )
  }
}

fun main() {
  fun part1(input: List<String>): Long {
    val ocean = Ocean.loadInitialFishies(input)
    repeat(80) { ocean.oceanCycle() }
    return ocean.fishies.values.sum()
  }

  fun part2(input: List<String>): Long {
    val ocean = Ocean.loadInitialFishies(input)
    repeat(256) { ocean.oceanCycle() }
    return ocean.fishies.values.sum()
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day06_test")
  println(part1(testInput))
  check(part1(testInput) == 5934.toLong())
  println(part2(testInput))
  check(part2(testInput) == 26984457539)

  val input = readInput("Day06")
  println(part1(input))
  println(part2(input))
}
