package view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import model.Usuario;
import model.UsuarioDAO;


public class PainelUsuarios extends JPanel {
    private String nomeUsuarioLogado;
    private JTable tabela;
    JButton botaoRemover = new JButton();
    private DefaultTableModel tableModel;
    private UsuarioDAO usuarioDAO; 

    public PainelUsuarios(String nomeUsuarioLogado) {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);
        this.nomeUsuarioLogado = nomeUsuarioLogado;

        this.usuarioDAO = new UsuarioDAO();

        this.add(configuraTopo(), BorderLayout.NORTH);      // Apenas título
        this.add(ConfigurarTabela(), BorderLayout.CENTER);   // Tabela + botões

        carregarDadosNaTabela();
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

        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };

        this.tabela = new JTable(tableModel);
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

        javax.swing.table.TableColumnModel columnModel = tabela.getColumnModel();

        javax.swing.table.TableColumn idColumn = columnModel.getColumn(0);

        idColumn.setMinWidth(0);
        idColumn.setMaxWidth(0);
        idColumn.setWidth(0);


        tabelaUser.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tabelaUser.add(scrol);

       
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new FlowLayout(FlowLayout.LEFT));
        painelBotoes.setBackground(Color.white);

        if("admin".equals(this.nomeUsuarioLogado)){
            this.botaoRemover = btnTabel("Remover Usuário");
            painelBotoes.add(botaoRemover);
        }

        tabelaUser.add(Box.createVerticalStrut(20)); // espaçamento opcional
        tabelaUser.add(painelBotoes); // adiciona os botões ao painel
        
        return tabelaUser;
    }

    public void addRemoverListener(ActionListener listener) {
        this.botaoRemover.addActionListener(listener);
    }

     public int getIdUsuarioSelecionado() {
        int selectedRow = tabela.getSelectedRow();
        if (selectedRow == -1) {
            return -1; 
        }
        
        return (int) tableModel.getValueAt(selectedRow, 0);
    }

    public void carregarDadosNaTabela() {
        tableModel.setRowCount(0);

        List<Usuario> usuarios = usuarioDAO.listarTodos();

        for (Usuario u : usuarios) {
            tableModel.addRow(new Object[]{
                u.getId(),
                u.getUsername(), 
                u.getNivelAcesso()
            });
        }
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

    public void mostrarMensagem(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem);
    }
}
