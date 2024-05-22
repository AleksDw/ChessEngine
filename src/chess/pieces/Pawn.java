package chess.pieces;

import chess.board.Board;

import java.awt.image.BufferedImage;

public class Pawn extends Piece{
    public Pawn(Board board, int col, int row, boolean isWhite) {
        super(board);
        this.col = col;
        this.row = row;
        this.xPos = col * board.tileSize;
        this.yPos = row * board.tileSize;

        this.isWhite = isWhite;
        this.name = "Pawn";

        this.sprite = sheet.getSubimage(5 * sheetScale, isWhite ? 0 : sheetScale, sheetScale, sheetScale).getScaledInstance(board.tileSize, board.tileSize, BufferedImage.SCALE_SMOOTH);
    }

    public boolean isValidMovement(int col, int row) { // TODO: En Passaint, Promotion
        int colorMove = isWhite ? 1 : -1;

        //push pawn on starting square
        if(isWhite && this.row == 6)
            return (this.row == row + colorMove || this.row == row + colorMove  + 1) && this.col == col && board.getPiece(col, row) == null;
        else if(!isWhite && this.row == 1)
            return (this.row  == row + colorMove || this.row == row + colorMove - 1) && this.col == col && board.getPiece(col, row) == null;

        // capture
        if((col == this.col-1 && row == this.row -colorMove && board.getPiece(col, row) != null) ||
                (col == this.col+1 && row == this.row -colorMove && board.getPiece(col, row) != null)){
            return true;
        }

        return this.row == row + colorMove & this.col == col && board.getPiece(col, row) == null;
    }

    public boolean moveCollideWithPiece(int col, int row) {
        return false;
    }
}
