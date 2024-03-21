package pieces.types;

import pieces.Piece;
import pieces.tools.PieceImagesLoader;
import pieces.tools.PieceName;
import src.Board;

public class Bishop extends Piece {
    public Bishop(Board board, int col, int row, boolean isWhite){
        super(board);
        setPositions(col,row);
        this.isWhite = isWhite;
        this.name = PieceName.BISHOP;

        PieceImagesLoader imagesLoader = new PieceImagesLoader();
        this.pieceImage = imagesLoader.getSinglePieceImage(board, isWhite, this.name);
    }
}
