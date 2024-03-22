package pieces;


import pieces.tools.PieceName;
import board.Board;

import java.awt.*;

public class Piece {
    protected int col, row, xPosition, yPosition;
    public int colBeforeDrag, rowBeforeDrag;
    protected boolean isWhite;
    protected PieceName name;

    protected Image pieceImage;
    protected Board board;

    public Piece(Board board) {
        this.board = board;
    }
    public boolean isMoveValid(int newCol, int newRow){
        return false;
    }



    protected boolean isMoveValidGeneral(int newCol, int newRow) {
        System.out.printf("trying to validate at newCol: %d, newRow: %d, current col: %d, current row: %d\n", newCol, newRow, this.colBeforeDrag, this.rowBeforeDrag);
        if (this.colBeforeDrag == newCol && this.rowBeforeDrag == newRow) {
            return false;
        }
        if (newCol < 0 || newCol >= board.cols || newRow < 0 || newRow >= board.rows) {
            return false;
        }
        for (Piece piece : board.pieces) {
            if (piece != this && piece.col == newCol && piece.row == newRow) {
                if (piece.isWhite == this.isWhite) {
                    return false;
                }
            }
        }

        return true;
    }
    public void paintPiece(Graphics2D graphics2D) {
        graphics2D.drawImage(pieceImage, xPosition, yPosition, null);
    }

    public void setPositionsColRow(int col, int row) {
        this.col = col;
        this.row = row;
        this.colBeforeDrag = col;
        this.rowBeforeDrag = row;
        this.xPosition = col * board.tileSize;
        this.yPosition = row * board.tileSize;
    }

    public void setPositionsXY(int x, int y) {
        this.xPosition = x;
        this.yPosition = y;
        this.col = x / board.tileSize;
        this.row = y / board.tileSize;
    }


    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public boolean IsWhite() {
        return isWhite;
    }
}
