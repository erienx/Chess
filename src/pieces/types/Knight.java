package pieces.types;

import pieces.Piece;
import pieces.tools.PieceImagesLoader;
import pieces.tools.PieceName;
import board.Board;

public class Knight extends Piece {
    public Knight(Board board, int col, int row, boolean isWhite){
        super(board);
        setPositionsColRow(col,row);
        this.isWhite = isWhite;
        this.name = PieceName.KNIGHT;

        PieceImagesLoader imagesLoader = new PieceImagesLoader();
        this.pieceImage = imagesLoader.getSinglePieceImage(board, isWhite, this.name);
    }
}
