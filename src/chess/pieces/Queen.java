package chess.pieces;

import chess.board.Board;

import java.awt.image.BufferedImage;

public class Queen extends Piece{
    public Queen(Board board, int col, int row, boolean isWhite) {
        super(board);
        this.col = col;
        this.row = row;
        this.xPos = col * board.TILE_SIZE;
        this.yPos = row * board.TILE_SIZE;

        this.isWhite = isWhite;
        this.name = "Queen";

        this.sprite = sheet.getSubimage(1 * sheetScale, isWhite ? 0 : sheetScale, sheetScale, sheetScale).getScaledInstance(board.TILE_SIZE, board.TILE_SIZE, BufferedImage.SCALE_SMOOTH);
    }

    public boolean isValidMovement(int col, int row) {
        if (isMoveOnBoard(col, row)) {
            return false;
        }
        return this.col == col || this.row == row || Math.abs(this.col - col) == Math.abs(this.row - row);
    }

    public boolean moveCollideWithPiece(int col, int row) {
        if(this.col == col || this.row == row) {
            //left
            if (this.col > col)
                for (int c = this.col - 1; c > col; c--) {
                    if (board.getPiece(c, this.row) != null) {
                        return true;
                    }
                }

            // right
            if (this.col < col)
                for (int c = this.col + 1; c < col; c++) {
                    if (board.getPiece(c, this.row) != null) {
                        return true;
                    }
                }

            // up
            if (this.row > row)
                for (int r = this.row - 1; r > row; r--) {
                    if (board.getPiece(this.col, r) != null) {
                        return true;
                    }
                }

            // down
            if (this.row < row)
                for (int r = this.row + 1; r < row; r++) {
                    if (board.getPiece(this.col, r) != null) {
                        return true;
                    }
                }
        }
        else {
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
            //down
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
        }

        return false;
    }
}
