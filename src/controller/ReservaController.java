package controller;

import model.Reserva;
import model.ReservaDAO;

import java.util.List;

public class ReservaController {
    private ReservaDAO dao;
    private RelatorioController relatorioController;

    public ReservaController() {
        this.dao = new ReservaDAO();
    }

    public boolean realizarReserva(Reserva reserva) {
        return dao.salvarReserva(reserva); // você pode colocar validações aqui depois
    }

    public List<Reserva> listarReservas() {
        return dao.listar();
    }
}
