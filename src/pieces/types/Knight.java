package pieces.types;

import board.Board;
import board.PointColRow;
import pieces.Piece;
import pieces.tools.PieceImagesLoader;
import pieces.tools.PieceName;

import java.util.ArrayList;

public class Knight extends Piece {
    public Knight(Board board, int col, int row, boolean isWhite) {
        super(board);
        setPositionsColRow(col, row);
        this.isWhite = isWhite;
        this.name = PieceName.KNIGHT;

        PieceImagesLoader imagesLoader = new PieceImagesLoader();
        this.pieceImage = imagesLoader.getSinglePieceImage(board, isWhite, this.name);
    }

    @Override
    public boolean isMoveValid(int newCol, int newRow) {
        if (!isMoveValidGeneral(newCol, newRow)) {
            return false;
        }
        PointColRow point = getDeltaAndCheckMoveLShape(newCol, newRow);

        if (point == null) {
            return false;
        }
        return true;
    }

    private PointColRow getDeltaAndCheckMoveLShape(int newCol, int newRow) {
        PointColRow point = new PointColRow();
        if ((newCol == col + 1 && newRow == row + 2)
                || (newCol == col - 1 && newRow == row + 2)
                || (newCol == col + 1 && newRow == row - 2)
                || (newCol == col - 1 && newRow == row - 2)

                || (newRow == row + 1 && newCol == col + 2)
                || (newRow == row - 1 && newCol == col + 2)
                || (newRow == row + 1 && newCol == col - 2)
                || (newRow == row - 1 && newCol == col - 2)) {
            point.col = newCol - col;
            point.row = newRow - row;
            return point;
        }
        point = null;
        return point;
    }

    @Override
    public ArrayList<PointColRow> getUncheckedPossibleMoves() {
        ArrayList<PointColRow> moves = new ArrayList<>();

        moves.add(new PointColRow(col + 1, row + 2));
        moves.add(new PointColRow(col - 1, row + 2));
        moves.add(new PointColRow(col + 1, row - 2));
        moves.add(new PointColRow(col - 1, row - 2));

        moves.add(new PointColRow(col + 2, row + 1));
        moves.add(new PointColRow(col - 2, row + 1));
        moves.add(new PointColRow(col + 2, row - 1));
        moves.add(new PointColRow(col - 2, row - 1));

        return moves;
    }

}
