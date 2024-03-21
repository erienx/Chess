package src;

import pieces.*;
import pieces.types.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Board extends JPanel {
    public final int tileSize = 85;
    public final int cols = 8;
    public final int rows = 8;
    ArrayList<Piece> pieces= new ArrayList<>();
    public Board(){
        this.setPreferredSize(new Dimension(cols * tileSize, rows*tileSize));
        addPieces();
    }
    public void addPieces(){
        pieces.add(new Knight(this, 1, 0, false));
        pieces.add(new Knight(this, 6, 0, false));
        pieces.add(new Knight(this, 1, 7, true));
        pieces.add(new Knight(this, 6, 7, true));

        pieces.add(new King(this,4,0,false));
        pieces.add(new King(this,4,7,true));

        pieces.add(new Queen(this,3,0,false));
        pieces.add(new Queen(this,3,7,true));

        pieces.add(new Bishop(this,2,0,false));
        pieces.add(new Bishop(this,5,0,false));
        pieces.add(new Bishop(this,2,7,true));
        pieces.add(new Bishop(this,5,7,true));

        pieces.add(new Rook(this,0,0,false));
        pieces.add(new Rook(this,7,0,false));
        pieces.add(new Rook(this,0,7,true));
        pieces.add(new Rook(this,7,7,true));

        for (int i = 0; i<8; i++){
            pieces.add(new Pawn(this, i, 1,false));
            pieces.add(new Pawn(this, i, 6,true));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;

        for (int row = 0; row<rows; row++){
            for (int col = 0; col<cols; col++){
                if ((row+col)%2==0){
                    graphics2D.setColor(new Color(154, 105, 51, 255));
                }
                else{
                    graphics2D.setColor(new Color(245, 210, 159, 255));
                }
                graphics2D.fillRect(col*tileSize, row*tileSize,tileSize,tileSize);
            }
        }
        for (Piece piece: pieces){
            piece.paintPiece(graphics2D);
        }
    }

}
