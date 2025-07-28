package model;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import controller.RelatorioController;

public class ReservaDAO {
    private Connection connection;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public ReservaDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

<<<<<<< HEAD
    public boolean horarioDisponivel(int idEspaco, String data, String inicio, String fim) {
        // Verifica se início é antes de fim
        if (inicio.compareTo(fim) >= 0) {
            return false;
        }

        String sql = "SELECT * FROM reservas WHERE id_espaco = ? AND data = ? AND (inicio < ? AND fim > ?)";
=======
    public boolean horarioDisponivel(int idEspaco, LocalDateTime inicio, LocalDateTime fim) {
        String sql = "SELECT * FROM reservas WHERE id_espaco = ? AND (inicio < ? AND fim > ?)";
>>>>>>> main

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idEspaco);
<<<<<<< HEAD
            stmt.setString(2, data);
            stmt.setString(3, fim);
            stmt.setString(4, inicio);
=======
            stmt.setString(2, fim.format(FORMATTER));
            stmt.setString(3, inicio.format(FORMATTER));
>>>>>>> main

            ResultSet rs = stmt.executeQuery();
            boolean disponivel = !rs.next();

<<<<<<< HEAD
=======
            RelatorioController.registrarLog(
                disponivel
                    ? "Verificação de disponibilidade: espaço " + idEspaco + " disponível em " + inicio.format(FORMATTER) + " – " + fim.format(FORMATTER)
                    : "Verificação de disponibilidade: espaço " + idEspaco + " **não** disponível em " + inicio.format(FORMATTER) + " – " + fim.format(FORMATTER)
            );

>>>>>>> main
            return disponivel;

        } catch (SQLException e) {
            return false;
        }
    }


    public boolean salvarReserva(Reserva reserva) {
<<<<<<< HEAD
        if (!horarioDisponivel(reserva.getIdEspaco(), reserva.getData(), reserva.getHoraInicio(), reserva.getHoraFim())) {
            return false; // Horário ocupado
=======
        if (!horarioDisponivel(reserva.getIdEspaco(), reserva.getInicio(), reserva.getFim())) {
            return false;
>>>>>>> main
        }

        String sql = "INSERT INTO reservas (id_espaco, id_usuario, data, inicio, fim) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, reserva.getIdEspaco());
            stmt.setInt(2, reserva.getIdUsuario());
<<<<<<< HEAD
            stmt.setString(3, reserva.getData());
            stmt.setString(4, reserva.getHoraInicio());
            stmt.setString(5, reserva.getHoraFim());
            stmt.executeUpdate();

=======
            stmt.setString(3, reserva.getInicio().format(FORMATTER));
            stmt.setString(4, reserva.getFim().format(FORMATTER));
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                reserva.setId(generatedKeys.getInt(1));
            }

            DateTimeFormatter exibicaoFormatter = DateTimeFormatter.ofPattern("dd-MM HH:mm");

            String inicioFormatado = reserva.getInicio().format(exibicaoFormatter);
            String fimFormatado = reserva.getFim().format(exibicaoFormatter);

            String username = usuarioDAO.buscarPorId(reserva.getIdUsuario()).getUsername();

>>>>>>> main
            RelatorioController.registrarLog(
                "Reserva criada pelo usuário " +  username + " (ID:" + reserva.getIdUsuario() + ")" +
                " no espaço " + reserva.getIdEspaco() +
<<<<<<< HEAD
                " do dia " + reserva.getData() +
                " de " + reserva.getHoraInicio() +
                " até " + reserva.getHoraFim()
=======
                " de " + inicioFormatado +
                " até " + fimFormatado
>>>>>>> main
            );

            RelatorioController controller = new RelatorioController();
            RelatorioController.registrarLog(controller.gerarComprovanteReserva(reserva));

            return true;

        } catch (SQLException e) {
            RelatorioController.registrarLog("Erro ao salvar reserva: " + e.getMessage());
            return false;
        }
    }

    public List<Reserva> listar() {
        List<Reserva> reservas = new ArrayList<>();
        String sql = "SELECT * FROM reservas";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
<<<<<<< HEAD
                Reserva r = new Reserva(
                    rs.getInt("id_espaco"),
                    rs.getInt("id_usuario"),
                    rs.getString("data"),
                    rs.getString("inicio"),
                    rs.getString("fim")
                );
                r.setId(rs.getInt("id"));
=======
                int id = rs.getInt("id");
                int idEspaco = rs.getInt("id_espaco");
                int idUsuario = rs.getInt("id_usuario");
                LocalDateTime inicio = LocalDateTime.parse(rs.getString("inicio"), FORMATTER);
                LocalDateTime fim = LocalDateTime.parse(rs.getString("fim"), FORMATTER);

                Reserva r = new Reserva(idEspaco, idUsuario, inicio, fim);
                r.setId(id);
>>>>>>> main
                reservas.add(r);
            }

            RelatorioController.registrarLog("Listagem de reservas solicitada");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservas;
    }
}
