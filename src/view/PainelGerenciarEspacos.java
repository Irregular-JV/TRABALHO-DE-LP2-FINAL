package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import controller.EspacoController;


public class PainelGerenciarEspacos extends JPanel {
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JLabel labelInfo;
    private controller.EspacoController controller;
    private java.util.List<model.Espaco> listaEspacos = new java.util.ArrayList<>();
    private String nome;

    // Construtor vazio
    public PainelGerenciarEspacos(String nome) {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);
        this.nome = nome;
    }

    // Método para associar o controller e configurar a interface
    public void setController(controller.EspacoController controller) {
        this.controller = controller;
        configurarTopo();
        ConfigurarTabela();
        configurarRodape();
    }

    // Método de fábrica que cria o painel com controller integrado
    public static PainelGerenciarEspacos criarComController(String nome) {
        PainelGerenciarEspacos painel = new PainelGerenciarEspacos(nome);
        controller.EspacoController controller = new controller.EspacoController(painel);
        painel.setController(controller);
        return painel;
    }

    public void configurarTopo() {
        JPanel painelTopo = new JPanel();
        painelTopo.setLayout(new BoxLayout(painelTopo, BoxLayout.Y_AXIS));
        painelTopo.setBackground(Color.WHITE);
        painelTopo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel linhaBusca = new JPanel();
        linhaBusca.setLayout(new BoxLayout(linhaBusca, BoxLayout.X_AXIS));
        linhaBusca.setBackground(Color.WHITE);
        linhaBusca.setAlignmentX(LEFT_ALIGNMENT);

        JLabel buscaMsg = new JLabel("Gerenciar Espaços");
        buscaMsg.setFont(new Font("SansSerif", Font.BOLD, 22));
        buscaMsg.setAlignmentX(LEFT_ALIGNMENT);

        // JTextField campoBusca = new JTextField(20);
        // JButton btnBuscar = new JButton("Buscar");
        // btnBuscar.setFocusPainted(false);
        // btnBuscar.setFont(new Font("SansSerif", Font.BOLD, 16));
        // btnBuscar.setForeground(Color.black);
        // btnBuscar.setBackground(Color.WHITE);
        // btnBuscar.setBorder(BorderFactory.createCompoundBorder(
        //     BorderFactory.createLineBorder(Color.black, 1, true),
        //     BorderFactory.createEmptyBorder(5, 30, 5, 30)
        // ));

        // campoBusca.setMaximumSize(new Dimension(500, 105));
        // linhaBusca.add(campoBusca);
        // linhaBusca.add(Box.createHorizontalStrut(15));
        // linhaBusca.add(btnBuscar);

        painelTopo.add(buscaMsg);
        painelTopo.add(Box.createVerticalStrut(5));
        painelTopo.add(linhaBusca);
        this.add(painelTopo, BorderLayout.NORTH);
    }

    public void ConfigurarTabela() {
        modeloTabela = new DefaultTableModel(new String[]{"Nome", "Tipo", "Capacidade"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabela = new JTable(modeloTabela);
        JScrollPane scrol = new JScrollPane(tabela);

        JPanel tabelaEspaco = new JPanel();
        tabelaEspaco.setBackground(Color.WHITE);
        tabelaEspaco.setLayout(new BoxLayout(tabelaEspaco, BoxLayout.Y_AXIS));
        scrol.getViewport().setBackground(Color.WHITE);

        tabela.setRowHeight(30);
        tabela.setFont(new Font("SansSerif", Font.PLAIN, 16));
        tabela.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 16));
        tabela.getTableHeader().setReorderingAllowed(false);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tabela.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);

        tabela.setShowGrid(false);
        tabela.setIntercellSpacing(new Dimension(0, 0));
        tabela.setBackground(Color.WHITE);
        tabela.getTableHeader().setBackground(Color.WHITE);

        tabelaEspaco.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tabelaEspaco.add(scrol);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelBotoes.setBackground(Color.white);


        if(nome.equals("admin")) {
            JButton btnNovoEspaco = btnTabel("Novo Espaco");
            btnNovoEspaco.setActionCommand("novo_espaco");
            btnNovoEspaco.addActionListener(controller);
            painelBotoes.add(btnNovoEspaco);
            
            JButton btnRemover = btnTabel("Remover Espaço");
            btnRemover.setActionCommand("remover_espaco");
            btnRemover.addActionListener(controller);
            painelBotoes.add(btnRemover);

            tabelaEspaco.add(Box.createVerticalStrut(20));
            tabelaEspaco.add(painelBotoes);
        }

        this.add(tabelaEspaco, BorderLayout.CENTER);

        recarregarTabela();

        tabela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2 && tabela.getSelectedRow() != -1) {
                    int row = tabela.getSelectedRow();
                    model.Espaco espacoSelecionado = listaEspacos.get(row);
                    mostrarDetalhesEspaco(espacoSelecionado);
                }
            }
        });
    }

    public void configurarRodape() {
        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rodape.setBackground(Color.WHITE);
        rodape.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        labelInfo = new JLabel(modeloTabela.getRowCount() + " espaços encontrados");
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

    public void recarregarTabela() {
        modeloTabela.setRowCount(0);
        listaEspacos = controller.listarTodos();

        for (model.Espaco esp : listaEspacos) {
            modeloTabela.addRow(new Object[]{
                esp.getLocalizacao(),
                esp.getClass().getSimpleName(),
                esp.getCapacidade()
            });
        }

        if (labelInfo != null) {
            labelInfo.setText(listaEspacos.size() + " espaços encontrados");
        }
    }

    public void mostrarDetalhesEspaco(model.Espaco espaco) {
        StringBuilder detalhes = new StringBuilder();
        detalhes.append("Nome: ").append(espaco.getLocalizacao()).append("\n");
        detalhes.append("Capacidade: ").append(espaco.getCapacidade()).append("\n");
        detalhes.append("Tipo: ").append(espaco.getTipo()).append("\n");

        if (espaco instanceof model.Laboratorio lab) {
            detalhes.append("Tipo do Laboratório: ").append(lab.getTipo()).append("\n");
            detalhes.append("Qtd. Computadores: ").append(lab.getQuantidadeComputadores()).append("\n");
        } else if (espaco instanceof model.Auditorio aud) {
            detalhes.append("Possui Palco: ").append(aud.getPossuiPalco() ? "Sim" : "Não").append("\n");
        } else if (espaco instanceof model.Quadra quadra) {
            detalhes.append("Esporte: ").append(quadra.getTipoEsporte()).append("\n");
            detalhes.append("Tipo de Piso: ").append(quadra.getTipoPiso()).append("\n");
        } else if (espaco instanceof model.SalaDeReuniao sala) {
            detalhes.append("Tipo de Mesa: ").append(sala.getTipoMesa()).append("\n");
        } else if(espaco instanceof model.SalaDeAula sala) {
            detalhes.append("QTD.Carteiras: ").append(sala.getQuantidadeCarteiras()).append("\n");
        }

        JOptionPane.showMessageDialog(this, detalhes.toString(),
            "Detalhes do Espaço", JOptionPane.INFORMATION_MESSAGE);
    }

    public JTable getTabela() {
        return tabela;
    }

    public int getEspacoSelecionadoId(int linha) {
        return listaEspacos.get(linha).getId();
    }

    public EspacoController getController() {
        return controller;
    }

}
