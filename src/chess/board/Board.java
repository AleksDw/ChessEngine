package chess.board;

import chess.pieces.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Board extends JPanel {

    public int tileSize = 80;
    int COLUMNS = 8;
    int ROWS = 8;

    ArrayList<Piece> pieceList = new ArrayList<>();

    public Board() {
        this.setPreferredSize(new Dimension(COLUMNS * tileSize, ROWS * tileSize));
        addPieces();
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

        for (int row = 0; row<ROWS; row++){
            for(int column = 0; column<COLUMNS; column++) {
                g2d.setColor((column+row) % 2 == 0 ? Color.lightGray : Color.darkGray);
                g2d.fillRect(column * tileSize, row * tileSize, tileSize, tileSize);
            }
        }


        for(Piece piece : pieceList) {
            piece.paint(g2d);
        }
    }
}