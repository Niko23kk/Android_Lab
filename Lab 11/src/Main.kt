fun main(args: Array<String>) {
    val whiteHorse: ChessFigureReal = ChessFigureReal(1, horseName, FigureColor.WHITE, "E2")
    val blackHorse: ChessFigureReal = ChessFigureReal(2, horseName, FigureColor.BLACK, "E4")
    val secondBlackHorse: ChessFigureReal = ChessFigureReal(8, horseName, FigureColor.BLACK, "E8")
    val wrongHorse: ChessFigureReal = ChessFigureReal(7, horseName, FigureColor.BLACK, "E8")
    val whiteKing: ChessFigureReal = ChessFigureReal(3, kingName, FigureColor.WHITE, "E7")
    val blackKing: ChessFigureReal = ChessFigureReal(4, kingName, FigureColor.BLACK, "B3")
    val wrongKing: ChessFigureReal = ChessFigureReal(5, kingName, FigureColor.BLACK, "E3")
    val whiteElephant: ChessFigureReal = ChessFigureReal(6, elephantName, FigureColor.WHITE, "A2")
    val whitePawn: ChessFigureReal = ChessFigureReal(9, pawnName, FigureColor.WHITE, "D7")
    for (figure in ChessFigureReal.availableFigures) {
        println("${figure.name} ${figure.position} ${figure.color}")
    }

    whiteKing.getFigureMovementAbility()
    whiteHorse.getFigureMovementAbility()
    whitePawn.getFigureMovementAbility()

    blackKing.moveToNewPosition("C3")
    whiteElephant.moveToNewPosition("A1")

    println("Story histories:")
    for (storyRecord in ChessFigureReal.movementHistory) {
        println(storyRecord)
    }


    whiteHorse.FigureState().canThisFigureMove(false)
    println("\nWhite horse movement state: ${whiteHorse.state}")
    secondBlackHorse.FigureState().canThisFigureMove(true)
    println("Second black horse movement state: ${secondBlackHorse.state}\n")

    whiteHorse.FigureState().canAttackOrBeingAttacked(false)
    println("White horse attack state: ${whiteHorse.state}")
    whitePawn.FigureState().canAttackOrBeingAttacked(true)
    println("White pawn attack state: ${whitePawn.state}")
    secondBlackHorse.FigureState().canAttackOrBeingAttacked(true)
    println("Second black horse attack state: ${secondBlackHorse.state}\n")

    whiteKing.removeFigureFromBoard()
    for (figure in ChessFigureReal.availableFigures) {
        println("${figure.name} ${figure.position} ${figure.color}")
    }


    println("\nPreset figures: ")
    for (figure in ChessBoard.getInitChessBoard()) {
        println("${figure.name} ${figure.color} ${figure.position}")
    }


    var firstUser = User("Lesha", 1, 40, 0)
    println("\nInfo about first user:\n${firstUser}")
    var secondUser = User("Sanya", 3, 100, 1)
    println("\nInfo about second user:\n${secondUser}")

    val aClass = A()
    aClass.display()

    println("\n% of wins to users: ${firstUser % secondUser}")
    println("Increment user wins: ${firstUser++}")
    println("Decrement user wins: ${secondUser--}")
    println("Is first user higher than second: ${firstUser > secondUser}")

    val resultSum = converter("+")?.invoke(2.3, 5.5)
    val resultSub = converter("-")?.invoke(2.3, 5.5)
    val resultMult = converter("*")?.invoke(2.3, 5.5)
    val resultDiv = converter("/")?.invoke(2.3, 5.5)
    val resultNull = converter(null)?.invoke(2.3, 5.5)
    println("\nSum: $resultSum")
    println("Sub: $resultSub")
    println("Mult: $resultMult")
    println("Div: $resultDiv")
    println("Null: $resultNull")
}

fun converter(str: String?): ((Double, Double) -> Double)? {
    if (str == null) {
        return null
    }

    when {
        str.contains("+") -> {
            return { firstNumber: Double, secondNumber: Double -> firstNumber + secondNumber }
        }
        str.contains("*") -> {
            return { firstNumber: Double, secondNumber: Double -> firstNumber * secondNumber }
        }
        str.contains("-") -> {
            return { firstNumber: Double, secondNumber: Double -> firstNumber - secondNumber }
        }
        str.contains("/") -> {
            return { firstNumber: Double, secondNumber: Double -> firstNumber / secondNumber }
        }
        else -> return null
    }

}