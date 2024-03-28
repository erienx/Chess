package pieces;


import board.PointColRow;
import pieces.tools.PieceName;
import board.Board;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class Piece {
    protected int colDuringDrag, rowDuringDrag, xPosition, yPosition;
    public int col, row;
    protected boolean isWhite;
    protected PieceName name;

    protected Image pieceImage;
    protected Board board;

    public Piece(Board board) {
        this.board = board;
    }

    public void paintPiece(Graphics2D graphics2D) {
        graphics2D.drawImage(pieceImage, xPosition, yPosition, null);
    }

    public abstract boolean isMoveValid(int newCol, int newRow);

    protected abstract ArrayList<PointColRow> getUncheckedPossibleMoves();

    public ArrayList<PointColRow> getPossibleMoves() {
        ArrayList<PointColRow> moves = getUncheckedPossibleMoves();
        if (moves == null) {
            return null;
        }
        Iterator<PointColRow> movesIterator = moves.iterator();

        while (movesIterator.hasNext()) {
            PointColRow move = movesIterator.next();
            if (!isMoveValid(move.col, move.row)) {
                movesIterator.remove();
            }
        }

        return moves;
    }
    public ArrayList<PointColRow> getPossibleCaptures(ArrayList<PointColRow> possibleMoves){
        ArrayList<PointColRow> possibleCaptures = new ArrayList<>();
        for (PointColRow point: possibleMoves){
            Piece piece = board.findPieceAt(point.col, point.row);
            if (piece!= null && piece.isWhite != this.isWhite) {
                possibleCaptures.add(point);
            }
        }
        return possibleCaptures;
    }


    protected boolean isMoveValidGeneral(int newCol, int newRow) {
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

    protected boolean isSteppingOverAnotherPiece(PointColRow pointDelta) {
        int newCol = col;
        int newRow = row;

        for (int i = 0; i < Math.abs(pointDelta.row) || i < Math.abs(pointDelta.col); i++) {

            if (pointDelta.col != 0) {
                newCol += pointDelta.col / Math.abs(pointDelta.col);
            }
            if (pointDelta.row != 0) {
                newRow += pointDelta.row / Math.abs(pointDelta.row);
            }

            if (board.isPieceAt(newCol, newRow) && !(col+pointDelta.col==newCol && row+pointDelta.row==newRow)) {
                return true;
            }
        }
        return false;
    }

    protected ArrayList<PointColRow> getUncheckedPossibleMovesOrthogonal() {
        ArrayList<PointColRow> moves = new ArrayList<>();
        for (int col = 0; col < board.cols; col++) {
            moves.add(new PointColRow(col, this.row));
        }
        for (int row = 0; row < board.rows; row++) {
            moves.add(new PointColRow(this.col, row));
        }
        return moves;
    }

    protected ArrayList<PointColRow> getUncheckedPossibleMovesDiagonal() {
        ArrayList<PointColRow> moves = new ArrayList<>();

        //top left to bottom right moves
        for (int col = this.col, row = this.row; col >= 0 && row >= 0; col--, row--) {
            moves.add(new PointColRow(col, row));
        }
        for (int col = this.col, row = this.row; col < board.cols && row < board.rows; col++, row++) {
            moves.add(new PointColRow(col, row));
        }

        //top right to bottom left moves
        for (int col = this.col, row = this.row; col < board.cols && row >= 0; col++, row--) {
            moves.add(new PointColRow(col, row));
        }
        for (int col = this.col, row = this.row; col >= 0 && row < board.rows; col--, row++) {
            moves.add(new PointColRow(col, row));
        }


        return moves;
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

    public boolean isWhite() {
        return isWhite;
    }
}
