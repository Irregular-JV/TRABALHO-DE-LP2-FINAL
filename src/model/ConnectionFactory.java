package model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    public Connection getConnection(){
        try {
            String url = "jdbc:sqlite:../banco.db";
            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new RuntimeException("Erro com conexão entre o banco de dados: "+ e.getMessage());
        }
    }
}
