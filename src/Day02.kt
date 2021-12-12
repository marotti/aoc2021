enum class MoveSet { FORWARD, DOWN, UP }

data class LocationPartOne(var horizontal: Int = 0, var vertical: Int = 0) {
  fun changeLocation(move: String, distance: Int) =
    when (MoveSet.valueOf(move.uppercase())) {
      MoveSet.FORWARD -> horizontal += distance
      MoveSet.DOWN -> vertical += distance
      MoveSet.UP -> vertical -= distance
    }

  fun getLocation() = horizontal * vertical
}

data class LocationPartTwo(var horizontal: Int = 0, var vertical: Int = 0, var aim: Int = 0) {
  fun changeLocation(move: String, distance: Int) =
    when (MoveSet.valueOf(move.uppercase())) {
      MoveSet.FORWARD -> {
        horizontal += distance
        vertical += aim * distance
      }
      MoveSet.DOWN -> aim += distance
      MoveSet.UP -> aim -= distance
    }

  fun getLocation() = horizontal * vertical
}


fun splitCommand(input: String): Pair<String, Int> =
  Pair(input.substringBefore(' ').trim(), input.substringAfter(' ').trim().toInt())


fun main() {
  fun part1(input: List<String>): Int {
    val location = LocationPartOne()
    input
      .map { splitCommand(it) }
      .forEach { location.changeLocation(it.first, it.second) }
    return location.getLocation()
  }

  fun part2(input: List<String>): Int {
    val location = LocationPartTwo()
    input
      .map { splitCommand(it) }
      .forEach { location.changeLocation(it.first, it.second) }
    return location.getLocation()
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day02_test")
  println(part1(testInput))
  check(part1(testInput) == 150)
  println(part2(testInput))
  check(part2(testInput) == 900)

  val input = readInput("Day02")
  println(part1(input))
  println(part2(input))
}
