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

    public boolean isValidMovement(int col, int row) {
        int colorMove = isWhite ? 1 : -1; // if white move upwards, if black move downwards

        //push pawn on starting square
        if((isWhite && this.row == 6) || (!isWhite && this.row == 1))
            return (this.row == row + colorMove || this.row == row + colorMove * 2) && this.col == col && board.getPiece(col, row) == null;

        // capture
        if((row == this.row -colorMove && board.getPiece(col, row) != null) &&
                (col == this.col-1/*left*/ || col == this.col+1/*right*/))
            return true;

        // en passaint
        if ((board.getTileNum(col, row) == board.enPassaintTile && row == this.row - colorMove && board.getPiece(col, row + colorMove) != null) &&
                ( col == this.col -1 || col == this.col +1))
            return true;


        return this.row == row + colorMove & this.col == col && board.getPiece(col, row) == null;
    }

    public boolean moveCollideWithPiece(int col, int row) {
        //up
        if(this.row > row)
            for(int r = this.row-1 ; r> row; r--) {
                if (board.getPiece(this.col, r) != null){
                    return true;
                }
            }

        //down
        if(this.row < row)
            for(int r = this.row+1 ; r< row; r++) {
                if (board.getPiece(this.col, r) != null){
                    return true;
                }
            }

        return false;
    }



}
