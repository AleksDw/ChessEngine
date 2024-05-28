package chess.pieces;

import chess.board.Board;
import chess.board.Move;

import java.awt.image.BufferedImage;

public class King extends Piece{
    public King(Board board, int col, int row, boolean isWhite) {
        super(board);
        this.col = col;
        this.row = row;
        this.xPos = col * board.TILE_SIZE;
        this.yPos = row * board.TILE_SIZE;

        this.isWhite = isWhite;
        this.name = "King";

        this.sprite = sheet.getSubimage(0 * sheetScale, isWhite ? 0 : sheetScale, sheetScale, sheetScale).getScaledInstance(board.TILE_SIZE, board.TILE_SIZE, BufferedImage.SCALE_SMOOTH);
    }
    public boolean isValidMovement(int col, int row) {
        if (isMoveOnBoard(col, row)) {
            return false;
        }
        return (Math.abs(this.col - col) == 1 || Math.abs(this.col - col) == 0)
                && (Math.abs(this.row - row) == 1 || Math.abs(this.row - row) ==  0)
                || canCastle(col,row);
    }

    public boolean moveCollideWithPiece(int col, int row) {
        return false;
    }

    private boolean canCastle(int col, int row) {

        if (this.row == row) {
            if(col == 6) {
                Piece rook = board.getPiece(7, row);
                if (rook != null && rook.isFirstMove && isFirstMove) {
                    return board.getPiece(5,row) == null &&
                            board.getPiece(6, row) == null &&
                            !board.checkScanner.isKingChecked(new Move(board, this, 5, row));
                }
            }
            else if (col == 2) {
                Piece rook = board.getPiece(7, row);
                if (rook != null && rook.isFirstMove && isFirstMove) {
                    return board.getPiece(3, row) == null &&
                            board.getPiece(2, row) == null &&
                            board.getPiece(1, row) == null &&
                            !board.checkScanner.isKingChecked(new Move(board, this, 5, row));
                }
            }
        }

        return false;
    }
}
