package board;

import pieces.Piece;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BoardInput extends MouseAdapter {
    Board board;
    MovePiece movePiece;
    Piece selectedPiece;

    public BoardInput(Board board) {
        this.board = board;
        this.movePiece = new MovePiece(board);
        this.selectedPiece = null;
    }

    @Override
    public void mousePressed(MouseEvent event) {
        int col = event.getX() / board.tileSize;
        int row = event.getY() / board.tileSize;
        Piece piecePressed = board.findPieceAt(col, row);
        if (piecePressed != null) {
            selectedPiece = piecePressed;
        }
    }

    public void mouseReleased(MouseEvent event) {
        if (selectedPiece != null) {
            movePiece.moveRelease(selectedPiece, event.getX(), event.getY());
            selectedPiece = null;
        }
    }

    @Override
    public void mouseDragged(MouseEvent event) {
        if (selectedPiece != null) {
            movePiece.moveDrag(selectedPiece, event.getX(), event.getY());
        }
    }

}
