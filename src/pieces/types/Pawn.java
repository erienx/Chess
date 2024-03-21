package pieces.types;

import pieces.Piece;
import pieces.tools.PieceImagesLoader;
import pieces.tools.PieceName;
import src.Board;

public class Pawn extends Piece {
    public Pawn(Board board, int col, int row, boolean isWhite){
        super(board);
        setPositions(col,row);
        this.isWhite = isWhite;
        this.name = PieceName.PAWN;

        PieceImagesLoader imagesLoader = new PieceImagesLoader();
        this.pieceImage = imagesLoader.getSinglePieceImage(board, isWhite, this.name);
    }
}
