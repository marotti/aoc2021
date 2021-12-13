val BABY_START_CYCLE = 8
val ADULT_START_CYCLE = 6

data class LanternFish(var cycle: Int) {
  fun cycleDay(): Any = if (cycle == 0) cycle = ADULT_START_CYCLE else cycle--
  fun willSpawnBaby(): Boolean = cycle == 0
}

data class Ocean(val fishies: MutableList<LanternFish>) {
  fun oceanCycle() {
    var newFishies = 0
    fishies.asSequence().forEach { fish ->
      if (fish.willSpawnBaby()) newFishies++
      fish.cycleDay()
    }
    repeat(newFishies) { fishies.add(LanternFish(BABY_START_CYCLE)) }
  }

  companion object {
    fun loadInitialFishies(input: List<String>) =
      Ocean(
        input
          .first()
          .split(",")
          .map { it.toInt() }
          .map { LanternFish(it) }
          .toMutableList()
      )
  }
}

fun main() {
  fun part1(input: List<String>): Int {
    val ocean = Ocean.loadInitialFishies(input)
    repeat(80) { ocean.oceanCycle() }
    return ocean.fishies.size
  }

  fun part2(input: List<String>): Long {
    val ocean = Ocean.loadInitialFishies(input)
    var cycle = 1
    repeat(256) {
      println(cycle++)
      ocean.oceanCycle()
    }
    return ocean.fishies.size.toLong()
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day06_test")
  println(part1(testInput))
  check(part1(testInput) == 5934)
  println(part2(testInput))
  check(part2(testInput) == 26984457539)

  val input = readInput("Day06")
  println(part1(input))
  println(part2(input))
}
