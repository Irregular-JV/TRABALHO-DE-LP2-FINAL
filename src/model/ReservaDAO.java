package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import controller.RelatorioController;

public class ReservaDAO {
    private Connection connection;

    public ReservaDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public boolean horarioDisponivel(int idEspaco, String data, String inicio, String fim) {
        // Verifica se início é antes de fim
        if (inicio.compareTo(fim) >= 0) {
            return false;
        }

        String sql = "SELECT * FROM reservas WHERE id_espaco = ? AND data = ? AND (inicio < ? AND fim > ?)";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idEspaco);
            stmt.setString(2, data);
            stmt.setString(3, fim);
            stmt.setString(4, inicio);

            ResultSet rs = stmt.executeQuery();
            boolean disponivel = !rs.next();

            return disponivel;

        } catch (SQLException e) {
            return false;
        }
    }


    public boolean salvarReserva(Reserva reserva) {
        if (!horarioDisponivel(reserva.getIdEspaco(), reserva.getData(), reserva.getHoraInicio(), reserva.getHoraFim())) {
            return false; // Horário ocupado
        }

        String sql = "INSERT INTO reservas (id_espaco, id_usuario, data, inicio, fim) VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, reserva.getIdEspaco());
            stmt.setInt(2, reserva.getIdUsuario());
            stmt.setString(3, reserva.getData());
            stmt.setString(4, reserva.getHoraInicio());
            stmt.setString(5, reserva.getHoraFim());
            stmt.executeUpdate();

            RelatorioController.registrarLog(
                "Reserva criada: usuário " + reserva.getIdUsuario() +
                " no espaço " + reserva.getIdEspaco() +
                " do dia " + reserva.getData() +
                " de " + reserva.getHoraInicio() +
                " até " + reserva.getHoraFim()
            );
            return true;

        } catch (SQLException e) {
            RelatorioController.registrarLog("Erro ao salvar reserva: " + e.getMessage());
            return false;
        }
    }

    // (Opcional) Listar todas as reservas
    public List<Reserva> listar() {
        List<Reserva> reservas = new ArrayList<>();
        String sql = "SELECT * FROM reservas";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Reserva r = new Reserva(
                    rs.getInt("id_espaco"),
                    rs.getInt("id_usuario"),
                    rs.getString("data"),
                    rs.getString("inicio"),
                    rs.getString("fim")
                );
                r.setId(rs.getInt("id"));
                reservas.add(r);
                RelatorioController.registrarLog("Listagem de reservas solicitada");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservas;
    }
}
