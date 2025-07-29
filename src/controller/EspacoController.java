package controller;

import model.Espaco;
import model.EspacoDAO;
import view.DialogNovoEspaco;
import view.PainelGerenciarEspacos;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class EspacoController implements ActionListener {
    private EspacoDAO dao;
    private PainelGerenciarEspacos view;

    public EspacoController(PainelGerenciarEspacos view) {
        this.dao = new EspacoDAO();
        this.view = view;
    }

    public void salvar(Espaco espaco) {
        dao.salvar(espaco);
    }

    public List<Espaco> listarTodos() {
        return dao.listarTodos();
    }

    public void excluir(int id) {
        dao.deletar(id);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        switch (cmd) {
            case "novo_espaco" -> abrirDialogNovoEspaco();
            case "remover_espaco" -> removerEspacoSelecionado();
            // outros comandos como editar, buscar, etc.
        }
    }

    //gora público para ser usado fora do controller também
    public void abrirDialogNovoEspaco() {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(view);
        DialogNovoEspaco dialog = new DialogNovoEspaco(parentFrame);
        dialog.setVisible(true);

        try {
            Espaco novoEspaco = dialog.getEspacoCriado();
            salvar(novoEspaco);
            view.recarregarTabela();
        } catch (Exception ex) {
            System.out.println("Espaço não criado.");
        }
    }

    private void removerEspacoSelecionado() {
        int linha = view.getTabela().getSelectedRow();

        if (linha == -1) {
            JOptionPane.showMessageDialog(view, "Selecione um espaço para remover.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(view, "Deseja realmente remover este espaço?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        int idEspaco = view.getEspacoSelecionadoId(linha);
        excluir(idEspaco);
        view.recarregarTabela();
    }
}
