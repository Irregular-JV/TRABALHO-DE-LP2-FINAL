package view;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PainelLogs extends JPanel {
    public PainelLogs() {
        this.setBackground(Color.WHITE);
        this.add(new JLabel("Logs"));
    }
}
