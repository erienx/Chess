package pieces.tools;

import board.Board;
import pieces.Piece;

public class MovePiece {
    Board board;

    public MovePiece(Board board) {
        this.board = board;
    }

    public void moveDrag(Piece piece, int x, int y) {
        x -= board.tileSize / 2;  // adjust so that center of the piece is at the cursor
        y -= board.tileSize / 2;  // adjust so that center of the piece is at the cursor
        piece.setPositionsXY(x, y);
    }

    public void moveRelease(Piece piece, int x, int y) {
        int col = x / board.tileSize;
        int row = y / board.tileSize;
        if (piece.isMoveValid(col, row)) {
            piece.setPositionsColRow(col, row);
        } else {
            piece.setPositionsColRow(piece.colBeforeDrag, piece.rowBeforeDrag);
        }
    }

}
