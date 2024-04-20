package ui;

import javax.swing.*;
import java.awt.*;

public class GameResultNotifier implements TimerListener{
    private final JFrame frame;

    public GameResultNotifier(JFrame frame){
        this.frame = frame;
    }
    @Override
    public void onTimeExpiration() {
        JOptionPane.showMessageDialog(frame, "Game over!");
    }
}
