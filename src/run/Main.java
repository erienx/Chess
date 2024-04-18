package run;

import board.Board;
import ui.TimerPanel;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frameOperations(frame);
    }

    private static void frameOperations(JFrame frame) {
        frame.setTitle("Chess");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon icon = new ImageIcon("src/res/logo.png");
        frame.setIconImage(icon.getImage());

        frame.setLayout(new GridBagLayout());
        frame.getContentPane().setBackground(new Color(65, 64, 64, 255));
        Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
        windowSize.height /= 1.2;
        windowSize.width /= 1.5;
        frame.setSize(windowSize);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        addPanels(frame, windowSize);



        frame.setVisible(true);
    }
    private static void addPanels(JFrame frame, Dimension windowSize){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0,0,0);

        Board board = new Board(windowSize);
        frame.add(board,gbc);

        int padVertical = board.rows * board.tileSize + board.tileSize/2;
        int padHorizontal = (board.cols-2) * board.tileSize;

        gbc.insets = new Insets(padVertical, padHorizontal,0,0);
        TimerPanel timerPanelWhite = new TimerPanel(board.tileSize);
        frame.add(timerPanelWhite,gbc);


        gbc.insets = new Insets(0, padHorizontal,padVertical,0);
        TimerPanel timerPanelBlack = new TimerPanel(board.tileSize);
        frame.add(timerPanelBlack,gbc);
    }
}
