package ui;

import board.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
        } else {
            message = "<b>White has won</b><br>by timeout";
        }
        createPopUp(message);
    }


    private void createPopUp(String message) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel messageLabel = getMessageLabel(message);
        panel.add(messageLabel, BorderLayout.CENTER);

        JButton resetButton = getButton("Reset");
        resetButton.addActionListener(e -> {
            SwingUtilities.getWindowAncestor(panel).dispose();
            board.resetGame();
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(resetButton);
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

    private JLabel getMessageLabel(String message) {
        JLabel messageLabel = new JLabel("<html><div style='text-align: center;'>" + message + "</div></html>");
        messageLabel.setFont(new Font("Helvetica", Font.PLAIN, 16));
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        return messageLabel;
    }

    private JButton getButton(String message) {
        JButton resetButton = new JButton(message);
        resetButton.setFont(new Font("Helvetica", Font.BOLD, 14));
        resetButton.setBackground(new Color(213, 213, 213));

        resetButton.setBorder(BorderFactory.createLineBorder(new Color(168, 168, 168)));
        resetButton.setPreferredSize(new Dimension(100, 40));
        resetButton.setFocusable(false);
        resetButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                resetButton.setBackground(new Color(180, 180, 180));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                resetButton.setBackground(new Color(213, 213, 213));
            }

        });

        return resetButton;
    }
}
