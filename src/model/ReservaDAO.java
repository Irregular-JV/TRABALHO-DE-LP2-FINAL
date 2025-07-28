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

    public boolean horarioDisponivel(int idEspaco, LocalDateTime inicio, LocalDateTime fim) {
        String sql = "SELECT * FROM reservas WHERE id_espaco = ? AND (inicio < ? AND fim > ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idEspaco);
            stmt.setString(2, fim.format(FORMATTER));
            stmt.setString(3, inicio.format(FORMATTER));

            ResultSet rs = stmt.executeQuery();
            boolean disponivel = !rs.next();

            RelatorioController.registrarLog(
                disponivel
                    ? "Verificação de disponibilidade: espaço " + idEspaco + " disponível em " + inicio.format(FORMATTER) + " – " + fim.format(FORMATTER)
                    : "Verificação de disponibilidade: espaço " + idEspaco + " **não** disponível em " + inicio.format(FORMATTER) + " – " + fim.format(FORMATTER)
            );

            return disponivel;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean salvarReserva(Reserva reserva) {
        if (!horarioDisponivel(reserva.getIdEspaco(), reserva.getInicio(), reserva.getFim())) {
            return false;
        }

        String sql = "INSERT INTO reservas (id_espaco, id_usuario, inicio, fim) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, reserva.getIdEspaco());
            stmt.setInt(2, reserva.getIdUsuario());
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

            RelatorioController.registrarLog(
                "Reserva criada pelo usuário " +  username + " (ID:" + reserva.getIdUsuario() + ")" +
                " no espaço " + reserva.getIdEspaco() +
                " de " + inicioFormatado +
                " até " + fimFormatado
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
                int id = rs.getInt("id");
                int idEspaco = rs.getInt("id_espaco");
                int idUsuario = rs.getInt("id_usuario");
                LocalDateTime inicio = LocalDateTime.parse(rs.getString("inicio"), FORMATTER);
                LocalDateTime fim = LocalDateTime.parse(rs.getString("fim"), FORMATTER);

                Reserva r = new Reserva(idEspaco, idUsuario, inicio, fim);
                r.setId(id);
                reservas.add(r);
            }

            RelatorioController.registrarLog("Listagem de reservas solicitada");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservas;
    }
}
