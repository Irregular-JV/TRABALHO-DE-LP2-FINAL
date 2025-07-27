package view;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PainelGerenciarEspacos extends JPanel  {
    public PainelGerenciarEspacos() {
        this.setBackground(Color.white);
        this.add(new JLabel("Painel: gerenciar espaços"));
    }
}