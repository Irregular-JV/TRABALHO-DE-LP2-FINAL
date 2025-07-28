package view;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.Component;
import java.awt.event.ActionListener;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

public class TelaLogin extends JFrame {
    private JPanel topoAzul;
    private JPanel painelCentral;
    JTextField campoUsuario = new JTextField();
    JButton botaoCadastro = new JButton("Cadastro");
    JButton botaoEntrar = new JButton("Entrar");
    JPasswordField campoSenha = new JPasswordField();

    public TelaLogin() {
        super("Login");
        configurarTelaLogin();
        configuraPainel();
        configurarComponentes();
        this.getRootPane().setDefaultButton(botaoEntrar); 
    }
    

    public void configurarTelaLogin() {
        this.setSize(400, 400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
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
        campoUsuario.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        campoUsuario.setBorder((BorderFactory.createTitledBorder("Usuário")));

        campoUsuario.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Usuário"),
            BorderFactory.createEmptyBorder(7, 12, 7, 12) // padding: top, left, bottom, right
        ));


        //Campo senha
        campoSenha.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        campoSenha.setBorder(BorderFactory.createTitledBorder("Senha"));

        campoSenha.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Senha"),
            BorderFactory.createEmptyBorder(7, 12, 7, 12)
        ));


        //Botão de entrar
        botaoEntrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        botaoEntrar.setBackground(new Color(45, 65, 95));
        botaoEntrar.setForeground(Color.WHITE);
        botaoEntrar.setFocusPainted(false);

        botaoEntrar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        botaoEntrar.setBorder(
            BorderFactory.createEmptyBorder(12, 12, 12, 12) // padding: top, left, bottom, right
        );

        //Botão de cadastro
        botaoCadastro.setAlignmentX(Component.CENTER_ALIGNMENT);
        botaoCadastro.setBackground(new Color(45, 65, 95));
        botaoCadastro.setForeground(Color.WHITE);
        botaoCadastro.setFocusPainted(false);

        botaoCadastro.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        botaoCadastro.setBorder(
            BorderFactory.createEmptyBorder(12, 12, 12, 12) // padding: top, left, bottom, right
        );
        
        

        //Adicionando os componentes
        painelCentral.add(campoUsuario);
        painelCentral.add(Box.createVerticalStrut(10));

        painelCentral.add(campoSenha);
        painelCentral.add(Box.createVerticalStrut(20));

        painelCentral.add(botaoEntrar);
        painelCentral.add(Box.createVerticalStrut(10));

        painelCentral.add(botaoCadastro);
    }

    public void addEntrarListener(ActionListener listener) {
    this.botaoEntrar.addActionListener(listener);
    }

    public void addCadastroListener(ActionListener listener) {
    this.botaoCadastro.addActionListener(listener);
    }

    public JButton getBotaoEntrar(){
        return this.botaoEntrar;
    }

    public JButton getBotaoCadastrar(){
        return this.botaoCadastro;
    }

    public String getUsername(){
        return this.campoUsuario.getText();
    }

    public String getSenha(){
        return new String(this.campoSenha.getPassword());
    }

    public void mostrarMensagem(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem);
    }
}
