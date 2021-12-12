data class BingoBoard(val board: List<List<Int>>, val calledNumbers: MutableList<Int> = mutableListOf()) {
  fun callNumber(number: Int): BingoBoard {
    calledNumbers.add(number)
    return this
  }

  fun hasWon(): Boolean = if (calledNumbers.size < 5) false else checkRowWin() || checkColWin()
  fun getScore(): Int = getUnmarkedNumberScore() * calledNumbers.last()
  private fun checkColWin(): Boolean = (0..4).any { col -> board.all { row -> row[col] in calledNumbers } }
  private fun checkRowWin(): Boolean = board.any { row -> row.all { it in calledNumbers } }
  private fun getUnmarkedNumberScore(): Int =
    board.flatMap { it.toList() }.filter { it !in calledNumbers }.reduce { acc, i -> acc + i }
}

class BingoGame(val boards: List<BingoBoard>, val calledNumbers: List<Int>) {
  fun findWinner(): BingoBoard {
    for (callNumber in calledNumbers) {
      for (board in boards) {
        if (board.callNumber(callNumber).hasWon()) return board
      }
    }
    throw Exception("No winner found")
  }

  companion object {
    fun parseBoards(input: List<String>): List<BingoBoard> =
      input.filter { it.isNotBlank() }.windowed(5, 5) { board ->
        BingoBoard(board.map { row -> row.split(" ").filter { it.isNotEmpty() }.map { it.toInt() } })
      }

    fun getCalledNumbers(input: String): List<Int> = input.split(",").map { it.toInt() }
  }
}

fun main() {
  fun part1(input: List<String>): Int {
    val calledNumbers = BingoGame.getCalledNumbers(input.first())
    val bingoBoards = BingoGame.parseBoards(input.drop(1))
    val game = BingoGame(bingoBoards, calledNumbers)
    val winningBoard = game.findWinner()
    return winningBoard.getScore()
  }

//  fun part2(input: List<String>): Int = input.size

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day04_test")
  println(part1(testInput))
  check(part1(testInput) == 4512)
//  println(part2(testInput))
//  check(part2(testInput) == 5)

  val input = readInput("Day04")
  println(part1(input))
//  println(part2(input))
}
