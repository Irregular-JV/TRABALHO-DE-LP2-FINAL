package view;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.UsuarioDAO;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

import controller.*;


public class TelaPrincipal extends JFrame{
    private String nomeUsuario;
    private int idUsuario;
    private JPanel navBar;
    private JPanel menuLateral;
    private JPanel painelPrincipal;
    private JButton botaoAtivo;
    private Map<String, JPanel> painelRegistradores = new HashMap<>();
    private PainelReservas painelReservas;


    public TelaPrincipal(String nomeUsuario, int idUsuario) {
        super("Gestão de Espaços Academicos");
        this.nomeUsuario = nomeUsuario;
        this.idUsuario = idUsuario;
        configurarJanela();
        configurarPaineis();
        montarLayout();
        configNavBar();
        registrarPaineis();
        this.setVisible(true);

    }

    public void configurarJanela() {
        this.setSize(900, 600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Centraliza na tela ao rodar
        this.setLocationRelativeTo(null);
    }

    public void configurarPaineis() {

        this.setLayout(new BorderLayout());
        //Criando a instancia dos paineis
        navBar = new JPanel();
        navBar.setBackground(new Color(45, 65, 95));
        navBar.setPreferredSize(new Dimension(this.getWidth(), 60));
        navBar.setLayout((new BorderLayout()));


        menuLateral = new JPanel();
        menuLateral.setLayout(new BoxLayout(menuLateral, BoxLayout.Y_AXIS));
        painelPrincipal = new JPanel(new CardLayout());
        painelPrincipal.setBackground(Color.WHITE);
        
        String[] nomes = { "Home", "Gerenciar Espaços", "Reservas", "Usuários", "Relatórios", "Logs", "Sair"};

        Color corFundo = new Color(247, 249, 252); // mesma do menu

        for(String nome : nomes) {
            JButton btn = criarBotaoMenu(nome, corFundo);

            // Efeito hover
            btn.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    btn.setBackground(new Color(230, 235, 245));
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    if (btn != botaoAtivo) {
                        btn.setBackground(corFundo);
                    }
                }
            });

            // Evento de clique: marcar como ativo
            btn.addActionListener(evt -> {

                if(nome.equals("Sair")) {
                    this.dispose(); //Fecha a tela principal
                    TelaLogin novaTelaLogin = new TelaLogin();
                    new EntrarController(novaTelaLogin, new UsuarioDAO());
                    novaTelaLogin.setVisible(true);
                    return;
                }

                if (botaoAtivo != null) {
                    botaoAtivo.setBackground(corFundo);
                }
                btn.setBackground(new Color(210, 225, 255));
                botaoAtivo = btn;

                if (painelRegistradores.containsKey(nome)) {
                    if (nome.equals("Logs")) {
                        PainelLogs painelLogs = (PainelLogs) painelRegistradores.get("Logs");
                        painelLogs.atualizarLog();
                    }
                    CardLayout c1 = (CardLayout) painelPrincipal.getLayout();
                    c1.show(painelPrincipal, nome);
                }
            });

                menuLateral.add(btn);
                menuLateral.add(Box.createVerticalStrut(10));

        }
    }

    public void montarLayout() {
        menuLateral.setBackground(new Color(247, 249, 252));
        painelPrincipal.setBackground(Color.WHITE);

        menuLateral.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //Layout fixo: menu a esquerda, conteudo no centro
        menuLateral.setPreferredSize(new Dimension(200, 0));
        this.add(menuLateral, BorderLayout.WEST);
        this.add(painelPrincipal, BorderLayout.CENTER);
        this.add(navBar,BorderLayout.NORTH);

    }

    private JButton criarBotaoMenu(String texto, Color corFundo) {
        JButton btn = new JButton(texto);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(true);
        btn.setOpaque(true);
        btn.setBackground(corFundo);
        btn.setPreferredSize(new Dimension(180, 40));
        btn.setMaximumSize(new Dimension(180, 40));
        btn.setHorizontalAlignment(JButton.LEFT);
        btn.setFont(new Font("SansSerif", Font.PLAIN, 14));
        btn.setIconTextGap(10);
        return btn;
    }

    public void configNavBar() {
        JPanel painelEsquerdo = new JPanel();
        painelEsquerdo.setOpaque(false);
        painelEsquerdo.setLayout(new BoxLayout(painelEsquerdo, BoxLayout.X_AXIS));

        JPanel painelDireito = new JPanel();
        painelDireito.setOpaque(false);
        painelDireito.setLayout(new BoxLayout(painelDireito, BoxLayout.X_AXIS));

        JLabel titulo = new JLabel("Sistema de Gestão de Espaços");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 16));

        JLabel usuario = new JLabel(nomeUsuario);
        usuario.setForeground(Color.WHITE);
        usuario.setFont(new Font("SansSerif", Font.BOLD, 16));

        painelEsquerdo.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        painelDireito.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));

        painelEsquerdo.add(titulo);
        painelDireito.add(usuario);

        navBar.add(painelEsquerdo, BorderLayout.WEST);
        navBar.add(painelDireito, BorderLayout.EAST);

    }

    public void registrarPaineis() {
        painelReservas = new PainelReservas();
        PainelHome home = new PainelHome(nomeUsuario, idUsuario, () -> {
            new TelaNovaReserva(this, idUsuario, () -> painelReservas.atualizarReservas());
        });
      
        PainelUsuarios painelUsuarios = new PainelUsuarios(this.nomeUsuario);
        UsuarioDAO dao = new UsuarioDAO();

        new PainelUsuariosController(painelUsuarios, dao);

        
        painelRegistradores.put("Home", home);
        painelRegistradores.put("Gerenciar Espaços", new PainelGerenciarEspacos());
        painelRegistradores.put("Reservas", painelReservas);
        painelRegistradores.put("Usuários", painelUsuarios);
        painelRegistradores.put("Relatórios", new PainelRelatorios());
        painelRegistradores.put("Logs", new PainelLogs());

        for(Map.Entry<String, JPanel> entry: painelRegistradores.entrySet()) {
            painelPrincipal.add(entry.getValue(), entry.getKey());
        }
        CardLayout layout = (CardLayout) painelPrincipal.getLayout();
        layout.show(painelPrincipal, "Home"); // mostra o painel Home ao iniciar
    }
}
