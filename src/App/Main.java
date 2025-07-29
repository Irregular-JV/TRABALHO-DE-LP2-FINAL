package App;

import view.*;
import model.*;
import controller.*;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities; // Import para boa prática de Swing

import java.net.URL;

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

    public static void setIcon(JFrame frame) {
        try {
            // Usamos Main.class para obter o caminho do recurso em um método estático
            URL iconeURL = Main.class.getResource("/Resources/icon.png");
            
            if (iconeURL != null) {
                ImageIcon icone = new ImageIcon(iconeURL);
                frame.setIconImage(icone.getImage());
            } else {
                System.err.println("Erro: Ícone não encontrado no caminho 'src/resources/icone.png'");
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar o ícone.");
            e.printStackTrace();
        }
    }
}