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
        boolean moved = false;
        if (board.selectedPiece != null) {
            moved = movePiece.moveOnRelease(board.selectedPiece, event.getX(), event.getY());
            if (!moved) {
                moved = movePiece.captureAttempt(board.selectedPiece, event.getX(), event.getY());
            }
            board.repaint();
            if (moved) {
                if (board.isCheckmate(!board.selectedPiece.isWhite())) {
                    board.resultNotifier.onCheckmate();
                } else {
                    if (!board.isMovePossible(!board.selectedPiece.isWhite())) {
                        board.resultNotifier.onStalemate();
                    }
                }
            }
            board.selectedPiece = null;
        }
    }

    @Override
    public void mouseDragged(MouseEvent event) {
        if (board.selectedPiece != null) {
            movePiece.moveOnDrag(board.selectedPiece, event.getX(), event.getY());
        }
        board.repaint();
    }

}
