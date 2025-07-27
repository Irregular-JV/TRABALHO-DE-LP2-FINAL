package view;

import model.Reserva;
import model.ReservaDAO;

import javax.swing.*;
import java.awt.*;

public class TelaNovaReserva extends JDialog {
    private JTextField campoIdEspaco;
    private int idUsuario;
    private JTextField campoInicio;
    private JTextField campoFim;

    public TelaNovaReserva(JFrame parent, int idUsuario) {
        super(parent, "Nova Reserva", true);
        this.idUsuario = idUsuario;
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(4, 2, 10, 10));

        add(new JLabel("ID do Espaço:"));
        campoIdEspaco = new JTextField();
        add(campoIdEspaco);

        add(new JLabel("Início (MM-dd-HH):"));
        campoInicio = new JTextField();
        add(campoInicio);

        add(new JLabel("Fim (MM-dd-HH):"));
        campoFim = new JTextField();
        add(campoFim);

        JButton btnReservar = new JButton("Reservar");
        btnReservar.addActionListener(e -> fazerReserva());
        add(btnReservar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());
        add(btnCancelar);

        setVisible(true);
    }

    private void fazerReserva() {
        try {
            int idEspaco = Integer.parseInt(campoIdEspaco.getText());
            String inicio = campoInicio.getText();
            String fim = campoFim.getText();

            Reserva reserva = new Reserva(idEspaco, idUsuario, inicio, fim);
            ReservaDAO dao = new ReservaDAO();

            if (dao.salvarReserva(reserva)) {
                JOptionPane.showMessageDialog(this, "Reserva realizada com sucesso!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Horário indisponível.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }
}
