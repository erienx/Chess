package pieces.tools;

import board.Board;
import pieces.Piece;
import pieces.types.King;
import pieces.types.Pawn;

public class MovePiece {
    Board board;

    public MovePiece(Board board) {
        this.board = board;
    }

    public void moveOnDrag(Piece piece, int x, int y) {
        x -= board.tileSize / 2;  // adjust so that center of the piece is at the cursor
        y -= board.tileSize / 2;  // adjust so that center of the piece is at the cursor
        piece.setPositionsXY(x, y);
    }

    public boolean moveOnRelease(Piece piece, int x, int y) {
        int col = x / board.tileSize;
        int row = y / board.tileSize;
        if (board.isWhitesTurn == board.selectedPiece.isWhite()) {
            if (piece.isMoveValid(col, row)) {
                if (handleRegularMove(piece, col, row)) {
                    return true;
                }
            } else if (piece.getName() == PieceName.KING) {
                if (handleCastling(piece, col, row)) {
                    return true;
                }
            }
        }
        piece.setPositionsColRow(piece.col, piece.row);
        return false;
    }

    public boolean captureAttempt(Piece attacker, int x, int y) {
        int col = x / board.tileSize;
        int row = y / board.tileSize;
        if (board.isWhitesTurn == board.selectedPiece.isWhite() && attacker.isCaptureValid(col, row)) {
            board.pieces.remove(board.findPieceAt(col, row));
            attacker.setPositionsColRow(col, row);
            switchTurn(attacker.isWhite());
            return true;
        }
        return false;
    }

    public void switchTurn(boolean isWhite) {
        board.switchTurn();
        board.handleTimerOnTurnSwitch(isWhite);
    }

    private boolean handleRegularMove(Piece piece, int col, int row) {
        piece.setPositionsColRow(col, row);
        piece.moved = true;
        board.repaint();
        switchTurn(piece.isWhite());
        return true;
    }

    private boolean handleCastling(Piece piece, int col, int row) {
        King king = (King) piece;
        if (king.isMoveAValidCastle(col, row)) {
            piece.setPositionsColRow(col, row);
            Piece rook = king.findCastlingRook(col, row);
            if (col == 2) {
                rook.setPositionsColRow(3, rook.row);
            }
            if (col == 6) {
                rook.setPositionsColRow(5, rook.row);
            }
            rook.moved = true;

            board.repaint();
            switchTurn(piece.isWhite());
            return true;
        }
        return false;
    }

}
