package model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UsuarioDAO {

    private Connection connection;

    public UsuarioDAO(){
        this.connection = new ConnectionFactory().getConnection();
    }

    public void salvar(Usuario usuario){
        String sql = "INSERT INTO Usuario (username, senha, nivelAcesso) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, usuario.getUsername());
            stmt.setString(2, usuario.getSenha());
            stmt.setString(3, usuario.getNivelAcesso());

            stmt.executeUpdate();
            System.out.println("Usuario salvo porra!");
        } catch (SQLException e){
            throw new RuntimeException("Erro ao salvar pqp" + e.getMessage());
        }
    }
}
