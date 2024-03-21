package pieces;


import pieces.tools.PieceName;
import src.Board;

import java.awt.*;

public class Piece {
    protected int col, row, xPosition, yPosition;
    protected boolean isWhite;
    protected PieceName name;
    protected int value;

    protected Image pieceImage;
    protected Board board;

    public Piece(Board board){
        this.board = board;
    }

    public void setPositions(int col, int row){
        this.col = col;
        this.row = row;
        this.xPosition = col * board.tileSize;
        this.yPosition = row * board.tileSize;
    }

    public void paintPiece(Graphics2D graphics2D){
        graphics2D.drawImage(pieceImage,xPosition,yPosition, null);
    }
}
