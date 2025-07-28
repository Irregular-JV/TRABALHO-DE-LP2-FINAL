package view;

import model.Reserva;
import javax.swing.*;

import controller.ReservaController;

import java.awt.*;
import java.util.List;

public class PainelReservas extends JPanel {
    private ReservaController reservaController;
    private JPanel painelCentro;

    public PainelReservas() {
        this.setBackground(Color.WHITE);
        this.setLayout(new BorderLayout());

        // Título no topo
        JPanel painelTopo = new JPanel();
        painelTopo.setBackground(Color.WHITE);
        painelTopo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        painelTopo.setLayout(new BoxLayout(painelTopo, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("Reservas Cadastradas");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        titulo.setForeground(Color.DARK_GRAY);

        painelTopo.add(titulo);
        this.add(painelTopo, BorderLayout.NORTH);

        // Painel central que vai conter os cards das reservas
        painelCentro = new JPanel();
        painelCentro.setBackground(Color.WHITE);
        painelCentro.setLayout(new BoxLayout(painelCentro, BoxLayout.Y_AXIS));
        painelCentro.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        reservaController = new ReservaController();
        atualizarReservas();

        JScrollPane scrollPane = new JScrollPane(painelCentro);
        scrollPane.setBorder(null);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    public void atualizarReservas() {
        painelCentro.removeAll();

        List<Reserva> reservas = reservaController.listarReservas();

        if (reservas.isEmpty()) {
            JLabel vazio = new JLabel("Nenhuma reserva cadastrada.");
            vazio.setFont(new Font("SansSerif", Font.ITALIC, 16));
            vazio.setForeground(Color.GRAY);
            painelCentro.add(vazio);
        } else {
            for (Reserva r : reservas) {
                painelCentro.add(criarCartaoReserva(r));
                painelCentro.add(Box.createVerticalStrut(10));
            }
        }

        painelCentro.revalidate();
        painelCentro.repaint();
        this.revalidate(); // também atualiza o PainelReservas em si
        this.repaint();
    }

    private JPanel criarCartaoReserva(Reserva reserva) {
        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100)); // altura fixa, largura flexível

        JLabel lblEspaco = new JLabel("Espaço ID: " + reserva.getIdEspaco());
        lblEspaco.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblEspaco.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblUsuario = new JLabel("Usuário ID: " + reserva.getIdUsuario());
        lblUsuario.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblUsuario.setForeground(Color.DARK_GRAY);
        lblUsuario.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Processa datas
        String[] inicioParts = reserva.getInicio().split("-");
        String[] fimParts = reserva.getFim().split("-");

        String data = inicioParts[1] + "/" + inicioParts[0]; // dd/MM
        String horaInicio = inicioParts[2] + ":00";
        String horaFim = fimParts[2] + ":00";

        JLabel lblHorario = new JLabel("Reserva dia " + data + " das " + horaInicio + " até " + horaFim);
        lblHorario.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblHorario.setForeground(Color.DARK_GRAY);
        lblHorario.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(lblEspaco);
        card.add(Box.createVerticalStrut(5));
        card.add(lblUsuario);
        card.add(Box.createVerticalStrut(5));
        card.add(lblHorario);

        return card;
    }   

}
