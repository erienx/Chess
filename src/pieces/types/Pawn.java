package pieces.types;

import board.PointColRow;
import pieces.Piece;
import pieces.tools.PieceImagesLoader;
import pieces.tools.PieceName;
import board.Board;

public class Pawn extends Piece {
    private boolean moved;

    public Pawn(Board board, int col, int row, boolean isWhite) {
        super(board);
        setPositionsColRow(col, row);
        this.isWhite = isWhite;
        this.name = PieceName.PAWN;
        this.moved = false;

        PieceImagesLoader imagesLoader = new PieceImagesLoader();
        this.pieceImage = imagesLoader.getSinglePieceImage(board, isWhite, this.name);
    }

    @Override
    public boolean isMoveValid(int newCol, int newRow) {
        if (!isMoveValidGeneral(newCol, newRow)) {
            return false;
        }


        PointColRow point = isMovePawnValid(newCol, newRow);
        System.out.println(point);

        return point != null;
    }

    public PointColRow isMovePawnValid(int newCol, int newRow) {
        if (newCol != col) {
            return null;
        }
        PointColRow point = new PointColRow(0, 0);
        int rowModifier;
        if (isWhite)
            rowModifier = -1;
        else
            rowModifier = 1;

        if (!moved) {
            if (newRow == this.row + rowModifier * 2) {
                point.row = rowModifier * 2;
            }
        }
        if (newRow == row + rowModifier) {
            point.row = rowModifier;
        }
        if (point.row == 0) {
            return null;
        }
        moved = true;

        return point;
    }
}
