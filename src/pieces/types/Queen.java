package pieces.types;

import pieces.Piece;
import pieces.tools.PieceImagesLoader;
import pieces.tools.PieceName;
import board.Board;

public class Queen extends Piece {
    public Queen(Board board, int col, int row, boolean isWhite){
        super(board);
        setPositions(col,row);
        this.isWhite = isWhite;
        this.name = PieceName.QUEEN;

        PieceImagesLoader imagesLoader = new PieceImagesLoader();
        this.pieceImage = imagesLoader.getSinglePieceImage(board, isWhite, this.name);
    }
}
