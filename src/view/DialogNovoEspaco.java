package view;

import java.awt.*;
import java.awt.event.ItemEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DialogNovoEspaco extends JDialog{
    private JPanel painelCamposEspecificos;
    private JTextField campoQtdComputadores;  // só aparece se tipo for Laboratório
    private JTextField campoTipoEsporte;      // só aparece se tipo for Quadra
    private JComboBox<String> campoTipo;

    private JTextField campoNome;
    private JTextField campoCapacidade;

    // Campos reutilizáveis:
    private JTextField campoTipoLab;
    private JCheckBox campoPalco;
    private JTextField campoTipoPiso;
    private JTextField campoQtdCarteiras;
    private JTextField campoTipoMesa;


    public DialogNovoEspaco(JFrame parent) {
        super(parent, "Novo Espaço", true);

        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setSize(400, 350);
        this.setLocationRelativeTo(parent); //Centraliza em relação ao pai


        inicializaComponentes();
    }

    public void inicializaComponentes() {
        JPanel painelConteudo = new JPanel();
        painelConteudo.setLayout(new BoxLayout(painelConteudo, BoxLayout.Y_AXIS));
        painelConteudo.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        painelConteudo.setBackground(Color.WHITE);

        campoNome = new JTextField();
        painelConteudo.add(campoComLabel("Nome", campoNome));
        painelConteudo.add(Box.createVerticalStrut(12));

        campoTipo = new JComboBox<>(new String[] {
            "Laboratório", "Auditório", "Quadra", "Sala de Reunião" 
        });

        painelConteudo.add(campoComLabel("Tipo", campoTipo));

        campoTipo.addItemListener(e -> {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            atualizarCamposEspecificos((String) campoTipo.getSelectedItem());
        }
        });



        painelConteudo.add(Box.createVerticalStrut(12));

        campoCapacidade = new JTextField();
        painelConteudo.add(campoComLabel("Capacidade", campoCapacidade));
        painelConteudo.add(Box.createVerticalStrut(20));

        painelCamposEspecificos = new JPanel();
        painelCamposEspecificos.setLayout(new BoxLayout(painelCamposEspecificos, BoxLayout.Y_AXIS));
        painelCamposEspecificos.setBackground(Color.WHITE);
        painelCamposEspecificos.setAlignmentX(Component.LEFT_ALIGNMENT);

        
        painelConteudo.add(painelCamposEspecificos);



        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotoes.setBackground(Color.WHITE);
        painelBotoes.setMaximumSize(new Dimension(400, 50));
        painelBotoes.setAlignmentX(Component.LEFT_ALIGNMENT);

        

        JButton btnSalvar = btnTabel("Salvar");
        btnSalvar.addActionListener(e -> {
            try {
                getEspacoCriado(); // tenta construir objeto (validação implícita)
                dispose(); // fecha se tudo estiver certo
            } catch (Exception ex) {
                ex.printStackTrace();
                javax.swing.JOptionPane.showMessageDialog(this, "Erro: verifique os campos preenchidos.");
            }
        });
        painelBotoes.add(btnSalvar);

        
        painelBotoes.add(btnTabel("Cancelar"));

        painelConteudo.add(Box.createVerticalStrut(10));
        painelConteudo.add(painelBotoes);
        painelConteudo.add(Box.createVerticalStrut(5));

        this.setContentPane(painelConteudo);
        atualizarCamposEspecificos((String) campoTipo.getSelectedItem());

    }

    private JPanel campoComLabel(String labelTexto, JComponent campo) {
        JLabel label = new JLabel(labelTexto);
        label.setFont(new Font("SansSerif", Font.BOLD, 14));
        label.setPreferredSize(new Dimension(90, 30)); // Largura constante p/ alinhar
        label.setMinimumSize(new Dimension(90, 30));
        label.setMaximumSize(new Dimension(90, 30));

        campo.setFont(new Font("SansSerif", Font.PLAIN, 16));
        campo.setPreferredSize(new Dimension(250, 30));
        campo.setMaximumSize(new Dimension(250, 30));

        JPanel linha = new JPanel();
        linha.setLayout(new BoxLayout(linha, BoxLayout.X_AXIS));
        linha.setBackground(Color.WHITE);
        linha.setAlignmentX(Component.LEFT_ALIGNMENT);
        linha.setMaximumSize(new Dimension(400, 40)); // limite de largura total

        linha.add(label);
        linha.add(Box.createHorizontalStrut(12));
        linha.add(campo);

        return linha;
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

    private void atualizarCamposEspecificos(String tipoSelecionado) {
        painelCamposEspecificos.removeAll();

        if (tipoSelecionado.equals("Laboratório")) {
            campoQtdComputadores = new JTextField();
            campoTipoLab = new JTextField();

            painelCamposEspecificos.add(campoComLabel("Qtd. Computadores", campoQtdComputadores));
            painelCamposEspecificos.add(campoComLabel("Tipo do Laboratório", campoTipoLab));

        } else if (tipoSelecionado.equals("Auditório")) {
            campoPalco = new JCheckBox("Possui palco?");
            campoPalco.setBackground(Color.WHITE);
            painelCamposEspecificos.add(campoPalco);

        } else if (tipoSelecionado.equals("Quadra")) {
            campoTipoEsporte = new JTextField();
            campoTipoPiso = new JTextField();

            painelCamposEspecificos.add(campoComLabel("Esporte", campoTipoEsporte));
            painelCamposEspecificos.add(campoComLabel("Tipo de Piso", campoTipoPiso));

        } else if (tipoSelecionado.equals("Sala de Reunião")) {
            campoTipoPiso = new JTextField();
            campoQtdCarteiras = new JTextField();
            campoTipoMesa = new JTextField();

            painelCamposEspecificos.add(campoComLabel("Tipo de Piso", campoTipoPiso));
            painelCamposEspecificos.add(campoComLabel("Qtd. Carteiras", campoQtdCarteiras));
            painelCamposEspecificos.add(campoComLabel("Tipo de Mesa", campoTipoMesa));
        }

        painelCamposEspecificos.revalidate();
        painelCamposEspecificos.repaint();
    }

    public model.Espaco getEspacoCriado() {
        String nome = campoNome.getText().trim();
        int capacidade = Integer.parseInt(campoCapacidade.getText().trim());
        String tipoSelecionado = (String) campoTipo.getSelectedItem();

        return switch (tipoSelecionado) {
            case "Laboratório" -> {
                int qtdComputadores = Integer.parseInt(campoQtdComputadores.getText().trim());
                String tipoLab = campoTipoLab.getText().trim();
                yield new model.Laboratorio(0, capacidade, nome, tipoLab, qtdComputadores);
            }
            case "Auditório" -> {
                boolean possui = campoPalco != null && campoPalco.isSelected();
                yield new model.Auditorio(0, capacidade, nome, possui);
            }
            case "Quadra" -> {
                String esporte = campoTipoEsporte.getText().trim();
                String piso = campoTipoPiso.getText().trim();
                yield new model.Quadra(0, capacidade, nome, esporte, piso);
            }
            case "Sala de Reunião" -> {
                String mesa = campoTipoMesa.getText().trim();
                yield new model.SalaDeReuniao(0, capacidade, nome, mesa);
            }
            default -> throw new IllegalArgumentException("Tipo inválido ou não tratado: " + tipoSelecionado);
        };
    }
}
