object ChessBoard {
    private var figures: MutableList<ChessFigureReal> = mutableListOf(
        ChessFigureReal(1, pawnName, FigureColor.BLACK, "E8"),
        ChessFigureReal(2, kingName, FigureColor.WHITE, "E1"),
        ChessFigureReal(3, pawnName, FigureColor.BLACK, "D8"),
        ChessFigureReal(4, queenName, FigureColor.WHITE, "D1")
    )

    fun getInitChessBoard(): MutableList<ChessFigureReal> {
        return this.figures
    }
}