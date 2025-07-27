package controller;

import model.Usuario;
import model.UsuarioDAO;
import view.TelaLogin;
import view.TelaPrincipal; // Supondo que esta classe exista
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EntrarController implements ActionListener {
    private TelaLogin view;
    private UsuarioDAO model;

    public EntrarController(TelaLogin view, UsuarioDAO model) {
        this.view = view;
        this.model = model;
        this.view.addLoginListener(this);
        this.view.addCadastroListener(this); // Supondo que você tenha esse
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == view.getBotaoEntrar()) {
            executarLogicaDeLogin();
        } else if (source == view.getBotaoCadastrar()) {
            // Se o botão for "cadastrar", chama a lógica de cadastro
            executarLogicaDeCadastro(); 
        }
    }

    private void executarLogicaDeLogin() {
        System.out.println("Executando lógica de login...");
        String username = view.getUsername();
        String senha = view.getSenha();

        if (username.isEmpty() || senha.isEmpty()) {
            view.mostrarMensagem("Por favor, preencha todos os campos.");
            return;
        }

        Usuario usuarioDoBanco = model.buscarPorUsername(username);

        if (usuarioDoBanco != null && usuarioDoBanco.getSenha().equals(senha)) {
            view.mostrarMensagem("Login bem-sucedido!");
            view.dispose();
            new TelaPrincipal().setVisible(true);
        } else {
            view.mostrarMensagem("Usuário ou senha inválidos.");
        }
    }

    private void executarLogicaDeCadastro() {
        System.out.println("Executando lógica de cadastro...");
        String username = view.getUsername();
        String senha = view.getSenha();

        if (username.isEmpty() || senha.isEmpty()) {
            view.mostrarMensagem("Para cadastrar, preencha username e senha.");
            return;
        }

        if (model.buscarPorUsername(username) != null) {
            view.mostrarMensagem("Este nome de usuário já está em uso. Tente outro.");
            return;
        }

        try {
            Usuario novoUsuario = new Usuario(username, senha, "comum");
            model.salvar(novoUsuario);
            view.mostrarMensagem("Cadastro realizado com sucesso! Você já está logado.");
            
            Usuario usuarioRecemCriado = model.buscarPorUsername(username);
            view.dispose();
            new TelaPrincipal().setVisible(true);

        } catch (Exception e) {
            view.mostrarMensagem("Erro durante o cadastro: " + e.getMessage());
        }
    }
}
