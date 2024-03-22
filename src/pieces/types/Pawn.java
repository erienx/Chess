package pieces.types;

import pieces.Piece;
import pieces.tools.PieceImagesLoader;
import pieces.tools.PieceName;
import board.Board;

public class Pawn extends Piece {
    public Pawn(Board board, int col, int row, boolean isWhite){
        super(board);
        setPositionsColRow(col,row);
        this.isWhite = isWhite;
        this.name = PieceName.PAWN;

        PieceImagesLoader imagesLoader = new PieceImagesLoader();
        this.pieceImage = imagesLoader.getSinglePieceImage(board, isWhite, this.name);
    }

    @Override
    public boolean isMoveValid(int newCol, int newRow){
        if (!isMoveValidGeneral(newCol, newRow)) {
            return false;
        }
        return true;
    }
}
