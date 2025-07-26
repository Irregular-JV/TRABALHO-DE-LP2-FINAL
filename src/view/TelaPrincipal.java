package view;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;


public class TelaPrincipal extends JFrame{

    private JPanel navBar;
    private JPanel menuLateral;
    private JPanel painelPrincipal;
    private JButton botaoAtivo;


    public TelaPrincipal() {
        super("Gestão de Espaços Academicos");
        configurarJanela();
        configurarPaineis();
        montarLayout();
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
        painelPrincipal = new JPanel();

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
            btn.addActionListener(_ -> {
                if (botaoAtivo != null) {
                    botaoAtivo.setBackground(corFundo);
                }
                btn.setBackground(new Color(210, 225, 255)); // cor de seleção
                botaoAtivo = btn;
            });

            menuLateral.add(btn);
            menuLateral.add(Box.createVerticalStrut(10));
        }


        
    }


    public void montarLayout() {
        menuLateral.setBackground(new Color(247, 249, 252));
        painelPrincipal.setBackground(Color.WHITE);

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


}
