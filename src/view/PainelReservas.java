package view;

import model.Reserva;
import javax.swing.*;

import controller.ReservaController;
import model.Espaco;
import model.EspacoDAO;

import model.Usuario;
import model.UsuarioDAO;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;

public class PainelReservas extends JPanel {
    private ReservaController reservaController;
    private EspacoDAO espacoDAO;
    private UsuarioDAO usuarioDAO;
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
        espacoDAO = new EspacoDAO();
        usuarioDAO = new UsuarioDAO();
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
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120)); // altura fixa, largura flexível

        Espaco espaco = espacoDAO.buscarPorId(reserva.getIdEspaco());

        JLabel lblEspaco = new JLabel("Espaço: " + espaco.getTipo());
        lblEspaco.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblEspaco.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblLocalizacao = new JLabel("Localização: " + espaco.getLocalizacao());
        lblLocalizacao.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblLocalizacao.setAlignmentX(Component.LEFT_ALIGNMENT);

        Usuario usuario = usuarioDAO.buscarPorId(reserva.getIdUsuario());

        JLabel lblUsuario = new JLabel("Usuário: " + usuario.getUsername());
        lblUsuario.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblUsuario.setForeground(Color.DARK_GRAY);
        lblUsuario.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Usando os métodos do LocalDateTime para montar a data e hora
        LocalDateTime inicio = reserva.getInicio();
        LocalDateTime fim = reserva.getFim();

        String data = String.format("%02d/%02d", inicio.getDayOfMonth(), inicio.getMonthValue());
        String horaInicio = String.format("%02d:%02d", inicio.getHour(), inicio.getMinute());
        String horaFim = String.format("%02d:%02d", fim.getHour(), fim.getMinute());

        JLabel lblHorario = new JLabel("Reserva dia " + data + " das " + horaInicio + " até " + horaFim);
        lblHorario.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblHorario.setForeground(Color.DARK_GRAY);
        lblHorario.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(lblEspaco);
        card.add(Box.createVerticalStrut(2));
        card.add(lblLocalizacao);
        card.add(Box.createVerticalStrut(5));
        card.add(lblUsuario);
        card.add(Box.createVerticalStrut(5));
        card.add(lblHorario);

        return card;
    }

}
