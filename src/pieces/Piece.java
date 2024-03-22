package pieces;


import board.PointColRow;
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

    public boolean isMoveValid(int newCol, int newRow) {
        return false;
    }


    protected boolean isMoveValidGeneral(int newCol, int newRow) {
        //System.out.printf("trying to validate at newCol: %d, newRow: %d, current col: %d, current row: %d\n", newCol, newRow, this.colBeforeDrag, this.rowBeforeDrag);
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

    protected PointColRow getDeltaAndCheckMoveOrthogonal(int newCol, int newRow) {
        boolean valid = false;
        PointColRow point = new PointColRow();
        if (rowBeforeDrag == newRow && newCol != colBeforeDrag) {
            point.col = newCol - colBeforeDrag;
            point.row = 0;
            valid = true;
        } else if (colBeforeDrag == newCol && newRow != rowBeforeDrag) {
            point.col = 0;
            point.row = newRow - rowBeforeDrag;
            valid = true;
        }
        if (!valid) {
            point = null;
        }
        return point;
    }

    protected PointColRow getDeltaAndCheckMoveDiagonal(int newCol, int newRow) {
        PointColRow point = new PointColRow();
        int deltaCol = Math.abs(colBeforeDrag - newCol);
        int deltaRow = Math.abs(rowBeforeDrag - newRow);
        if (deltaRow == deltaCol) {
            point.col = newCol - colBeforeDrag;
            point.row = newRow - rowBeforeDrag;
            return point;
        } else {
            point = null;
            return point;
        }
    }

    protected PointColRow getDeltaAndCheckMoveLShape(int newCol, int newRow) {
        PointColRow point = new PointColRow();
        if ((newCol == colBeforeDrag + 1 && newRow == rowBeforeDrag + 2)
                || (newCol == colBeforeDrag - 1 && newRow == rowBeforeDrag + 2)
                || (newCol == colBeforeDrag + 1 && newRow == rowBeforeDrag - 2)
                || (newCol == colBeforeDrag - 1 && newRow == rowBeforeDrag - 2)
                || (newRow == rowBeforeDrag + 1 && newCol == colBeforeDrag + 2)
                || (newRow == rowBeforeDrag - 1 && newCol == colBeforeDrag + 2)
                || (newRow == rowBeforeDrag + 1 && newCol == colBeforeDrag - 2)
                || (newRow == rowBeforeDrag - 1 && newCol == colBeforeDrag - 2)) {
            point.col = newCol - colBeforeDrag;
            point.row = newRow - rowBeforeDrag;
            return point;
        }
        point = null;
        return point;
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
