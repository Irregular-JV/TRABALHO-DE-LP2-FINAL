package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    private Connection connection;

    public UsuarioDAO(){
        this.connection = new ConnectionFactory().getConnection();
    }

    // CRUD (Create (Criar), Read (Ler), Update (Atualizar) e Delete (Excluir))

    // Incluí-lo no banco de dados

    public void salvar(Usuario usuario){
        String sql = "INSERT INTO Usuario (username, senha, nivelAcesso) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, usuario.getUsername());
            stmt.setString(2, usuario.getSenha());
            stmt.setString(3, usuario.getNivelAcesso());

            stmt.executeUpdate();
            System.out.println("Usuario salvo com sucesso!");
        } catch (SQLException e){
            throw new RuntimeException("Erro ao salvar dados do usuário" + e.getMessage());
        }
    }

    // Deletar usuário do banco de dados

    public void deletar(int id){
        String sql = "DELETE FROM Usuario WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            stmt.executeUpdate();
            System.out.println("Usuario deletado com sucesso!");
        } catch (SQLException e){
            throw new RuntimeException("Erro ao deletar usuário" + e.getMessage());
        }
    }

    // Listagem de todos os usuários no banco de dados

    public List<Usuario> listarTodos(){
        String sql = "SELECT * FROM Usuario";
        List<Usuario> usuarios = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql); 
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = (rs.getInt("id"));
                String username = (rs.getString("username"));
                String senha = (rs.getString("senha"));
                String nivelAcesso = (rs.getString("nivelAcesso"));

                Usuario usuario = new Usuario(id, username, senha, nivelAcesso);
                usuarios.add(usuario);
            }
        } catch (SQLException e){
            throw new RuntimeException("Erro ao listar usuários" + e.getMessage());
        }
        System.out.println("Listagem feita com sucesso!");
        return usuarios;
    }
    
    // Buscar personalizada

    // Buscar pelo username do usuário

    public Usuario buscarPorUsername(String username){
        String sql = "SELECT * FROM Usuario WHERE username = ?";
        Usuario usuario = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) { 
                int id = rs.getInt("id");
                String foundUsername = rs.getString("username");
                String senha = rs.getString("senha");
                String nivelAcesso = rs.getString("nivelAcesso");
                usuario = new Usuario(id, foundUsername, senha, nivelAcesso);
            }
        }

        } catch (SQLException e){
            throw new RuntimeException("Erro ao encontrar usuario pelo username: " + e.getMessage());
        }
        System.out.println("Usuário encontrado com sucesso!");
        return usuario;
    }

    // Buscar pelo ID do usuário

    public Usuario buscarPorId(int id){
        String sql = "SELECT * FROM Usuario WHERE id = ?";
        Usuario usuario = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) { 
                int foundId = rs.getInt("id");
                String Username = rs.getString("username");
                String senha = rs.getString("senha");
                String nivelAcesso = rs.getString("nivelAcesso");
                usuario = new Usuario(foundId, Username, senha, nivelAcesso);
            }
        }

        } catch (SQLException e){
            throw new RuntimeException("Erro ao encontrar usuario pelo id: " + e.getMessage());
        }
        System.out.println("Usuário encontrado com sucesso!");
        return usuario;
    }

    // Update personalizado

    // Update de senha (caso haja uma feature de esqueci minha senha, ou alterar senha)

    public void atualizarSenha(int id, String novaSenha){
        String sql = "UPDATE Usuario SET senha = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, novaSenha);
            stmt.setInt(2, id);

            stmt.executeUpdate();
            System.out.println("Senha do usuário atualizada com sucesso!");
        } catch (SQLException e){
            throw new RuntimeException("Erro ao atualizar senha do usuário: " + e.getMessage());
        }
    }

    // Update de username, caso usuário queira trocá-lo

    public void atualizarUsername(int id, String novoUsername){
        String sql = "UPDATE Usuario SET username = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, novoUsername);
            stmt.setInt(2, id);

            stmt.executeUpdate();
            System.out.println("Username do usuário atualizada com sucesso!");
        } catch (SQLException e){
            throw new RuntimeException("Erro ao atualizar username do usuário: " + e.getMessage());
        }
    }

    // Update de nível de acesso, caso haja a necessidade

    public void atualizarNivelAcesso(int id, String novoNivelAcesso){
        String sql = "UPDATE Usuario SET nivelAcesso = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, novoNivelAcesso);
            stmt.setInt(2, id);

            stmt.executeUpdate();
            System.out.println("Nivel de acesso do usuário atualizado com sucesso!");
        } catch (SQLException e){
            throw new RuntimeException("Erro ao atualizar o nivel de acesso do usuário: " + e.getMessage());
        }
    }
}
