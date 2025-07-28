package view;

import java.awt.*;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


public class PainelUsuarios extends JPanel {
    public PainelUsuarios() {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);

        this.add(configuraTopo(), BorderLayout.NORTH);      // Apenas título
        this.add(ConfigurarTabela(), BorderLayout.CENTER);   // Tabela + botões
    }

    public JPanel configuraTopo() {
        //Engloba tudo
        JPanel painelTopo = new JPanel();
        painelTopo.setLayout(new BoxLayout(painelTopo, BoxLayout.Y_AXIS));
        painelTopo.setBackground(Color.WHITE);
        painelTopo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        JPanel linhaText = new JPanel();
        linhaText.setLayout(new FlowLayout(FlowLayout.LEFT));
        linhaText.setBackground(Color.WHITE); // manter o fundo


        JLabel user = new JLabel("Usúarios");
        user.setFont(new Font("SansSerif", Font.BOLD, 18));
        user.setAlignmentX(LEFT_ALIGNMENT);


        linhaText.add(user);

        painelTopo.add(linhaText);
        return painelTopo;
    }

    public JPanel ConfigurarTabela() {
        //Modelo para visualização
        String[] colunas = { "ID", "Nome de Usuário", "Nivel de Acesso"};
        Object[][] dados = {
        };

        JTable tabela = new JTable(dados, colunas);
        JScrollPane scrol = new JScrollPane(tabela);

        JPanel tabelaUser = new JPanel();
        tabelaUser.setBackground(Color.WHITE);
        tabelaUser.setLayout(new BoxLayout(tabelaUser, BoxLayout.Y_AXIS));
        scrol.getViewport().setBackground(Color.WHITE);

        tabela.setRowHeight(30);
        tabela.setFont(new Font("SansSerif", Font.PLAIN, 16));


        tabela.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 16));
        tabela.getTableHeader().setReorderingAllowed(false); // impedir mover colunas

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tabela.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); // ID

        tabela.setShowGrid(false);
        tabela.setIntercellSpacing(new Dimension(0, 0));

        tabela.setBackground(Color.WHITE);
        tabela.getTableHeader().setBackground(Color.WHITE);


        tabelaUser.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tabelaUser.add(scrol);

       
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new FlowLayout(FlowLayout.LEFT));
        painelBotoes.setBackground(Color.white);

        painelBotoes.add(btnTabel("Novo Usuário"));
        painelBotoes.add(btnTabel("Remover Usuário"));

        tabelaUser.add(Box.createVerticalStrut(20)); // espaçamento opcional
        tabelaUser.add(painelBotoes); // adiciona os botões ao painel
        
        return tabelaUser;
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
