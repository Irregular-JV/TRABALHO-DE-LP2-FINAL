package controller;

import model.Reserva;
import model.ReservaDAO;

import java.util.List;

public class ReservaController {
    private ReservaDAO dao;

    public ReservaController() {
        this.dao = new ReservaDAO();
    }

    public boolean realizarReserva(Reserva reserva) {
        return dao.salvarReserva(reserva);
    }

    public List<Reserva> listarReservas() {
        return dao.listar();
    }

    public boolean removerReserva(int idReserva) {
        return new ReservaDAO().remover(idReserva);
    }
}
