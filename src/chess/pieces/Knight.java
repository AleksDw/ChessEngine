package chess.pieces;

import chess.board.Board;

import java.awt.image.BufferedImage;

public class Knight extends Piece{

    public Knight(Board board, int col, int row, boolean isWhite) {
        super(board);
        this.col = col;
        this.row = row;
        this.xPos = col * board.TILE_SIZE;
        this.yPos = row * board.TILE_SIZE;

        this.isWhite = isWhite;
        this.name = "Knight";

        this.sprite = sheet.getSubimage(3 * sheetScale, isWhite ? 0 : sheetScale, sheetScale, sheetScale).getScaledInstance(board.TILE_SIZE, board.TILE_SIZE, BufferedImage.SCALE_SMOOTH);
    }

    public boolean isValidMovement(int col, int row){
        if (isMoveOnBoard(col, row)) {
            return false;
        }
        return Math.abs(col-this.col) * Math.abs(row-this.row) == 2;
    }

    public boolean moveCollideWithPiece(int col, int row) {
        return false;
    }

}