package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class EspacoDAO {
    
    private Connection connection;

    public EspacoDAO(){
        this.connection = new ConnectionFactory().getConnection();
    }

    // CRUD

    // salvar no banco de dados
    
    public void salvar(Espaco espaco){
        String sql = "INSERT INTO Espaco (capacidade, localizacao, tipo, tipoLaboratorio, quantidadeComputadores, possuiPalco, tipoEsporte, tipoPiso, quantidadeCarteiras, tipoMesa) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, espaco.getCapacidade());
            stmt.setString(2, espaco.getLocalizacao());
            stmt.setString(3, espaco.getClass().getSimpleName());

            if(espaco instanceof Laboratorio lab){
                stmt.setString(4, lab.getTipoLaboratorio());
                stmt.setInt(5, lab.getQuantidadeComputadores());
            } else {
                stmt.setNull(4, java.sql.Types.VARCHAR);
                stmt.setNull(5, java.sql.Types.INTEGER);
            }

            if(espaco instanceof Auditorio aud){
                stmt.setInt(6, aud.getPossuiPalco() ? 1 : 0);
            } else {
                stmt.setNull(6, java.sql.Types.INTEGER);
            }

            if(espaco instanceof Quadra quadra){
                stmt.setString(7, quadra.getTipoEsporte());
                stmt.setString(8, quadra.getTipoPiso());
            } else {
                stmt.setNull(7, java.sql.Types.VARCHAR);
                stmt.setNull(8, java.sql.Types.VARCHAR);
            }

            if(espaco instanceof SalaDeAula sda){
                stmt.setInt(9, sda.getQuantidadeCarteiras());
            } else {
                stmt.setNull(9, java.sql.Types.INTEGER);
            }

            if(espaco instanceof SalaDeReuniao sdr){
                stmt.setString(10, sdr.getTipoMesa());
            } else {
                stmt.setNull(10, java.sql.Types.VARCHAR);
            }

            stmt.executeUpdate();
            System.out.println("Espaco salvo com sucesso!");

        } catch (SQLException e){
            throw new RuntimeException("Erro ao salvar espaco: " + e.getMessage());
        }
    }   

    public void deletar(int id){
        String sql =  "DELETE FROM Espaco WHERE id = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, id);

            stmt.executeUpdate();
            System.out.println("Espaco deletado com sucesso!");
        } catch (SQLException e){
            throw new RuntimeException("Erro ao deletar espaco: " + e.getMessage());
        }
    }

    public void atualizar(Espaco espaco) {
        String sql = """
            UPDATE Espaco SET 
                capacidade = ?, 
                localizacao = ?, 
                tipo = ?, 
                tipoLaboratorio = ?, 
                quantidadeComputadores = ?, 
                possuiPalco = ?, 
                tipoEsporte = ?, 
                tipoPiso = ?, 
                quantidadeCarteiras = ?, 
                tipoMesa = ?
            WHERE id = ?
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, espaco.getCapacidade());
            stmt.setString(2, espaco.getLocalizacao());
            stmt.setString(3, espaco.getClass().getSimpleName());

            if (espaco instanceof Laboratorio lab) {
                stmt.setString(4, lab.getTipoLaboratorio());
                stmt.setInt(5, lab.getQuantidadeComputadores());
            } else {
                stmt.setNull(4, java.sql.Types.VARCHAR);
                stmt.setNull(5, java.sql.Types.INTEGER);
            }

            if (espaco instanceof Auditorio aud) {
                stmt.setInt(6, aud.getPossuiPalco() ? 1 : 0);
            } else {
                stmt.setNull(6, java.sql.Types.INTEGER);
            }

            if (espaco instanceof Quadra quadra) {
                stmt.setString(7, quadra.getTipoEsporte());
                stmt.setString(8, quadra.getTipoPiso());
            } else {
                stmt.setNull(7, java.sql.Types.VARCHAR);
                stmt.setNull(8, java.sql.Types.VARCHAR);
            }

            if (espaco instanceof SalaDeAula sda) {
                stmt.setInt(9, sda.getQuantidadeCarteiras());
            } else {
                stmt.setNull(9, java.sql.Types.INTEGER);
            }

            if (espaco instanceof SalaDeReuniao sdr) {
                stmt.setString(10, sdr.getTipoMesa());
            } else {
                stmt.setNull(10, java.sql.Types.VARCHAR);
            }

            stmt.setInt(11, espaco.getId());

            stmt.executeUpdate();
            System.out.println("Espaco atualizado com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar espaco: " + e.getMessage());
        }
    }

    public List<Espaco> listarTodos(){
        String sql = "SELECT * FROM Espaco";
        List<Espaco> espacos = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                int capacidade = rs.getInt("capacidade");
                String localizacao = rs.getString("localizacao");
                String tipo = rs.getString("tipo");

                switch (tipo) {
                    case "Laboratorio" -> {
                        String tipoLaboratorio = rs.getString("tipoLaboratorio");
                        int quantidadeComputadores = rs.getInt("quantidadeComputadores");
                        espacos.add(new Laboratorio(id, capacidade, localizacao, tipoLaboratorio, quantidadeComputadores));
                    }
                    case "Auditorio" -> {
                        boolean possuiPalco = rs.getInt("possuiPalco") == 1;
                        espacos.add(new Auditorio(id, capacidade, localizacao, possuiPalco));
                    }
                    case "Quadra" -> {
                        String tipoEsporte = rs.getString("tipoEsporte");
                        String tipoPiso = rs.getString("tipoPiso");
                        espacos.add(new Quadra(id, capacidade, localizacao, tipoEsporte, tipoPiso));
                    }
                    case "SalaDeAula" -> {
                        int quantidadeCarteiras = rs.getInt("quantidadeCarteiras");
                        espacos.add(new SalaDeAula(id, capacidade, localizacao, quantidadeCarteiras));
                    }
                    case "SalaDeReuniao" -> {
                        String tipoMesa = rs.getString("tipoMesa");
                        espacos.add(new SalaDeReuniao(id, capacidade, localizacao, tipoMesa));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar espaços: " + e.getMessage());
        }

        return espacos;
    }

    public Espaco buscarPorId(int id){
        String sql = "SELECT * FROM Espaco WHERE id = ?";
        Espaco espaco = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try(ResultSet rs = stmt.executeQuery()){
                if (rs.next()) {
                    int foundId = rs.getInt("id");
                    int capacidade = rs.getInt("capacidade");
                    String localizacao = rs.getString("localizacao");
                    String tipo = rs.getString("tipo"); 

                    switch (tipo) {
                        case "Laboratorio" -> {
                            String tipoLaboratorio = rs.getString("tipoLaboratorio");
                            int quantidadeComputadores = rs.getInt("quantidadeComputadores");
                            espaco = new Laboratorio(foundId, capacidade, localizacao, tipoLaboratorio, quantidadeComputadores);
                        }
                        case "Auditorio" -> {
                            boolean possuiPalco = rs.getInt("possuiPalco") == 1;
                            espaco = new Auditorio(foundId, capacidade, localizacao, possuiPalco);
                        }
                        case "Quadra" -> {
                            String tipoEsporte = rs.getString("tipoEsporte");
                            String tipoPiso = rs.getString("tipoPiso");
                            espaco = new Quadra(foundId, capacidade, localizacao, tipoEsporte, tipoPiso);
                        }
                        case "SalaDeAula" -> {
                            int quantidadeCarteiras = rs.getInt("quantidadeCarteiras");
                            espaco = new SalaDeAula(foundId, capacidade, localizacao, quantidadeCarteiras);
                        }
                        case "SalaDeReuniao" -> {
                            String tipoMesa = rs.getString("tipoMesa");
                            espaco = new SalaDeReuniao(foundId, capacidade, localizacao, tipoMesa);
                        }
                    }
                }
            }
        } catch (SQLException e){
            throw new RuntimeException("Erro ao encontrar espaco pelo id: " + e.getMessage());
        }

        if (espaco != null)
            System.out.println("Espaco encontrado com sucesso!");
        else
            System.out.println("Espaco não encontrado.");

        return espaco;
    }

    public List<Espaco> buscarPorTipo(String tipoDesejado) {
        String sql = "SELECT * FROM Espaco WHERE tipo = ?";
        List<Espaco> espacos = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, tipoDesejado);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int capacidade = rs.getInt("capacidade");
                    String localizacao = rs.getString("localizacao");
                    String tipo = rs.getString("tipo");

                    switch (tipo) {
                        case "Laboratorio" -> {
                            String tipoLaboratorio = rs.getString("tipoLaboratorio");
                            int quantidadeComputadores = rs.getInt("quantidadeComputadores");
                            espacos.add(new Laboratorio(id, capacidade, localizacao, tipoLaboratorio, quantidadeComputadores));
                        }
                        case "Auditorio" -> {
                            boolean possuiPalco = rs.getInt("possuiPalco") == 1;
                            espacos.add(new Auditorio(id, capacidade, localizacao, possuiPalco));
                        }
                        case "Quadra" -> {
                            String tipoEsporte = rs.getString("tipoEsporte");
                            String tipoPiso = rs.getString("tipoPiso");
                            espacos.add(new Quadra(id, capacidade, localizacao, tipoEsporte, tipoPiso));
                        }
                        case "SalaDeAula" -> {
                            int quantidadeCarteiras = rs.getInt("quantidadeCarteiras");
                            espacos.add(new SalaDeAula(id, capacidade, localizacao, quantidadeCarteiras));
                        }
                        case "SalaDeReuniao" -> {
                            String tipoMesa = rs.getString("tipoMesa");
                            espacos.add(new SalaDeReuniao(id, capacidade, localizacao, tipoMesa));
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar por tipo: " + e.getMessage());
        }

        return espacos;
    }

    public List<Espaco> buscarPorLocal(String termo) {
        String sql = "SELECT * FROM Espaco WHERE localizacao LIKE ?";
        List<Espaco> espacos = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + termo + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int capacidade = rs.getInt("capacidade");
                    String localizacao = rs.getString("localizacao");
                    String tipo = rs.getString("tipo");

                    switch (tipo) {
                        case "Laboratorio" -> {
                            String tipoLaboratorio = rs.getString("tipoLaboratorio");
                            int quantidadeComputadores = rs.getInt("quantidadeComputadores");
                            espacos.add(new Laboratorio(id, capacidade, localizacao, tipoLaboratorio, quantidadeComputadores));
                        }
                        case "Auditorio" -> {
                            boolean possuiPalco = rs.getInt("possuiPalco") == 1;
                            espacos.add(new Auditorio(id, capacidade, localizacao, possuiPalco));
                        }
                        case "Quadra" -> {
                            String tipoEsporte = rs.getString("tipoEsporte");
                            String tipoPiso = rs.getString("tipoPiso");
                            espacos.add(new Quadra(id, capacidade, localizacao, tipoEsporte, tipoPiso));
                        }
                        case "SalaDeAula" -> {
                            int quantidadeCarteiras = rs.getInt("quantidadeCarteiras");
                            espacos.add(new SalaDeAula(id, capacidade, localizacao, quantidadeCarteiras));
                        }
                        case "SalaDeReuniao" -> {
                            String tipoMesa = rs.getString("tipoMesa");
                            espacos.add(new SalaDeReuniao(id, capacidade, localizacao, tipoMesa));
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar por localizacao: " + e.getMessage());
        }

        return espacos;
    }



}
