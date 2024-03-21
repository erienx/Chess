package board;

import pieces.Piece;

public class MovePiece {
    Board board;

    public MovePiece(Board board) {
        this.board = board;
    }

    public void moveDrag(Piece piece, int x, int y) {
        x -= board.tileSize / 2;  // adjust center of the piece to the mouse
        y -= board.tileSize / 2;  // adjust center of the piece to the mouse
        piece.setPositionsXY(x, y);
        board.repaint();
    }

    public void moveRelease(Piece piece, int x, int y) {
        int col = x / board.tileSize;
        int row = y / board.tileSize;
        piece.setPositionsColRow(col,row);
        board.repaint();
    }
}
