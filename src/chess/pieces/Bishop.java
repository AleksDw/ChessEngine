package chess.pieces;

import chess.board.Board;

import java.awt.image.BufferedImage;

public class Bishop extends Piece{

    public Bishop(Board board, int col, int row, boolean isWhite) {
        super(board);
        this.col = col;
        this.row = row;
        this.xPos = col * board.TILE_SIZE;
        this.yPos = row * board.TILE_SIZE;

        this.isWhite = isWhite;
        this.name = "Bishop";

        this.sprite = sheet.getSubimage(2 * sheetScale, isWhite ? 0 : sheetScale, sheetScale, sheetScale).getScaledInstance(board.TILE_SIZE, board.TILE_SIZE, BufferedImage.SCALE_SMOOTH);
    }

    public boolean isValidMovement(int col, int row) {
        if (isMoveOnBoard(col, row)) {
            return false;
        }
        return Math.abs(this.col - col) == Math.abs(this.row - row);
    }

    public boolean moveCollideWithPiece(int col, int row) {

        //up
        if(this.row > row) {
            //left
            if (this.col > col)
                for(int i = 1; i < Math.abs(this.col - col); i++) {
                    if(board.getPiece(this.col - i, this.row - i) != null)
                        return true;
                }
            else // right
                for(int i = 1; i < Math.abs(this.col - col); i++) {
                    if(board.getPiece(this.col + i, this.row - i) != null)
                        return true;
                }
        }

        // down
        if(this.row < row) {
            //left
            if (this.col > col)
                for(int i = 1; i < Math.abs(this.col - col); i++) {
                    if(board.getPiece(this.col - i, this.row + i) != null)
                        return true;
                }
            else // right
                for(int i = 1; i < Math.abs(this.col - col); i++) {
                    if(board.getPiece(this.col + i, this.row + i) != null)
                        return true;
                }
        }

        return false;
    }

}
