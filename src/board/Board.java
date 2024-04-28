package board;

import pieces.*;
import pieces.tools.PieceName;
import pieces.types.*;
import ui.TimerPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Board extends JPanel {
    public int tileSize;
    public static final int cols = 8;
    public static final int rows = 8;
    public final ArrayList<Piece> pieces = new ArrayList<>();
    private final BoardInput boardInput;
    private final TimerPanel timerPanelWhite;
    private final TimerPanel timerPanelBlack;
    private boolean gameStarted = false;
    public Piece selectedPiece = null;
    public boolean isWhitesTurn = true;

    public Board(int tileSize, TimerPanel timerPanelWhite, TimerPanel timerPanelBlack) {
        this.tileSize = tileSize;
        this.timerPanelWhite = timerPanelWhite;
        this.timerPanelBlack = timerPanelBlack;

        this.setPreferredSize(new Dimension(cols * tileSize, rows * tileSize));
        placePiecesAtStartingPositions();

        boardInput = new BoardInput(this);
        this.addMouseListener(boardInput);
        this.addMouseMotionListener(boardInput);
    }

    public void resetGame() {
        placePiecesAtStartingPositions();
        isWhitesTurn = true;
        gameStarted = false;
        selectedPiece = null;
        timerPanelBlack.resetTimer();
        timerPanelWhite.resetTimer();
        this.repaint();
    }

    public void placePiecesAtStartingPositions() {
        pieces.clear();
//        pieces.add(new Knight(this, 1, 0, false));
//        pieces.add(new Knight(this, 6, 0, false));
        pieces.add(new Knight(this, 1, 7, true));
        pieces.add(new Knight(this, 6, 7, true));

        pieces.add(new King(this, 4, 0, false));
        pieces.add(new King(this, 4, 7, true));

//        pieces.add(new Queen(this, 3, 0, false));
        pieces.add(new Queen(this, 3, 7, true));

//        pieces.add(new Bishop(this, 2, 0, false));
//        pieces.add(new Bishop(this, 5, 0, false));
        pieces.add(new Bishop(this, 2, 7, true));
        pieces.add(new Bishop(this, 5, 7, true));

//        pieces.add(new Rook(this, 0, 0, false));
//        pieces.add(new Rook(this, 7, 0, false));
        pieces.add(new Rook(this, 0, 7, true));
        pieces.add(new Rook(this, 7, 7, true));

        for (int i = 0; i < 8; i++) {
//            pieces.add(new Pawn(this, i, 1, false));
            pieces.add(new Pawn(this, i, 6, true));
        }
    }

    public Piece findPieceAt(int col, int row) {
        for (Piece piece : pieces) {
            if (piece.row == row && piece.col == col) {
                return piece;
            }
        }
        return null;
    }

    public boolean isPieceAt(int col, int row) {
        for (Piece piece : pieces) {
            if (piece.row == row && piece.col == col) {
                return true;
            }
        }
        return false;
    }

    public boolean isKingInCheck(boolean isWhite) {
        ArrayList<Piece> piecesCopy = new ArrayList<>(pieces);
        Piece king = findKing(isWhite);
        for (Piece piece : piecesCopy) {
            if (piece.isWhite() != isWhite) {
                ArrayList<PointColRow> possibleMoves = piece.getUncheckedPossibleMoves();

                for (PointColRow move : possibleMoves) {
                    if (move.col == king.col && move.row == king.row) {
                        if (piece.getName() == PieceName.KNIGHT) {
                            return true;
                        }
                        if (!piece.isSteppingOverAnotherPieceDelta(new PointColRow(move.col - piece.col, move.row - piece.row))) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;

    }

    public Piece findKing(boolean isWhite) {
        for (Piece piece : pieces) {
            if (piece.getName() == PieceName.KING && piece.isWhite() == isWhite) {
                return piece;
            }
        }
        return null;
    }

    public void switchTurn() {
        isWhitesTurn = !isWhitesTurn;
    }


    public void handleTimer(boolean isWhite) {
        if (isWhite) {
            timerPanelBlack.startTimer();
            timerPanelWhite.stopTimer();
        } else {
            timerPanelWhite.startTimer();
            timerPanelBlack.stopTimer();
        }
    }


    public boolean isMovePossible(boolean isWhite) {
        for (Piece piece : pieces) {
            if (piece.isWhite() == isWhite) {
                if (!piece.getPossibleMoves().isEmpty() || !piece.getPossibleCaptures().isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isCheckmate(boolean isWhite) {
        return !isMovePossible(isWhite) && isKingInCheck(isWhite);
    }


    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if ((row + col) % 2 == 0) {
                    graphics2D.setColor(new Color(154, 105, 51, 255));
                } else {
                    graphics2D.setColor(new Color(245, 210, 159, 255));
                }
                graphics2D.fillRect(col * tileSize, row * tileSize, tileSize, tileSize);
            }
        }
        if (selectedPiece != null) {
            ArrayList<PointColRow> possibleMoves = selectedPiece.getPossibleMoves();
            ArrayList<PointColRow> possibleCaptures = selectedPiece.getPossibleCaptures();
            possibleMoves.removeAll(possibleCaptures);

            g.setColor(new Color(49, 48, 48, 130));
            for (PointColRow point : possibleMoves) {
                int moveX = point.col * tileSize + tileSize / 2;
                int moveY = point.row * tileSize + tileSize / 2;
                g.fillOval(moveX - (int) (tileSize / 7.5), moveY - (int) (tileSize / 7.5), (int) (tileSize * 0.3), (int) (tileSize * 0.3));
            }

            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(5)); // circle thickness
            for (PointColRow point : possibleCaptures) {
                int pieceX = point.col * tileSize;
                int pieceY = point.row * tileSize;
                g.drawOval(pieceX, pieceY, (int) (tileSize), (int) (tileSize));
            }
            g2d.setStroke(new BasicStroke());

        }

        for (Piece piece : pieces) {
            piece.paintPiece(graphics2D);
        }
    }

}
