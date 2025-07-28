package model;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionFactory {

    private static final String url = "jdbc:sqlite:banco.db";
    private static boolean bancoInicializado = false;

    public Connection getConnection(){
        try {
            // Padronizar a conexão com o banco de dados, evitar repetição de código em toda classe DAO
            Connection conn = DriverManager.getConnection(url);
            
            if(!bancoInicializado){
                inicializarBanco(conn);
                bancoInicializado = true;
            }
            
            return conn;
        } catch (SQLException e) {
            throw new RuntimeException("Erro com conexão entre o banco de dados: "+ e.getMessage());
        }
    }

    private void inicializarBanco(Connection conn){
        criarTabelaUsuario(conn);
        criarTabelaEspaco(conn);
    }

    private void criarTabelaUsuario(Connection conn){
        String sql = """
            CREATE TABLE IF NOT EXISTS Usuario (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT NOT NULL,
                senha TEXT NOT NULL,
                nivelAcesso TEXT NOT NULL
            );
        """;

        String sqlVerificarAdmin = "SELECT COUNT(*) AS total FROM Usuario WHERE username = 'admin'";

        String sqlInserirAdmin = "INSERT INTO Usuario (username, senha, nivelAcesso) VALUES ('admin', 'admin', 'admin')";

        try(Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabela 'Usuario' criada.");

            ResultSet rs = stmt.executeQuery(sqlVerificarAdmin);

            if (rs.next() && rs.getInt("total") == 0) {
            
            stmt.execute(sqlInserirAdmin);
            System.out.println("Usuário 'admin' padrão criado com sucesso.");
        }
        } catch (SQLException e){
            throw new RuntimeException("Erro ao criar tabela Usuario: " + e.getMessage());
        }
    }

        private void criarTabelaEspaco(Connection conn) {
        String sql = """
            CREATE TABLE IF NOT EXISTS Espaco (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                capacidade INTEGER NOT NULL,
                localizacao TEXT NOT NULL,
                tipo TEXT NOT NULL,

                -- Laboratorio
                tipoLaboratorio TEXT,
                quantidadeComputadores INTEGER,

                -- Auditorio
                possuiPalco INTEGER,

                -- Quadra
                tipoEsporte TEXT,
                tipoPiso TEXT,

                -- SalaDeAula
                quantidadeCarteiras INTEGER,

                -- SalaDeReuniao
                tipoMesa TEXT
            );
        """;

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabela 'Espaco' verificada/criada.");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar tabela Espaco: " + e.getMessage());
        }
    }
}

