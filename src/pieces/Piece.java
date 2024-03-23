package pieces;


import board.PointColRow;
import pieces.tools.PieceName;
import board.Board;

import java.awt.*;

public class Piece {
    protected int colDuringDrag, rowDuringDrag, xPosition, yPosition;
    public int col, row;
    protected boolean isWhite;
    protected PieceName name;

    protected Image pieceImage;
    protected Board board;

    public Piece(Board board) {
        this.board = board;
    }

    public boolean isMoveValid(int newCol, int newRow) {
        return false;
    }


    protected boolean isMoveValidGeneral(int newCol, int newRow) {
        //System.out.printf("trying to validate at newCol: %d, newRow: %d, current col: %d, current row: %d\n", newCol, newRow, this.colBeforeDrag, this.rowBeforeDrag);
        if (this.col == newCol && this.row == newRow) {
            return false;
        }
        if (newCol < 0 || newCol >= board.cols || newRow < 0 || newRow >= board.rows) {
            return false;
        }
        for (Piece piece : board.pieces) {
            if (piece != this && piece.colDuringDrag == newCol && piece.rowDuringDrag == newRow) {
                if (piece.isWhite == this.isWhite) {
                    return false;
                }
            }
        }

        return true;
    }

    protected PointColRow getDeltaAndCheckMoveOrthogonal(int newCol, int newRow) {
        boolean valid = false;
        PointColRow point = new PointColRow();
        if (row == newRow && newCol != col) {
            point.col = newCol - col;
            point.row = 0;
            valid = true;
        } else if (col == newCol && newRow != row) {
            point.col = 0;
            point.row = newRow - row;
            valid = true;
        }
        if (!valid) {
            point = null;
        }
        return point;
    }

    protected PointColRow getDeltaAndCheckMoveDiagonal(int newCol, int newRow) {
        PointColRow point = new PointColRow();
        int deltaCol = Math.abs(col - newCol);
        int deltaRow = Math.abs(row - newRow);
        if (deltaRow == deltaCol) {
            point.col = newCol - col;
            point.row = newRow - row;
            return point;
        } else {
            point = null;
            return point;
        }
    }

    protected boolean isSteppingOverAnotherPiece(PointColRow point){
        int newCol = col;
        int newRow = row;

        for (int i = 0; i<Math.abs(point.row) || i<Math.abs(point.col); i++) {

            if (point.col!=0){
                newCol += point.col/Math.abs(point.col);
            }
            if (point.row!=0){
                newRow += point.row/Math.abs(point.row);
            }

            if (board.isPieceAt(newCol, newRow)){
                return true;
            }
        }
        return false;
    }


    public void paintPiece(Graphics2D graphics2D) {
        graphics2D.drawImage(pieceImage, xPosition, yPosition, null);
    }

    public void setPositionsColRow(int col, int row) {
        this.colDuringDrag = col;
        this.rowDuringDrag = row;
        this.col = col;
        this.row = row;
        this.xPosition = col * board.tileSize;
        this.yPosition = row * board.tileSize;
    }

    public void setPositionsXY(int x, int y) {
        this.xPosition = x;
        this.yPosition = y;
        this.colDuringDrag = x / board.tileSize;
        this.rowDuringDrag = y / board.tileSize;
    }

}
