package view;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PainelReservas extends JPanel {
    public PainelReservas() {
        this.setBackground(Color.WHITE);
        this.add(new JLabel("Reservas"));
    }
}
