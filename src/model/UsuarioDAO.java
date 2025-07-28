package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import controller.RelatorioController;

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
            String msg = "Usuario salvo com sucesso!";
            RelatorioController.registrarLog(msg);
            System.out.println(msg);
        } catch (SQLException e){
            String msg = "Erro ao salvar dados do usuário" + e.getMessage();
            RelatorioController.registrarLog(msg);
            throw new RuntimeException(msg);
        }
    }

    // Deletar usuário do banco de dados

    public void deletar(int id){
        String username = "(desconhecido)"; // valor padrão, caso algo dê errado

        try {
            // Primeiro: recuperar username
            String sqlSelect = "SELECT username FROM Usuario WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sqlSelect)) {
                stmt.setInt(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        username = rs.getString("username");
                    }
                }
            }

            // Segundo: deletar usuário
            String sqlDelete = "DELETE FROM Usuario WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sqlDelete)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }

            String msg_delete = "Usuario " + username + " deletado com sucesso!";
            RelatorioController.registrarLog(msg_delete);
            System.out.println(msg_delete);

        } catch (SQLException e){
            String msg_delete = "Erro ao deletar usuário: " + e.getMessage();
            RelatorioController.registrarLog(msg_delete);
            throw new RuntimeException(msg_delete);
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
            String msg_listagem = "Erro ao listar usuários" + e.getMessage();
            RelatorioController.registrarLog(msg_listagem);
            throw new RuntimeException(msg_listagem);
        }

        String msg_listagem = "Listagem de usuarios feita com sucesso!";
        RelatorioController.registrarLog(msg_listagem); 
        System.out.println(msg_listagem);
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
            String msg_busca = "Erro ao encontrar usuario pelo username: " + e.getMessage();
            RelatorioController.registrarLog(msg_busca);
            throw new RuntimeException(msg_busca);
        }
        String msg_busca = "Usuário encontrado com sucesso!";
        RelatorioController.registrarLog(msg_busca);
        System.out.println(msg_busca);
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
            String msg_busca = "Erro ao encontrar usuario pelo id: " + e.getMessage();
            RelatorioController.registrarLog(msg_busca);
            throw new RuntimeException(msg_busca);
        }
        String msg_busca = "Usuário encontrado com sucesso!";
        RelatorioController.registrarLog(msg_busca);
        System.out.println(msg_busca);
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
            String msg_update = "Senha do usuário de id " + id + " atualizada com sucesso!";
            RelatorioController.registrarLog(msg_update);
            System.out.println(msg_update);
        } catch (SQLException e){
            String msg_update = "Erro ao atualizar senha do usuário: " + e.getMessage();
            RelatorioController.registrarLog(msg_update);
            throw new RuntimeException(msg_update);
        }
    }

    // Update de username, caso usuário queira trocá-lo

    public void atualizarUsername(int id, String novoUsername){
        String sql = "UPDATE Usuario SET username = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, novoUsername);
            stmt.setInt(2, id);

            stmt.executeUpdate();
            String msg_update = "Username do usuário de id " + id + " atualizada com sucesso!";
            RelatorioController.registrarLog(msg_update);
            System.out.println(msg_update);
        } catch (SQLException e){
            String msg_update = "Erro ao atualizar username do usuário: " + e.getMessage();
            RelatorioController.registrarLog(msg_update);
            throw new RuntimeException(msg_update);
        }
    }

    // Update de nível de acesso, caso haja a necessidade

    public void atualizarNivelAcesso(int id, String novoNivelAcesso){
        String sql = "UPDATE Usuario SET nivelAcesso = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, novoNivelAcesso);
            stmt.setInt(2, id);

            stmt.executeUpdate();
            String msg_update = "Nivel de acesso do usuário de id " + id + " atualizado com sucesso!";
            RelatorioController.registrarLog(msg_update);
            System.out.println(msg_update);
        } catch (SQLException e){
            String msg_update = "Erro ao atualizar o nivel de acesso do usuário: " + e.getMessage();
            RelatorioController.registrarLog(msg_update);
            throw new RuntimeException(msg_update);
        }
    }
}
