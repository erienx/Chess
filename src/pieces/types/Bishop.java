package pieces.types;

import board.Board;
import board.PointColRow;
import pieces.Piece;
import pieces.tools.PieceImagesLoader;
import pieces.tools.PieceName;

import java.util.ArrayList;

public class Bishop extends Piece {
    public Bishop(Board board, int col, int row, boolean isWhite) {
        super(board);
        setPositionsColRow(col, row);
        this.isWhite = isWhite;
        this.name = PieceName.BISHOP;

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
            return false;
        }
        if (isSteppingOverAnotherPieceDelta(point)) {
            return false;
        }

        return true;
    }

    @Override
    public ArrayList<PointColRow> getUncheckedPossibleMoves() {
        return getUncheckedPossibleMovesDiagonal();
    }

}
