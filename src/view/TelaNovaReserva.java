package view;

import model.Reserva;
import model.ReservaDAO;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class TelaNovaReserva extends JDialog {
    private JTextField campoIdEspaco;
    private JTextField campoIdUsuario;
    private JTextField campoInicio;
    private JTextField campoFim;

    public TelaNovaReserva(JFrame parent) {
        super(parent, "Nova Reserva", true);
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(5, 2, 10, 10));

        add(new JLabel("ID do Espaço:"));
        campoIdEspaco = new JTextField();
        add(campoIdEspaco);

        add(new JLabel("ID do Usuário:"));
        campoIdUsuario = new JTextField();
        add(campoIdUsuario);

        add(new JLabel("Início (yyyy-MM-ddTHH:mm):"));
        campoInicio = new JTextField();
        add(campoInicio);

        add(new JLabel("Fim (yyyy-MM-ddTHH:mm):"));
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
            int idUsuario = Integer.parseInt(campoIdUsuario.getText());
            LocalDateTime inicio = LocalDateTime.parse(campoInicio.getText());
            LocalDateTime fim = LocalDateTime.parse(campoFim.getText());

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
