package view;

import javax.swing.*;

import model.*;

import java.awt.*;

import java.util.List;

public class PainelHome extends JPanel {
    private String nome;
    private int idUsuario;
    private final Runnable onNovaReserva;
    private UsuarioDAO usuarioDAO;
    private EspacoDAO espacoDAO;
    private ReservaDAO reservaDAO;

    public PainelHome(String nome, int idUsuario, Runnable onNovaReserva) {
        this.nome = nome;
        this.idUsuario = idUsuario;

        this.espacoDAO = new EspacoDAO();
        this.usuarioDAO = new UsuarioDAO();
         this.reservaDAO = new ReservaDAO();
        this.onNovaReserva = onNovaReserva;
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);

        configuraLayout();
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

        List<Reserva> reservas = reservaDAO.listar();
        painelCartoes.add(criarCartao("Reservas", Integer.toString(reservas.size()), Color.WHITE));
        List<Espaco> espacos = espacoDAO.listarTodos();
        painelCartoes.add(criarCartao("Espaços cadastrados", Integer.toString(espacos.size()), Color.WHITE));
        List<Usuario> usuarios = usuarioDAO.listarTodos();
        painelCartoes.add(criarCartao("Usuários cadastrados", Integer.toString(usuarios.size()), Color.WHITE));

        painelCentro.add(painelCartoes);
        painelCentro.add(Box.createVerticalStrut(15));

        // RODAPÉ
        JPanel painelRodape = new JPanel();
        painelRodape.setLayout(new BoxLayout(painelRodape, BoxLayout.X_AXIS));
        painelRodape.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelRodape.setBackground(Color.WHITE);

        painelRodape.add(btnRodape("Nova Reserva"));
        painelRodape.add(Box.createHorizontalStrut(10));
        if("admin".equals(this.nome)){
            painelRodape.add(btnRodape("Novo Espaço"));
            painelRodape.add(Box.createHorizontalStrut(10));
            painelRodape.add(btnRodape("Relatórios"));
        }
        

        painelCentro.add(painelRodape);

        // Envolver o centro para evitar expansão vertical
        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        wrapper.setBackground(Color.WHITE);
        wrapper.add(painelCentro);

        this.add(wrapper, BorderLayout.CENTER);
    }

    public JPanel criarCartao(String titulo, String valor, Color cor) {
        JPanel p1 = new JPanel();
        p1.setBackground(cor);
        p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS));
        p1.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
                BorderFactory.createEmptyBorder(15, 10, 15, 10)
        ));

        JLabel lblvalor = new JLabel(valor);
        lblvalor.setFont(new Font("SansSerif", Font.BOLD, 19));
        lblvalor.setAlignmentX(Component.LEFT_ALIGNMENT);

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

        p1.add(lblTitulo);
        p1.add(lblvalor);
        p1.add(Box.createVerticalStrut(15));
        p1.add(botao);

        return p1;
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
