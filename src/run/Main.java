package run;

import board.Board;
import ui.GameResultNotifier;
import ui.TimerPanel;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();

        frameInitialOperations(frame);


    }

    private static void frameInitialOperations(JFrame frame) {
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

    private static void addPanels(JFrame frame, Dimension windowSize) {
        GameResultNotifier resultNotifier = new GameResultNotifier(frame);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;

        int tileScale = 11;
        int tileSize = Math.min(windowSize.height, windowSize.width) / tileScale;

        int padVertical = Board.rows * tileSize + tileSize / 2;
        int padHorizontal = (Board.cols - 2) * tileSize;

        gbc.insets = new Insets(padVertical, padHorizontal, 0, 0);
        TimerPanel timerPanelWhite = new TimerPanel(tileSize, 0, 3, resultNotifier);
        frame.add(timerPanelWhite, gbc);


        gbc.insets = new Insets(0, padHorizontal, padVertical, 0);
        TimerPanel timerPanelBlack = new TimerPanel(tileSize, 0, 3, resultNotifier);
        frame.add(timerPanelBlack, gbc);

        gbc.insets = new Insets(0, 0, 0, 0);
        Board board = new Board(tileSize, timerPanelWhite, timerPanelBlack);
        frame.add(board, gbc);

        resultNotifier.setBoard(board);
    }
}
