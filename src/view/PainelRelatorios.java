package view;

import java.awt.*;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.RelatorioController;

public class PainelRelatorios extends JPanel {

    public PainelRelatorios() {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);

        configuraLayout();
    }

    public void configuraLayout() {
        // Container geral com BoxLayout
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        container.setBackground(Color.WHITE);

        // Título
        JPanel linhaTopo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel textRelatorio = new JLabel("Gerador de Relatório");
        textRelatorio.setFont(new Font("Sansserif", Font.BOLD, 22));
        linhaTopo.setBackground(Color.WHITE);
        linhaTopo.add(textRelatorio);
        container.add(linhaTopo);

        // Painel central com botão centralizado
        JPanel centro = new JPanel(new GridBagLayout());
        centro.setBackground(Color.WHITE);
        JButton btnGerar = btnTabel("Gerar Relatório");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        centro.add(btnGerar, gbc);

        // faz o botao funcionar e gerar relatorio
        btnGerar.addActionListener(e -> {
            RelatorioController relatorioController = new RelatorioController();
            String resultado = relatorioController.exportarReservasCSV("relatorio_reservas.csv");

            // Exibe resultado no console ou em um popup
            System.out.println(resultado);
            javax.swing.JOptionPane.showMessageDialog(this, resultado);
        });

        // Adiciona ao painel principal
        container.add(centro);
        this.add(container, BorderLayout.CENTER);
    }

    public JButton btnTabel(String nome) {
            JButton btn = new JButton(nome);
            btn.setFont(new Font("SansSerif", Font.BOLD, 16));
            btn.setForeground(Color.black);
            btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(147, 220, 225), 1, true),
                BorderFactory.createEmptyBorder(7, 20, 7, 20)
            ));
            btn.setFocusPainted(false);
            btn.setBackground(Color.WHITE);
            return btn;
        }
}
