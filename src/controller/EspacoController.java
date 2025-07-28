package controller;

import model.Espaco;
import model.EspacoDAO;

import java.util.List;

public class EspacoController {
    private EspacoDAO dao;

    public EspacoController() {
        this.dao = new EspacoDAO();
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

}
