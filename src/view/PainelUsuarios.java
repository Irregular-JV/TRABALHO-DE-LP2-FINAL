package view;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PainelUsuarios extends JPanel {
    public PainelUsuarios() {
        this.setBackground(Color.gray);
        this.add(new JLabel("Usuarios"));
    }
}
