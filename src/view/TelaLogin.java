package view;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.Component;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

public class TelaLogin extends JFrame {
    private JPanel topoAzul;
    private JPanel painelCentral;

    public TelaLogin() {
        super("Login");
        configurarTelaLogin();
        configuraPainel();
        configurarComponentes();
    }
    

    public void configurarTelaLogin() {
        this.setSize(400, 330);
        this.setLocationRelativeTo(null);
    }

    public void configuraPainel() {
        this.setLayout(new BorderLayout());

        topoAzul = new JPanel();
        topoAzul.setLayout(new BorderLayout());
        topoAzul.setBackground(new Color(45, 65, 95));
        topoAzul.setPreferredSize(new Dimension(0, 60));
        this.add(topoAzul, BorderLayout.NORTH);

        painelCentral = new JPanel();
        painelCentral.setLayout(new BoxLayout(painelCentral, BoxLayout.Y_AXIS));
        painelCentral.setBackground(Color.WHITE);

        //Margem interna
        painelCentral.setBorder(BorderFactory.createEmptyBorder(25,50,40,50));
        painelCentral.add(Box.createVerticalGlue());

        this.add(painelCentral, BorderLayout.CENTER);

    }

    public void configurarComponentes() {
        JLabel titulo = new JLabel("Sistema de Gestão de Espaços");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 16)); 
        titulo.setForeground(Color.WHITE);
        titulo.setHorizontalAlignment(JLabel.CENTER);

        topoAzul.add(titulo, BorderLayout.CENTER);


        //Campo usuario
        JTextField campoUsuario = new JTextField();
        campoUsuario.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        campoUsuario.setBorder((BorderFactory.createTitledBorder("Usuário")));

        campoUsuario.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Usuário"),
            BorderFactory.createEmptyBorder(7, 12, 7, 12) // padding: top, left, bottom, right
        ));


        //Campo senha
        JPasswordField campoSenha = new JPasswordField();
        campoSenha.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        campoSenha.setBorder(BorderFactory.createTitledBorder("Senha"));

        campoSenha.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Senha"),
            BorderFactory.createEmptyBorder(7, 12, 7, 12)
        ));


        //Botão de entrar
        JButton botaoEntrar = new JButton("Entrar");
        botaoEntrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        botaoEntrar.setBackground(new Color(45, 65, 95));
        botaoEntrar.setForeground(Color.WHITE);
        botaoEntrar.setFocusPainted(false);

        botaoEntrar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        botaoEntrar.setBorder(
            BorderFactory.createEmptyBorder(12, 12, 12, 12) // padding: top, left, bottom, right
        );
        

        //Adicionando os componentes
        painelCentral.add(campoUsuario);
        painelCentral.add(Box.createVerticalStrut(10));

        painelCentral.add(campoSenha);
        painelCentral.add(Box.createVerticalStrut(20));

        painelCentral.add(botaoEntrar);
        
    }
}
