package pieces.tools;
import src.Board;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PieceImagesLoader {
    private BufferedImage piecesImage;
    private int imageSinglePieceDimension;
    final String imagePath = "pieces.png";
    public PieceImagesLoader() {
        try {
            piecesImage = ImageIO.read(ClassLoader.getSystemResourceAsStream(imagePath));
        } catch (IOException e) {
            System.out.println("Error occurred when opening " + imagePath + ": " + e);
        }
        if (piecesImage == null){
            System.out.println(imagePath + " is null");
            System.exit(1);
        }
        imageSinglePieceDimension = piecesImage.getWidth() / 6;
    }
    private int getYPosition(boolean isWhite){
        int yPosition = 0;
        if (!isWhite){
            yPosition = imageSinglePieceDimension;
        }
        return yPosition;
    }
    public Image getKingImage(Board board, boolean isWhite){
        int pieceCol = 0;
        int xPosInImage = pieceCol*imageSinglePieceDimension;

        BufferedImage pieceSubImage = piecesImage.getSubimage(xPosInImage, getYPosition(isWhite), imageSinglePieceDimension, imageSinglePieceDimension);
        return pieceSubImage.getScaledInstance(board.tileSize,board.tileSize, BufferedImage.SCALE_SMOOTH);
    }
    public Image getSinglePieceImage(Board board, boolean isWhite, PieceName pieceCol){
        int xPosInImage = pieceCol.getIndex()*imageSinglePieceDimension;

        BufferedImage pieceSubImage = piecesImage.getSubimage(xPosInImage, getYPosition(isWhite), imageSinglePieceDimension, imageSinglePieceDimension);
        return pieceSubImage.getScaledInstance(board.tileSize,board.tileSize, BufferedImage.SCALE_SMOOTH);
    }


}
