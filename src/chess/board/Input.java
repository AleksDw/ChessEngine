package chess.board;

import chess.pieces.Piece;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Input extends MouseAdapter {

    Board board;
    public Input(Board board) {
        this.board = board;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int row = e.getY() / board.TILE_SIZE;
        int col = e.getX() / board.TILE_SIZE;

        Piece pieceXY = board.getPiece(col, row);
        if (pieceXY != null) {
            board.selectedPiece = pieceXY;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if ( board.selectedPiece != null) {
            board.selectedPiece.xPos = e.getX() - board.TILE_SIZE / 2;
            board.selectedPiece.yPos = e.getY() - board.TILE_SIZE / 2;

            board.repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        int row = e.getY() / board.TILE_SIZE;
        int col = e.getX() / board.TILE_SIZE;

        if (board.selectedPiece != null) {
            Move move = new Move(board, board.selectedPiece, col, row);

            if (board.isValidMove(move)) {
                board.makeMove(move);
            }
            else {
                board.selectedPiece.xPos = board.selectedPiece.col * board.TILE_SIZE;
                board.selectedPiece.yPos = board.selectedPiece.row * board.TILE_SIZE;
            }
        }
        board.selectedPiece = null;
        board.repaint();
    }

}
