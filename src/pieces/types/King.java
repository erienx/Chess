package pieces.types;

import pieces.Piece;
import pieces.tools.PieceImagesLoader;
import pieces.tools.PieceName;
import board.Board;

public class King extends Piece {
    public King(Board board, int col, int row, boolean isWhite){
        super(board);
        setPositions(col,row);
        this.isWhite = isWhite;
        this.name = PieceName.KING;

        PieceImagesLoader imagesLoader = new PieceImagesLoader();
        this.pieceImage = imagesLoader.getSinglePieceImage(board, isWhite, this.name);
    }
}
