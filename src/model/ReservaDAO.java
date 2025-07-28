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

    public boolean horarioDisponivel(int idEspaco, String inicio, String fim) {
        String sql = "SELECT * FROM reservas WHERE id_espaco = ? AND " +
                     "(inicio < ? AND fim > ?)";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idEspaco);
            stmt.setString(2, fim);
            stmt.setString(3, inicio);

            ResultSet rs = stmt.executeQuery();
            boolean disponivel  = !rs.next();
            RelatorioController.registrarLog(
                disponivel
                ? "Verificação de disponibilidade: espaço " + idEspaco + " disponível em " + inicio + "–" + fim
                : "Verificação de disponibilidade: espaço " + idEspaco + " **não** disponível em " + inicio + "–" + fim
            );
            return disponivel; // Se não houver conflito, está disponível

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean salvarReserva(Reserva reserva) {
        if (!horarioDisponivel(reserva.getIdEspaco(), reserva.getInicio(), reserva.getFim())) {
            return false; // Horário ocupado
        }

        String sql = "INSERT INTO reservas (id_espaco, id_usuario, inicio, fim) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, reserva.getIdEspaco());
            stmt.setInt(2, reserva.getIdUsuario());
            stmt.setString(3, reserva.getInicio());
            stmt.setString(4, reserva.getFim());
            stmt.executeUpdate();
            RelatorioController.registrarLog(
                "Reserva criada: usuário " + reserva.getIdUsuario() +
                " no espaço " + reserva.getIdEspaco() +
                " de " + reserva.getInicio() +
                " até " + reserva.getFim()
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
