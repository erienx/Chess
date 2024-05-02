package pieces.types;

import board.Board;
import board.PointColRow;
import pieces.Piece;
import pieces.tools.PieceImagesLoader;
import pieces.tools.PieceName;

import java.util.ArrayList;

public class King extends Piece {
    public King(Board board, int col, int row, boolean isWhite) {
        super(board);
        setPositionsColRow(col, row);
        this.isWhite = isWhite;
        this.name = PieceName.KING;

        PieceImagesLoader imagesLoader = new PieceImagesLoader();
        this.pieceImage = imagesLoader.getSinglePieceImage(board, isWhite, this.name);
    }

    @Override
    public boolean isMoveValid(int newCol, int newRow) {
        if (!isMoveValidGeneral(newCol, newRow)) {
            return false;
        }
        PointColRow point = getDeltaAndCheckMoveDiagonal(newCol, newRow);
        if (point == null) {
            point = getDeltaAndCheckMoveOrthogonal(newCol, newRow);
        }

        if (point == null) {
            return false;
        }
        if (Math.abs(point.col) > 1 || Math.abs(point.row) > 1) {
            return false;
        }

        return true;
    }

    @Override
    public ArrayList<PointColRow> getUncheckedPossibleMoves() {
        ArrayList<PointColRow> moves = new ArrayList<>();
        moves.add(new PointColRow(col + 1, row));
        moves.add(new PointColRow(col + 1, row + 1));
        moves.add(new PointColRow(col, row + 1));

        moves.add(new PointColRow(col - 1, row));
        moves.add(new PointColRow(col - 1, row - 1));
        moves.add(new PointColRow(col, row - 1));

        moves.add(new PointColRow(col - 1, row + 1));
        moves.add(new PointColRow(col + 1, row - 1));


        return moves;
    }

    public boolean isMoveAValidCastle(int newCol, int newRow) {
        if (this.moved || Math.abs(this.col - newCol) != 2 || this.row != newRow) {
            return false;
        }
        if (newCol == 2) {
            if (!isRookValidForCastle(true) || isKingInCheckDuringCastle(true)) {
                return false;
            }
        } else if (newCol == 6) {
            if (!isRookValidForCastle(false) || isKingInCheckDuringCastle(false)) {
                return false;
            }
        } else {
            return false;
        }


        return true;
    }

    private boolean isRookValidForCastle(boolean isLeftRook) {
        int col;
        if (isLeftRook) {
            col = 0;
        } else {
            col = 7;
        }
        Piece potentialRook = board.findPieceAt(col, this.row);
        if (potentialRook == null || potentialRook.getName() != PieceName.ROOK || potentialRook.moved) {
            return false;
        }
        if (this.isSteppingOverAnotherPiece(new PointColRow(col, this.row))) {
            return false;
        }

        return true;
    }

    private boolean isKingInCheckDuringCastle(boolean isLeftRook) {
        int direction = isLeftRook ? -1 : 1;
        if (board.isKingInCheck(this.isWhite)
                || this.isMoveLeavingKingInCheck(this.col + direction, this.row)
                || this.isMoveLeavingKingInCheck(this.col + (direction * 2), this.row)) {
            return true;
        }
        return false;
    }

    public Piece findCastlingRook(int newCol, int newRow) {
        if (newCol == 2) {
            return board.findPieceAt(0, newRow);
        }
        if (newCol == 6) {
            return board.findPieceAt(7, newRow);
        }
        return null;
    }
}
