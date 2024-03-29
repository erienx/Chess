package board;

import pieces.Piece;
import pieces.tools.MovePiece;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BoardInput extends MouseAdapter {
    private final Board board;
    private final MovePiece movePiece;


    public BoardInput(Board board) {
        this.board = board;
        this.movePiece = new MovePiece(board);
        board.selectedPiece = null;
    }

    @Override
    public void mousePressed(MouseEvent event) {
        int col = event.getX() / board.tileSize;
        int row = event.getY() / board.tileSize;
        Piece piecePressed = board.findPieceAt(col, row);
        if (piecePressed == null)
            return;
        piecePressed.setPositionsColRow(col, row);
        board.selectedPiece = piecePressed;
        board.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        if (board.selectedPiece != null) {
            if (!movePiece.moveOnRelease(board.selectedPiece, event.getX(), event.getY())) {
                movePiece.captureAttempt(board.selectedPiece, event.getX(), event.getY());
            }
            board.selectedPiece = null;
        }
        board.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent event) {
        if (board.selectedPiece != null) {
            movePiece.moveOnDrag(board.selectedPiece, event.getX(), event.getY());
        }
        board.repaint();
    }

}
