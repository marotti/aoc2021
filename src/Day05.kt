data class Vent(val vent: Pair<Pair<Int, Int>, Pair<Int, Int>>) {
  fun getX1(): Int = vent.first.first
  fun getX2(): Int = vent.second.first
  fun getLargerX(): Int = if (getX1() >= getX2()) getX1() else getX2()
  fun getSmallerX(): Int = if (getX1() <= getX2()) getX1() else getX2()
  fun getY1(): Int = vent.first.second
  fun getY2(): Int = vent.second.second
  fun getLargerY(): Int = if (getY1() >= getY2()) getY1() else getY2()
  fun getSmallerY(): Int = if (getY1() <= getY2()) getY1() else getY2()
  fun getDiagonalDistance(): Int = getLargerX() - getSmallerX()

  fun isHorizontal(): Boolean = getX1() == getX2()
  fun isVertical(): Boolean = getY1() == getY2()
  fun isDiagonal(): Boolean =
    (!isHorizontal() && !isVertical() && (getLargerX() - getSmallerX() == getLargerY() - getSmallerY()))
}

data class RadarMap(val radar: MutableList<MutableList<Int>>) {
  fun fillMap(vents: List<Vent>): RadarMap {
    vents.forEach { vent ->
      if (vent.isVertical()) (vent.getSmallerX()..vent.getLargerX()).forEach { x -> radar[x][vent.getY1()] += 1 }
      if (vent.isHorizontal()) (vent.getSmallerY()..vent.getLargerY()).forEach { y -> radar[vent.getX1()][y] += 1 }
      if (vent.isDiagonal()) (0..vent.getDiagonalDistance()).forEach { i ->
        radar[fillDiagonalX(vent, i)][fillDiagonalY(
          vent,
          i
        )] += 1
      }
    }
    return this
  }

  private fun fillDiagonalX(vent: Vent, step: Int) =
    if (vent.getX1() > vent.getX2()) vent.getX1() - step else vent.getX1() + step

  private fun fillDiagonalY(vent: Vent, step: Int) =
    if (vent.getY1() > vent.getY2()) vent.getY1() - step else vent.getY1() + step

  fun dangerAreaCount(): Int = radar.flatten().count { it > 1 }

  companion object {
    fun loadVents(input: List<String>): List<Vent> =
      input.map { line -> Vent(Pair(loadXYPair(line.split("->")[0].trim()), loadXYPair(line.split("->")[1].trim()))) }

    private fun loadXYPair(input: String) =
      Pair(input.split(",")[0].trim().toInt(), input.split(",")[1].trim().toInt())

    fun getLargestXY(vents: List<Vent>): Pair<Int, Int> {
      val maxX = listOf(vents.maxOf { vent -> vent.vent.first.first },
        vents.maxOf { vent -> vent.vent.second.first }).maxOf { it }
      val maxY = listOf(vents.maxOf { vent -> vent.vent.first.second },
        vents.maxOf { vent -> vent.vent.second.second }).maxOf { it }
      return Pair(maxX, maxY)
    }
  }
}


fun main() {
  fun part1(input: List<String>): Int {
    val vents = RadarMap.loadVents(input)
    val largestXY = RadarMap.getLargestXY(vents)
    val radarMap = RadarMap(MutableList(largestXY.first + 1) { MutableList(largestXY.second + 1) { 0 } })
    return radarMap.fillMap(vents.filter { vent -> vent.isVertical() || vent.isHorizontal() }).dangerAreaCount()
  }

  fun part2(input: List<String>): Int {
    val vents = RadarMap.loadVents(input)
    val largestXY = RadarMap.getLargestXY(vents)
    val radarMap = RadarMap(MutableList(largestXY.first + 1) { MutableList(largestXY.second + 1) { 0 } })
    return radarMap.fillMap(vents).dangerAreaCount()
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day05_test")
  println(part1(testInput))
  check(part1(testInput) == 5)
  println(part2(testInput))
  check(part2(testInput) == 12)

  val input = readInput("Day05")
  println(part1(input))
  println(part2(input))
}
