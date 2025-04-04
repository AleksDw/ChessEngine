package chess.board;

import chess.pieces.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Board extends JPanel {

    public final int TILE_SIZE = 80;
    public final int COLUMNS = 8;
    public final int ROWS = 8;

    ArrayList<Piece> pieceList = new ArrayList<>();
    public Piece selectedPiece;

    public int enPassantTile = -1;

    Input input = new Input(this);

    public CheckScanner checkScanner = new CheckScanner(this);

    private boolean isWhiteToMove = true;
    private boolean isGameOver = false;

    public Board() {
        this.setPreferredSize(new Dimension(COLUMNS * TILE_SIZE, ROWS * TILE_SIZE));
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

    public int getTileNum(int col, int row) {
        return row * ROWS + col;
    }


    void makeMove(Move move) {

        if(move.piece.name.equals("Pawn"))
            movePawn(move);
        if (move.piece.name.equals("King"))
            moveKing(move);
        move.piece.col = move.newColumn;
        move.piece.row = move.newRow;
        move.piece.xPos = move.newColumn * TILE_SIZE;
        move.piece.yPos = move.newRow * TILE_SIZE;

        move.piece.isFirstMove = false;
        capture(move.capture);

        isWhiteToMove = !isWhiteToMove;

        updateGameState();
    }

    private void movePawn(Move move) {

        //en passant
        int colorMove = move.piece.isWhite ? 1 : -1;

        if(getTileNum(move.newColumn, move.newRow) == enPassantTile) {
            move.capture = getPiece(move.newColumn, move.newRow + colorMove);
        }
        if(Math.abs(move.piece.row - move.newRow) == 2) {
            enPassantTile = getTileNum(move.newColumn, move.newRow + colorMove);
        }
        else {
            enPassantTile = -1;
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

    private void moveKing(Move move) {
        if (Math.abs(move.piece.col- move.newColumn) == 2) {
            Piece rook;
            if(move.piece.col < move.newColumn) {
                rook = getPiece(7, move.piece.row);
                rook.col = 5;
            }
            else {
                rook = getPiece(0, move.piece.row);
                rook.col = 3;
            }
            rook.xPos = rook.col * TILE_SIZE;
        }
    }

    private void capture(Piece piece) {
        pieceList.remove(piece);
    }


    boolean isValidMove(Move move) {

        if (isGameOver){
            return false;
        }
        if(move.piece.isWhite != isWhiteToMove) {
            return false;
        }
        if(sameTeam(move.piece, move.capture))
            return false;
        if(!move.piece.isValidMovement(move.newColumn, move.newRow))
            return false;
        if(move.piece.moveCollideWithPiece(move.newColumn, move.newRow))
            return false;
        return !checkScanner.isKingChecked(move);
    }

    boolean sameTeam(Piece p1, Piece p2) {
        if(p1 == null || p2 == null) {
            return false;
        }
        return p1.isWhite == p2.isWhite;
    }

    private void updateGameState() {
        Piece king = findKing(isWhiteToMove);
        if(checkScanner.isGameOver(king)) {
            if (checkScanner.isKingChecked(new Move(this, king, king.col, king.row))) {
                JOptionPane.showMessageDialog(null, (isWhiteToMove ? "Black" : "White") + " wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);

            } else {
                JOptionPane.showMessageDialog(null, "Stalemate", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            }
            isGameOver = true;
        } else if(isNotEnoughMaterial(true) && isNotEnoughMaterial(false)) {
            JOptionPane.showMessageDialog(null, "Stalemate, not enough material", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            isGameOver = true;
        }
    }

    private boolean isNotEnoughMaterial(boolean isWhite) {
        ArrayList<String> names = pieceList.stream()
                .filter(p -> p.isWhite == isWhite)
                .map(p -> p.name)
                .collect(Collectors.toCollection(ArrayList::new));
        if(names.contains("Queen") || names.contains("Pawn") || names.contains("Rook")) {
            return false;
        }
        return names.size() < 3;
    }

    Piece findKing(boolean isWhite) {
        for(Piece piece : pieceList) {
            if (isWhite == piece.isWhite && piece.name.equals("King")) {
                return piece;
            }
        }
        return null;
    }

    private void addPieces() {
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
                g2d.fillRect(column * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE);
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
                        g2d.fillOval((int) ((column+0.5-(dotSize/2)) * TILE_SIZE), (int) ((row+0.5-(dotSize/2)) * TILE_SIZE), (int) (dotSize* TILE_SIZE), (int) (dotSize* TILE_SIZE));

                    }

                }
            }
        }
    }
}