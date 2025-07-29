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
import java.util.Base64;
import java.util.List;

public class PainelReservas extends JPanel {
    private ReservaController reservaController;
    private EspacoDAO espacoDAO;
    private UsuarioDAO usuarioDAO;
    private JPanel painelCentro;
    private int idUsuario;
    private String nomeUsuario;
    private final Runnable onNovaReserva;

    public PainelReservas(int idUsuario, String nomeUsuario, Runnable onNovaReserva) {
        this.idUsuario = idUsuario;
        this.nomeUsuario = nomeUsuario;
        // 1. Configuração do Painel Principal
        this.setBackground(Color.WHITE);
        this.setLayout(new BorderLayout()); // O layout principal que vai organizar tudo
        this.onNovaReserva = onNovaReserva;

        // --- INICIALIZAÇÃO (continua igual) ---
        reservaController = new ReservaController();
        espacoDAO = new EspacoDAO();
        usuarioDAO = new UsuarioDAO();

        // --- 2. PAINEL DO TOPO (seu código, sem mudanças) ---
        JPanel painelTopo = new JPanel();
        painelTopo.setBackground(Color.WHITE);
        painelTopo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        painelTopo.setLayout(new BoxLayout(painelTopo, BoxLayout.Y_AXIS));
        JLabel titulo = new JLabel("Reservas Cadastradas");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        titulo.setForeground(Color.DARK_GRAY);
        painelTopo.add(titulo);
        this.add(painelTopo, BorderLayout.NORTH);

        // --- 3. PAINEL CENTRAL (COM ROLAGEM) ---
        // Este painel vai conter APENAS os cards de reserva
        painelCentro = new JPanel();
        painelCentro.setBackground(Color.WHITE);
        painelCentro.setLayout(new BoxLayout(painelCentro, BoxLayout.Y_AXIS));
        painelCentro.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 20));

        // Colocamos o painel de cards dentro da barra de rolagem
        JScrollPane scrollPane = new JScrollPane(painelCentro);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220))); // Remove a borda do scrollpane
        this.add(scrollPane, BorderLayout.CENTER); // Adiciona ao CENTRO do layout principal

        // --- 4. PAINEL DO RODAPÉ (COM O BOTÃO) ---
        JPanel painelRodape = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Alinhado à esquerda
        painelRodape.setBackground(Color.WHITE);
        painelRodape.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Padding

        JButton btnNovaReserva = btnRodape("Nova Reserva"); // Usa seu método helper
        painelRodape.add(btnNovaReserva);

        this.add(painelRodape, BorderLayout.SOUTH); // Adiciona ao SUL do layout principal

        // --- 5. CARGA INICIAL DOS DADOS (continua igual) ---
        atualizarReservas();
    }

    public void atualizarReservas() {
        painelCentro.removeAll();

        List<Reserva> reservas = reservaController.listarReservas();
        boolean encontrouAlguma = false;

        for (Reserva r : reservas) {
            JPanel card = criarCartaoReserva(r);
            if (card != null) {
                painelCentro.add(card);
                painelCentro.add(Box.createVerticalStrut(10));
                encontrouAlguma = true;
            }
        }

        if (!encontrouAlguma) {
            JLabel vazio = new JLabel("Nenhuma reserva cadastrada.");
            vazio.setFont(new Font("SansSerif", Font.ITALIC, 16));
            vazio.setForeground(Color.GRAY);
            painelCentro.add(vazio);
        }

        painelCentro.revalidate();
        painelCentro.repaint();
        this.revalidate();
        this.repaint();
    }


    private JPanel criarCartaoReserva(Reserva reserva) {
        Espaco espaco = espacoDAO.buscarPorId(reserva.getIdEspaco());

        if (espaco == null) return null;

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        // ----- Conteúdo à esquerda -----
        JPanel conteudo = new JPanel();
        conteudo.setLayout(new BoxLayout(conteudo, BoxLayout.Y_AXIS));
        conteudo.setBackground(Color.WHITE);

        JLabel lblEspaco = new JLabel("Espaço: " + espaco.getTipo());
        lblEspaco.setFont(new Font("SansSerif", Font.BOLD, 16));

        JLabel lblLocalizacao = new JLabel("Nome: " + espaco.getLocalizacao());
        lblLocalizacao.setFont(new Font("SansSerif", Font.BOLD, 16));

        Usuario usuario = usuarioDAO.buscarPorId(reserva.getIdUsuario());
        JLabel lblUsuario = new JLabel("Usuário: " + usuario.getUsername());
        lblUsuario.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblUsuario.setForeground(Color.DARK_GRAY);

        LocalDateTime inicio = reserva.getInicio();
        LocalDateTime fim = reserva.getFim();
        String data = String.format("%02d/%02d", inicio.getDayOfMonth(), inicio.getMonthValue());
        String horaInicio = String.format("%02d:%02d", inicio.getHour(), inicio.getMinute());
        String horaFim = String.format("%02d:%02d", fim.getHour(), fim.getMinute());

        JLabel lblHorario = new JLabel("Reserva dia " + data + " das " + horaInicio + " até " + horaFim);
        lblHorario.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblHorario.setForeground(Color.DARK_GRAY);

        conteudo.add(lblEspaco);
        conteudo.add(Box.createVerticalStrut(2));
        conteudo.add(lblLocalizacao);
        conteudo.add(Box.createVerticalStrut(5));
        conteudo.add(lblUsuario);
        conteudo.add(Box.createVerticalStrut(5));
        conteudo.add(lblHorario);

        card.add(conteudo, BorderLayout.CENTER);

        // ----- Botão de remover à direita -----
        if (reserva.getIdUsuario() == idUsuario || nomeUsuario.equals("admin")) {
            JButton btnRemover = new JButton();
            btnRemover.setToolTipText("Remover reserva");
            btnRemover.setContentAreaFilled(false);
            btnRemover.setBorderPainted(false);
            btnRemover.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            ImageIcon trashIcon = new ImageIcon(getClass().getResource("/resources/trash.png"));
            Image img = trashIcon.getImage();
            Image resizedImg = img.getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            trashIcon = new ImageIcon(resizedImg);
            
            btnRemover.setIcon(trashIcon);


            btnRemover.addActionListener(evt -> {
                int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Tem certeza que deseja remover esta reserva?",
                    "Confirmação",
                    JOptionPane.YES_NO_OPTION
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    reservaController.removerReserva(reserva.getId());
                    atualizarReservas();
                }
            });

            JPanel painelLateral = new JPanel(new GridBagLayout());
            painelLateral.setBackground(Color.WHITE);
            painelLateral.add(btnRemover);

            card.add(painelLateral, BorderLayout.EAST);
        }

        return card;
    }

    public JButton btnRodape(String nome) {
        JButton btn = new JButton(nome);
        btn.setFont(new Font("SansSerif", Font.BOLD, 18));
        btn.setForeground(Color.black);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(147, 220, 225), 1, true),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        btn.setFocusPainted(false);
        btn.setBackground(Color.WHITE);

        btn.addActionListener(evt -> {
            if (nome.equals("Nova Reserva")) {
                onNovaReserva.run();
            }
        });
        return btn;
    }


}
