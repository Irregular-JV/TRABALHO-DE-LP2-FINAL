package controller;

import model.Espaco;
import model.EspacoDAO;

import java.util.List;

public class EspacoController {
    private EspacoDAO dao;
    
    public EspacoController() {
        this.dao = new EspacoDAO();
    }

    public List<Espaco> listarTodos() {
        return dao.listarTodos();
    }
}
