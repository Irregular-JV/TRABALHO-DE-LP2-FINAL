package view;

import javax.swing.*;

import model.*;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class PainelHome extends JPanel {
    private String nome;
    private int idUsuario;
    private UsuarioDAO usuarioDAO;
    private EspacoDAO espacoDAO;
    private ReservaDAO reservaDAO;
    private final Map<String, Runnable> acoesDeNavegacao;
    private JLabel lblValorReservas;
    private JLabel lblValorEspacos;
    private JLabel lblValorUsuarios;

    public PainelHome(String nome, int idUsuario, Map<String, Runnable> acoesDeNavegacao) {
        this.nome = nome;
        this.idUsuario = idUsuario;

        this.espacoDAO = new EspacoDAO();
        this.usuarioDAO = new UsuarioDAO();
        this.reservaDAO = new ReservaDAO();
        this.acoesDeNavegacao = acoesDeNavegacao;
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);

        configuraLayout();
        atualizarDados();
    }

    public void configuraLayout() {
        // TOPO
        JPanel painelTopo = new JPanel();
        painelTopo.setLayout(new BoxLayout(painelTopo, BoxLayout.Y_AXIS));
        painelTopo.setBackground(Color.WHITE);
        painelTopo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel saudacao = new JLabel("Bem-vindo, " + nome);
        saudacao.setFont(new Font("SansSerif", Font.BOLD, 22));

        JLabel subtitulo = new JLabel("Aqui você pode reservar salas, gerenciar espaços e muito mais.");
        subtitulo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitulo.setForeground(Color.GRAY);

        painelTopo.add(saudacao);
        painelTopo.add(Box.createVerticalStrut(5));
        painelTopo.add(subtitulo);

        this.add(painelTopo, BorderLayout.NORTH);

        // CENTRO (cartões + rodapé)
        JPanel painelCentro = new JPanel();
        painelCentro.setLayout(new BoxLayout(painelCentro, BoxLayout.Y_AXIS));
        painelCentro.setBackground(Color.WHITE);
        painelCentro.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20)); // margem lateral e inferior

        // CARTÕES
        JPanel painelCartoes = new JPanel();
        painelCartoes.setLayout(new GridLayout(2, 2, 20, 20));
        painelCartoes.setBackground(Color.WHITE);
        painelCartoes.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelCartoes.setMaximumSize(new Dimension(900, 400));

        lblValorReservas = new JLabel("0");
        lblValorEspacos = new JLabel("0");
        lblValorUsuarios = new JLabel("0");

        painelCartoes.add(criarCartao("Reservas", lblValorReservas, Color.WHITE, "Reservas"));
        painelCartoes.add(criarCartao("Espaços cadastrados", lblValorEspacos, Color.WHITE, "Gerenciar Espaços"));
        painelCartoes.add(criarCartao("Usuários cadastrados", lblValorUsuarios, Color.WHITE, "Usuários"));

        painelCentro.add(painelCartoes);
        painelCentro.add(Box.createVerticalStrut(15));

        // RODAPÉ
        JPanel painelRodape = new JPanel();
        painelRodape.setLayout(new BoxLayout(painelRodape, BoxLayout.X_AXIS));
        painelRodape.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelRodape.setBackground(Color.WHITE);

        painelRodape.add(Box.createHorizontalStrut(10));

        painelCentro.add(painelRodape);

        // Envolver o centro para evitar expansão vertical
        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        wrapper.setBackground(Color.WHITE);
        wrapper.add(painelCentro);

        this.add(wrapper, BorderLayout.CENTER);
    }

    public JPanel criarCartao(String titulo, JLabel lblValor, Color cor, String nomeAcao) {
        JPanel p1 = new JPanel();
        p1.setBackground(cor);
        p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS));
        p1.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
                BorderFactory.createEmptyBorder(15, 10, 15, 10)
        ));

        lblValor.setFont(new Font("SansSerif", Font.BOLD, 19));
        lblValor.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));
        lblTitulo.setForeground(Color.DARK_GRAY);
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton botao = new JButton("Acessar");
        botao.setAlignmentX(Component.LEFT_ALIGNMENT);
        botao.setFocusPainted(false);
        botao.setFont(new Font("SansSerif", Font.BOLD, 16));
        botao.setForeground(Color.black);
        botao.setBackground(Color.WHITE);
        botao.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.black, 1, true),
                BorderFactory.createEmptyBorder(7, 10, 7, 10)
        ));
        botao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        Runnable acao = acoesDeNavegacao.get(nomeAcao);
        if (acao != null) {
            botao.addActionListener(e -> acao.run());
        }

        p1.add(lblTitulo);
        p1.add(lblValor);
        p1.add(Box.createVerticalStrut(15));
        p1.add(botao);

        return p1;
    }

     public void atualizarDados() {
        System.out.println("PainelHome: Atualizando os contadores...");
        try {
            List<Reserva> todasReservas = reservaDAO.listar();
            int reservasValidas = 0;
            for (Reserva r : todasReservas) {
                Espaco e = espacoDAO.buscarPorId(r.getIdEspaco());
                if (e != null) {
                    reservasValidas++;
                }
            } // Use o nome correto do seu método
            List<Espaco> espacos = espacoDAO.listarTodos();
            List<Usuario> usuarios = usuarioDAO.listarTodos();

            // Atualiza o texto dos JLabels com os novos valores
            lblValorReservas.setText(Integer.toString(reservasValidas));
            lblValorEspacos.setText(Integer.toString(espacos.size()));
            lblValorUsuarios.setText(Integer.toString(usuarios.size()));
        } catch (Exception e) {
            System.err.println("Erro ao atualizar dados do PainelHome: " + e.getMessage());
            // Define como 0 em caso de erro
            lblValorReservas.setText("0");
            lblValorEspacos.setText("0");
            lblValorUsuarios.setText("0");
        }
    }
}
