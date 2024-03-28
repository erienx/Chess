package pieces.types;

import board.PointColRow;
import pieces.Piece;
import pieces.tools.PieceImagesLoader;
import pieces.tools.PieceName;
import board.Board;

import java.util.ArrayList;
import java.util.Iterator;

public class King extends Piece {
    public King(Board board, int col, int row, boolean isWhite) {
        super(board);
        setPositionsColRow(col, row);
        this.isWhite = isWhite;
        this.name = PieceName.KING;

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
            point = getDeltaAndCheckMoveOrthogonal(newCol, newRow);
        }

        if (point == null) {
            return false;
        }
        if(Math.abs(point.col) > 1 || Math.abs(point.row) > 1){
            return false;
        }

        return true;
    }

    @Override
    protected ArrayList<PointColRow> getUncheckedPossibleMoves(){
        ArrayList<PointColRow> moves = new ArrayList<>();
        moves.add(new PointColRow(col +1, row));
        moves.add(new PointColRow(col +1,row+1));
        moves.add(new PointColRow(col,row+1));

        moves.add(new PointColRow(col -1, row));
        moves.add(new PointColRow(col -1,row-1));
        moves.add(new PointColRow(col,row-1));

        moves.add(new PointColRow(col -1,row+1));
        moves.add(new PointColRow(col +1,row-1));


        return moves;
    }
}
