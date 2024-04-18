package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimerPanel extends JPanel {
    private JLabel timerLabel;
    private Timer timer;
    private int timeRemaining;

    public TimerPanel(int tileSize) {
        timerLabel = new JLabel("5:00");
        add(timerLabel);
        timerLabel.setFont(new Font("Arial", Font.BOLD, (int)(tileSize/3.5)));
        setPreferredSize(new Dimension(tileSize *2, tileSize/2));

        timeRemaining = 300;

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTime();
            }
        });
    }

    public void startTimer() {
        timer.start();
    }

    public void stopTimer() {
        timer.stop();
    }

    private void updateTime() {
        int minutes = timeRemaining / 60;
        int seconds = timeRemaining % 60;

        String timeString = String.format("%d:%02d", minutes, seconds);
        timerLabel.setText(timeString);

        timeRemaining--;

        if (timeRemaining <= 0) {
            stopTimer();
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(4));

        g2d.setColor(new Color(248, 248, 248));
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.setColor(new Color(128, 128, 128));
        g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
    }
}
