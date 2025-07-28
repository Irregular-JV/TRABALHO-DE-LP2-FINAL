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
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;

public class PainelGerenciarEspacos extends JPanel  {
    public PainelGerenciarEspacos() {
      this.setLayout(new BorderLayout());
      this.setBackground(Color.WHITE);

      configurarTopo();
      ConfigurarTabela();
      configurarRodape();
    }


    public void configurarTopo(){
        JPanel painelTopo = new JPanel();
        painelTopo.setLayout(new BoxLayout(painelTopo, BoxLayout.Y_AXIS));

        painelTopo.setBackground(Color.WHITE);
        painelTopo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel linhaBusca = new JPanel();
        linhaBusca.setLayout(new BoxLayout(linhaBusca, BoxLayout.X_AXIS));
        linhaBusca.setBackground(Color.WHITE); // manter o fundo
        linhaBusca.setAlignmentX(LEFT_ALIGNMENT);


        JLabel buscaMsg = new JLabel("Buscar por nome ou id");
        buscaMsg.setFont(new Font("SansSerif", Font.BOLD, 18));
        buscaMsg.setAlignmentX(LEFT_ALIGNMENT);

        JTextField campoBusca = new JTextField(20);
        JButton btnBuscar = new JButton("Buscar");

        btnBuscar.setFocusPainted(false);
        btnBuscar.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnBuscar.setForeground(Color.black);
        btnBuscar.setBackground(Color.WHITE);
        btnBuscar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.black, 1, true),
            BorderFactory.createEmptyBorder(5, 30, 5, 30)
        ));



        campoBusca.setMaximumSize(new java.awt.Dimension(500, 105));
        linhaBusca.add(campoBusca);
        linhaBusca.add(Box.createHorizontalStrut(15));
        linhaBusca.add(btnBuscar);

        painelTopo.add(buscaMsg);
        painelTopo.add(Box.createVerticalStrut(5)); // espaçamento
        painelTopo.add(linhaBusca);

        this.add(painelTopo, BorderLayout.NORTH);
    }

    public void ConfigurarTabela() {
        //Modelo para visualização
        String[] colunas = { "ID", "Nome", "Tipo", "Capacidade"};
        Object[][] dados = {
            { 1, "Sala 101", "Laboratório", 30},
            { 2, "Auditório", "Auditório", 100}
        };

        JTable tabela = new JTable(dados, colunas);
        JScrollPane scrol = new JScrollPane(tabela);

        JPanel tabelaEspaco = new JPanel();
        tabelaEspaco.setBackground(Color.WHITE);
        tabelaEspaco.setLayout(new BoxLayout(tabelaEspaco, BoxLayout.Y_AXIS));
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


        tabelaEspaco.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tabelaEspaco.add(scrol);

       
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new FlowLayout(FlowLayout.LEFT));
        painelBotoes.setBackground(Color.white);

        painelBotoes.add(btnTabel("Novo Espaco"));
        painelBotoes.add(btnTabel("Editar Espaço"));
        painelBotoes.add(btnTabel("Remover Espaço"));

        tabelaEspaco.add(Box.createVerticalStrut(20)); // espaçamento opcional
        tabelaEspaco.add(painelBotoes); // adiciona os botões ao painel

        this.add(tabelaEspaco, BorderLayout.CENTER);
    }

    public void configurarRodape() {
        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rodape.setBackground(Color.WHITE);
        rodape.setBorder((BorderFactory.createEmptyBorder(10, 20, 10, 20)));

        JLabel labelInfo = new JLabel("2 espaços encontrados");
        rodape.add(labelInfo);

        this.add(rodape, BorderLayout.SOUTH);
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