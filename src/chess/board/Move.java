package chess.board;

import chess.pieces.Piece;

public class Move {

    int oldColumn;
    int oldRow;

    public int newColumn;
    public int newRow;

    public Piece piece;
    public Piece capture;

    public Move(Board board, Piece piece, int newColumn, int newRow) {
        this.oldRow = piece.col;
        this.oldColumn = piece.row;
        this.newColumn = newColumn;
        this.newRow = newRow;

        this.piece = piece;
        this.capture = board.getPiece(newColumn, newRow);

    }

}
