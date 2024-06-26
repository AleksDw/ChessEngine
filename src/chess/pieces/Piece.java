package chess.pieces;

import chess.board.Board;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public abstract class Piece {

    public int col, row;
    public int xPos, yPos;

    public boolean isWhite;
    public String name;
    public boolean isFirstMove = true;

    protected BufferedImage sheet;
    {
        try {
            sheet = ImageIO.read(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("res/chessPieces.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected int sheetScale = sheet.getWidth()/6;

    protected Image sprite;

    protected Board board;

    public Piece(Board board) {
        this.board = board;
    }

    public abstract boolean isValidMovement(int col, int row);

    protected boolean isMoveOnBoard(int col, int row) {
        return col >= board.COLUMNS || row >= board.ROWS;
    }


    public abstract boolean moveCollideWithPiece(int col, int row);

    public void paint(Graphics2D g2d) {
        g2d.drawImage(sprite, xPos, yPos, null);
    }
}