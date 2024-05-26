package chess.board;

import chess.pieces.Piece;

public class CheckScanner {

    Board board;

    public CheckScanner(Board board) {
        this.board = board;
    }

    public boolean isKingChecked(Move move) {

        Piece king = board.findKing(move.piece.isWhite);
        assert king != null;

        int kingCol = king.col;
        int kingRow = king.row;

        if(board.selectedPiece != null && board.selectedPiece.name.equals("King")) {
            kingCol = move.newColumn;
            kingRow = move.newRow;
        }

        return  hitByRook(move.newColumn, move.newRow, king, kingCol, kingRow, 0, -1) || // left
                hitByRook(move.newColumn, move.newRow, king, kingCol, kingRow, 0, 1) || // right
                hitByRook(move.newColumn, move.newRow, king, kingCol, kingRow, 1, 0) || // up
                hitByRook(move.newColumn, move.newRow, king, kingCol, kingRow, -1, 0) || // down

                hitByBishop(move.newColumn, move.newRow, king, kingCol, kingRow, -1, -1) || // up-left
                hitByBishop(move.newColumn, move.newRow, king, kingCol, kingRow, 1, -1) || // up-right
                hitByBishop(move.newColumn, move.newRow, king, kingCol, kingRow, -1, 1) || // down-left
                hitByBishop(move.newColumn, move.newRow, king, kingCol, kingRow, 1, 1) || // down-right

                hitByKnight(move.newColumn, move.newRow, king, kingCol, kingRow) ||
                hitByPawn(move.newColumn, move.newRow, king, kingCol, kingRow) ||
                hitByKing(king, kingCol, kingRow);

    }

    private boolean hitByRook(int col, int row, Piece king, int kingCol, int kingRow, int colVal, int rowVal) {
        for(int i = 1; i < 8; i++) {
            if(kingCol + (i*colVal) == col && kingRow + (i*rowVal) == row) {
                break;
            }

            Piece piece = board.getPiece(kingCol + (i*colVal), kingRow + (i*rowVal));
            if (piece != null && piece != board.selectedPiece) {
                if (!board.sameTeam(piece, king) && (piece.name.equals("Rook") || piece.name.equals("Queen")))
                    return true;
                break;
            }
        }
        return false;
    }

    private boolean hitByBishop(int col, int row, Piece king, int kingCol, int kingRow, int colVal, int rowVal) {
        for(int i = 1; i < 8; i++) {
            if(kingCol - (i*colVal) == col && kingRow - (i*rowVal) == row) {
                break;
            }

            Piece piece = board.getPiece(kingCol - (i*colVal), kingRow - (i*rowVal));
            if (piece != null && piece != board.selectedPiece) {
                if (!board.sameTeam(piece, king) && (piece.name.equals("Bishop") || piece.name.equals("Queen")))
                    return true;
                break;
            }
        }
        return false;
    }

    private boolean hitByKnight(int col, int row, Piece king, int kingCol, int kingRow) {
        return checkKnight(board.getPiece(kingCol - 1, kingRow - 2), king, col, row) ||
                checkKnight(board.getPiece(kingCol + 1, kingRow - 2), king, col, row) ||
                checkKnight(board.getPiece(kingCol + 2, kingRow - 1), king, col, row) ||
                checkKnight(board.getPiece(kingCol + 2, kingRow + 1), king, col, row) ||
                checkKnight(board.getPiece(kingCol + 1, kingRow + 2), king, col, row) ||
                checkKnight(board.getPiece(kingCol - 1, kingRow + 2), king, col, row) ||
                checkKnight(board.getPiece(kingCol - 2, kingRow + 1), king, col, row) ||
                checkKnight(board.getPiece(kingCol - 2, kingRow - 1), king, col, row);
    }

    private boolean checkKnight(Piece piece, Piece king, int col, int row) {
        return check(piece, king) && piece.name.equals("Knight") && !(piece.col == col && piece.row == row);
    }

    private boolean hitByKing(Piece king, int kingCol, int kingRow) {
        return checkKing(board.getPiece(kingCol - 1, kingRow - 1), king) ||
                checkKing(board.getPiece(kingCol + 1, kingRow - 1), king) ||
                checkKing(board.getPiece(kingCol, kingRow - 1), king) ||
                checkKing(board.getPiece(kingCol - 1, kingRow + 1), king) ||
                checkKing(board.getPiece(kingCol + 1, kingRow + 1), king) ||
                checkKing(board.getPiece(kingCol, kingRow + 1), king) ||
                checkKing(board.getPiece(kingCol - 1, kingRow), king) ||
                checkKing(board.getPiece(kingCol + 1, kingRow), king);
    }

    private boolean checkKing(Piece piece, Piece king) {
        return check(piece, king) &&piece.name.equals("King");
    }

    private boolean hitByPawn(int col, int row, Piece king, int kingCol, int kingRow) {
        int colorMove = king.isWhite ? -1 : 1;
        return checkPawn(board.getPiece(kingCol + 1, kingRow + colorMove), king, col, row) ||
                checkPawn(board.getPiece(kingCol - 1, kingRow + colorMove), king, col, row);
    }

    private boolean checkPawn(Piece piece, Piece king, int col, int row) {
        return check(piece, king) && piece.name.equals("Pawn") && !(piece.col == col && piece.row == row);
    }

    boolean check(Piece piece,Piece king) {
        return piece != null & !board.sameTeam(piece, king);
    }

    public boolean isGameOver(Piece king) {
        for (Piece piece : board.pieceList) {
            if(board.sameTeam(piece, king)) {
                board.selectedPiece = piece == king ? king : null;
                for (int row = 0; row < board.ROWS; row++) {
                    for (int col = 0; col < board.COLUMNS; col ++) {
                        Move move = new Move(board, piece, col, row);
                        if(board.isValidMove(move)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

}
