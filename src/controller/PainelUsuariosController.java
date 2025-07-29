package controller;

import model.Usuario;
import model.UsuarioDAO;
import model.ReservaDAO;
import view.PainelUsuarios; 
import view.TelaLogin;

import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PainelUsuariosController implements ActionListener {
    private PainelUsuarios view;
    private UsuarioDAO model;

    public PainelUsuariosController(PainelUsuarios view, UsuarioDAO model) {
        this.view = view;
        this.model = model;
        this.view.addRemoverListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        int idUsuario = view.getIdUsuarioSelecionado();

        if (idUsuario == -1) {
            view.mostrarMensagem("Por favor selecione um usuário.");
            return;
        }

        Usuario usuario = model.buscarPorId(idUsuario);

        if (usuario.getUsername().equals("admin")) {
            view.mostrarMensagem("Não é possível remover o admin.");
        } else {
            int confirm = JOptionPane.showConfirmDialog(
                null,
                "Tem certeza que deseja remover o usuário: " + usuario.getUsername() + "?\nTodas as reservas desse usuário também serão excluídas.",
                "Confirmar Remoção",
                JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    // 1. Remover reservas do usuário
                    ReservaDAO reservaDAO = new ReservaDAO();
                    reservaDAO.removerReservasPorUsuario(idUsuario);

                    // 2. Remover usuário
                    model.deletar(idUsuario);

                    JOptionPane.showMessageDialog(null, "Usuário e reservas removidos com sucesso!");
                    view.carregarDadosNaTabela();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao remover usuário: " + ex.getMessage());
                }
            }
        }
    }

}
