package pieces;


import board.PointColRow;
import pieces.tools.PieceName;
import board.Board;
import pieces.types.Pawn;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class Piece {
    public int colDuringDrag, rowDuringDrag, xPosition, yPosition;
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

    public boolean isCaptureValid(int newCol, int newRow) {
        if (board.isWhitesTurn != this.isWhite) {
            return false;
        }
        PointColRow newPosition = new PointColRow(newCol, newRow);
        ArrayList<PointColRow> possibleCaptures = board.selectedPiece.getPossibleCaptures();
        for (PointColRow possibleCapture : possibleCaptures) {
            if (newPosition.equals(possibleCapture)) {
                return true;
            }
        }
        return false;
    }

    public abstract ArrayList<PointColRow> getUncheckedPossibleMoves();

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


    public ArrayList<PointColRow> getPossibleCaptures() {
        ArrayList<PointColRow> possibleCaptures = new ArrayList<>();
        if (board.isWhitesTurn != this.isWhite) {
            return possibleCaptures;
        }
        ArrayList<PointColRow> possibleMoves;
        if (this.getName() == PieceName.PAWN) {
            possibleMoves = ((Pawn) this).getUncheckedPossibleCaptures();
        } else {
            possibleMoves = this.getUncheckedPossibleMoves();
        }

        for (PointColRow point : possibleMoves) {
            Piece piece = board.findPieceAt(point.col, point.row);
            if (piece != null && piece.isWhite != this.isWhite && !this.isMoveLeavingKingInCheck(point.col, point.row)) {

                if (this.name == PieceName.KNIGHT || this.name == PieceName.PAWN || !this.isSteppingOverAnotherPiece(point)) {
                    possibleCaptures.add(point);
                }

            }
        }
        return possibleCaptures;
    }


    protected boolean isMoveValidGeneral(int newCol, int newRow) {
        if (board.isWhitesTurn != this.isWhite) {
            return false;
        }
        if (this.col == newCol && this.row == newRow) {
            return false;
        }
        if (newCol < 0 || newCol >= Board.cols || newRow < 0 || newRow >= Board.rows) {
            return false;
        }
        for (Piece piece : board.pieces) {
            if (piece != this && piece.colDuringDrag == newCol && piece.rowDuringDrag == newRow) {
                return false;
            }
        }
        boolean result = true;
        if (isMoveLeavingKingInCheck(newCol, newRow)) {
            return false;
        }

        return result;
    }

    public boolean isMoveLeavingKingInCheck(int newCol, int newRow) {
        int oldCol = col;
        int oldRow = row;


        Piece piece = board.findPieceAt(newCol, newRow);
        col = newCol;
        row = newRow;

        boolean pieceValid = (piece != null && piece.name != PieceName.KING);

        if (pieceValid) {
            board.pieces.remove(piece);
        }

        boolean isKingInCheck = board.isKingInCheck(isWhite);
        col = oldCol;
        row = oldRow;

        if (pieceValid) {
            board.pieces.add(piece);
        }

        return isKingInCheck;
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

    public boolean isSteppingOverAnotherPieceDelta(PointColRow pointDelta) {
        int newCol = col;
        int newRow = row;

        for (int i = 0; i < Math.abs(pointDelta.row) || i < Math.abs(pointDelta.col); i++) {

            if (pointDelta.col != 0) {
                newCol += pointDelta.col / Math.abs(pointDelta.col);
            }
            if (pointDelta.row != 0) {
                newRow += pointDelta.row / Math.abs(pointDelta.row);
            }

            if (board.isPieceAt(newCol, newRow) && !(col + pointDelta.col == newCol && row + pointDelta.row == newRow)) {
                return true;
            }
        }
        return false;
    }

    public boolean isSteppingOverAnotherPiece(PointColRow targetPosition) {
        int targetCol = targetPosition.col;
        int targetRow = targetPosition.row;

        int colDelta = targetCol - col;
        int rowDelta = targetRow - row;

        for (int i = 1; i < Math.max(Math.abs(colDelta), Math.abs(rowDelta)); i++) {
            int checkCol = col;
            int checkRow = row;

            if (colDelta > 0) {
                checkCol += i;
            } else if (colDelta < 0) {
                checkCol -= i;
            }

            if (rowDelta > 0) {
                checkRow += i;
            } else if (rowDelta < 0) {
                checkRow -= i;
            }

            if (board.isPieceAt(checkCol, checkRow) && !(checkCol == targetCol && checkRow == targetRow)) {
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

    public PieceName getName() {
        return name;
    }
}
