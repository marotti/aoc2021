fun main() {
  fun part1(input: List<String>): Int =
    input
      .map { it.toInt() }
      .zipWithNext { a, b -> if (b > a) 1 else 0 }
      .count { it == 1 }

  fun part2(input: List<String>): Int =
    input
      .map { it.toInt() }
      .windowed(3, 1) { it.reduce { acc, s -> acc + s } }
      .zipWithNext { a, b -> if (b > a) 1 else 0 }
      .count { it == 1 }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day01_test")
  println(part1(testInput))
  check(part1(testInput) == 7)
  println(part2(testInput))
  check(part2(testInput) == 5)

  val input = readInput("Day01")
  println(part1(input))
  println(part2(input))
}
