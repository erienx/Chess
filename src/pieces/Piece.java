package pieces;


import pieces.tools.PieceName;
import board.Board;

import java.awt.*;

public class Piece {
    protected int col, row, xPosition, yPosition;
    protected boolean isWhite;
    protected PieceName name;

    protected Image pieceImage;
    protected Board board;

    public Piece(Board board){
        this.board = board;
    }

    public void paintPiece(Graphics2D graphics2D){
        graphics2D.drawImage(pieceImage,xPosition,yPosition, null);
    }

    public void setPositionsColRow(int col, int row){
        this.col = col;
        this.row = row;
        this.xPosition = col * board.tileSize;
        this.yPosition = row * board.tileSize;
    }
    public void setPositionsXY(int x, int y){
        this.xPosition = x;
        this.yPosition = y;
        this.col = x/board.tileSize;
        this.row = y/board.tileSize;
    }


    public int getCol(){
        return col;
    }

    public int getRow() {
        return row;
    }

    public boolean getIsWhite() {
        return isWhite;
    }
}
