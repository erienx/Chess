package pieces.tools;

import board.Board;
import board.PointColRow;
import pieces.Piece;
import pieces.types.Pawn;

import java.util.ArrayList;

public class MovePiece {
    Board board;

    public MovePiece(Board board) {
        this.board = board;
    }

    public void moveOnDrag(Piece piece, int x, int y) {
        x -= board.tileSize / 2;  // adjust so that center of the piece is at the cursor
        y -= board.tileSize / 2;  // adjust so that center of the piece is at the cursor
        piece.setPositionsXY(x,y);
    }

    public boolean moveOnRelease(Piece piece, int x, int y) {
        int col = x / board.tileSize;
        int row = y / board.tileSize;
        if (piece.isMoveValid(col, row)) {
            piece.setPositionsColRow(col, row);
            if (piece.getName() == PieceName.PAWN) {
                Pawn piecePawn = (Pawn) piece;
                piecePawn.moved = true;
            }
            return true;
        }
        piece.setPositionsColRow(piece.col, piece.row);
        return false;
    }
    public void captureAttempt(Piece attacker, int x, int y){
        int col = x / board.tileSize;
        int row = y / board.tileSize;
        if (attacker.isCaptureValid(col, row)){
            board.pieces.remove(board.findPieceAt(col,row));
            attacker.setPositionsColRow(col,row);
        }
    }

}
