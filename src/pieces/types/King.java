package pieces.types;

import pieces.Piece;
import pieces.tools.PieceImagesLoader;
import pieces.tools.PieceName;
import board.Board;

public class King extends Piece {
    public King(Board board, int col, int row, boolean isWhite){
        super(board);
        setPositionsColRow(col,row);
        this.isWhite = isWhite;
        this.name = PieceName.KING;

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
