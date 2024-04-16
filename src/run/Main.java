package run;

import board.Board;

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
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenSize.height = 900;
        screenSize.width = 1100;
        frame.setSize(screenSize);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        Board board = new Board();
        frame.add(board);


        frame.setVisible(true);
    }
}
