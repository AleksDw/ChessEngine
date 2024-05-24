package chess.board;

import chess.pieces.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Board extends JPanel {

    public int tileSize = 80;
    final int COLUMNS = 8;
    final int ROWS = 8;

    ArrayList<Piece> pieceList = new ArrayList<>();
    public Piece selectedPiece;

    public int enPassaintTile = -1;

    Input input = new Input(this);

    CheckScanner checkScanner = new CheckScanner(this);

    public Board() {
        this.setPreferredSize(new Dimension(COLUMNS * tileSize, ROWS * tileSize));
        addPieces();

        this.addMouseListener(input);
        this.addMouseMotionListener(input);
    }


    public Piece getPiece(int col, int row) {

        for(Piece piece : pieceList) {
            if(piece.col == col && piece.row == row)
                return piece;
        }
        return null;
    }

    public void makeMove(Move move) {

        if(move.piece.name.equals("Pawn"))
            movePawn(move);

        move.piece.col = move.newColumn;
        move.piece.row = move.newRow;
        move.piece.xPos = move.newColumn * tileSize;
        move.piece.yPos = move.newRow * tileSize;

        capture(move.capture);
    }

    private void movePawn(Move move) {

        //en passaint
        int colorMove = move.piece.isWhite ? 1 : -1;

        if(getTileNum(move.newColumn, move.newRow) == enPassaintTile) {
            move.capture = getPiece(move.newColumn, move.newRow + colorMove);
        }
        if(Math.abs(move.piece.row - move.newRow) == 2) {
            enPassaintTile = getTileNum(move.newColumn, move.newRow + colorMove);
        }
        else {
            enPassaintTile = -1;
        }

        //promotion
        colorMove = move.piece.isWhite ? 0 : 7;
        if(move.newRow == colorMove) {
            promotePawn(move);
        }
    }

    private void  promotePawn(Move move) {
        pieceList.add(new Queen(this, move.newColumn, move.newRow, move.piece.isWhite));
        capture(move.piece);
    }

    public void capture(Piece piece) {
        pieceList.remove(piece);
    }

    public boolean isValidMove(Move move) { //TODO: check if king is pinned
        if(sameTeam(move.piece, move.capture))
            return false;
        if(!move.piece.isValidMovement(move.newColumn, move.newRow))
            return false;
        if(move.piece.moveCollideWithPiece(move.newColumn, move.newRow))
            return false;
        if (checkScanner.isKingChecked(move))
            return false;
        return true;
    }

    public boolean sameTeam(Piece p1, Piece p2) {
        if(p1 == null || p2 == null) {
            return false;
        }
        return p1.isWhite == p2.isWhite;
    }

    public int getTileNum(int col, int row) {
        return row * ROWS + col;
    }

    Piece findKing(boolean isWhite) {
        for(Piece piece : pieceList) {
            if (isWhite == piece.isWhite && piece.name.equals("King")) {
                return piece;
            }
        }
        return null;
    }

    public void addPieces() {
        pieceList.add(new Knight(this, 1, 0, false));
        pieceList.add(new Knight(this, 6, 0, false));
        pieceList.add(new Knight(this, 1, 7, true));
        pieceList.add(new Knight(this, 6, 7, true));

        pieceList.add(new Rook(this, 0, 0, false));
        pieceList.add(new Rook(this, 7, 0, false));
        pieceList.add(new Rook(this, 7, 7, true));
        pieceList.add(new Rook(this, 0, 7, true));

        pieceList.add(new Bishop(this, 2, 0, false));
        pieceList.add(new Bishop(this, 5, 0, false));
        pieceList.add(new Bishop(this, 2, 7, true));
        pieceList.add(new Bishop(this, 5, 7, true));

        pieceList.add(new Queen(this, 3, 0, false));
        pieceList.add(new Queen(this, 3, 7, true));

        pieceList.add(new King(this, 4, 0, false));
        pieceList.add(new King(this, 4, 7, true));

        for (int i = 0; i <= 7; i++) {
            pieceList.add(new Pawn(this, i, 1,false));
            pieceList.add(new Pawn(this, i, 6,true));
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        float dotSize = 0.125F;

        // paint board
        for (int row = 0; row<ROWS; row++){
            for(int column = 0; column<COLUMNS; column++) {
                g2d.setColor((column+row) % 2 == 0 ? Color.lightGray : Color.darkGray);
                g2d.fillRect(column * tileSize, row * tileSize, tileSize, tileSize);
            }
        }

        // paints pieces
        for(Piece piece : pieceList) {
            piece.paint(g2d);
        }

        // highlight all possible moves of a piece
        if (selectedPiece != null) {
            for (int row = 0; row<ROWS; row++){
                for(int column = 0; column<COLUMNS; column++){

                    if (isValidMove(new Move(this, selectedPiece, column, row))) {

                        g2d.setColor(new Color(70, 180, 60, 200));
                        g2d.fillOval((int) ((column+0.5-(dotSize/2)) * tileSize), (int) ((row+0.5-(dotSize/2)) * tileSize), (int) (dotSize*tileSize), (int) (dotSize*tileSize));

                    }

                }
            }
        }
    }
}