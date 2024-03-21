package board;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BoardInput extends MouseAdapter {
    Board board;
    public BoardInput(Board board){
        this.board = board;
    }
    @Override
    public void mousePressed(MouseEvent event){
        int x = event.getX();
        int y = event.getY();
        System.out.printf("Pressed mouse at x: %d and y: %d\n",x,y);
    }
    public void mouseReleased(MouseEvent event){
        int x = event.getX();
        int y = event.getY();
        System.out.printf("Released mouse at x: %d and y: %d\n",x,y);
    }
    @Override
    public void mouseDragged(MouseEvent event){
        int x = event.getX();
        int y = event.getY();
    }

}
