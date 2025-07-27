package model;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAO {
    private Connection connection;

    public ReservaDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public boolean horarioDisponivel(int idEspaco, LocalDateTime inicio, LocalDateTime fim) {
        String sql = "SELECT * FROM reservas WHERE id_espaco = ? AND " +
                     "(inicio < ? AND fim > ?)";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idEspaco);
            stmt.setTimestamp(2, Timestamp.valueOf(fim));
            stmt.setTimestamp(3, inicio);

            ResultSet rs = stmt.executeQuery();
            return !rs.next(); // Se não houver conflito, está disponível

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
            stmt.setTimestamp(3, Timestamp.valueOf(reserva.getInicio()));
            stmt.setTimestamp(4, Timestamp.valueOf(reserva.getFim()));
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
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
                    rs.getTimestamp("inicio").toLocalDateTime(),
                    rs.getTimestamp("fim").toLocalDateTime()
                );
                r.setId(rs.getInt("id"));
                reservas.add(r);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservas;
    }
}
