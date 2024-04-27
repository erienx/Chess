package ui;

import board.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameResultNotifier {
    private final JFrame frame;
    private Board board = null;

    public GameResultNotifier(JFrame frame) {
        this.frame = frame;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void onTimeExpiration() {
        String message;
        if (board.isWhitesTurn) {
            message = "<b>Black has won</b><br>by timeout";
        }
        else{
            message = "<b>White has won</b><br>by timeout";
        }
        createPopUp(message);
    }


    private void createPopUp(String message) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel messageLabel = new JLabel("<html><div style='text-align: center;'>" + message + "</div></html>");
        messageLabel.setFont(new Font("Helvetica", Font.PLAIN, 16));
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(messageLabel, BorderLayout.CENTER);

        JButton closeButton = new JButton("OK");
        closeButton.addActionListener(e -> {
            SwingUtilities.getWindowAncestor(panel).dispose();
            board.resetGame();
        });
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(closeButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        JDialog dialog = new JDialog(frame, "Game Result", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                SwingUtilities.getWindowAncestor(panel).dispose();
                board.resetGame();
            }
        });

        dialog.setContentPane(panel);
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }
}
