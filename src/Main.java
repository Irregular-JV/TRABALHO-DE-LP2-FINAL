
import view.*;
import model.*;
import controller.*;
import javax.swing.SwingUtilities; // Import para boa prática de Swing

public class Main {
    public static void main(String[] args) {
        // Garante que a interface gráfica seja criada na thread correta do Swing
        SwingUtilities.invokeLater(() -> {
            UsuarioDAO usuarioDAO = new UsuarioDAO();

            TelaLogin loginView = new TelaLogin();

            EntrarController loginController = new EntrarController(loginView, usuarioDAO);

            loginView.setVisible(true);
        });
    }
}