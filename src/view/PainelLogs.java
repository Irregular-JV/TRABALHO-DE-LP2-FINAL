package view;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.*;

public class PainelLogs extends JPanel {
    private JTextArea logArea;
    private Path logPath = Paths.get("docs/log.txt");

    public PainelLogs() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(logArea);

        this.add(scrollPane, BorderLayout.CENTER);
    }

    public void atualizarLog() {
        try {
            if (Files.exists(logPath)) {
                logArea.setText(Files.readString(logPath));
                logArea.setCaretPosition(logArea.getDocument().getLength()); // rola pro fim
            } else {
                logArea.setText("Arquivo de log ainda não existe.");
            }
        } catch (IOException e) {
            logArea.setText("Erro ao ler log: " + e.getMessage());
        }
    }
}
