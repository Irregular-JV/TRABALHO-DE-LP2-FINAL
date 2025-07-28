package view;

import model.Reserva;
import model.ReservaDAO;
import org.jdatepicker.impl.*;

import controller.EspacoController;
import controller.ReservaController;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Date;
import java.util.Calendar;
import java.util.List;

public class TelaNovaReserva extends JDialog {
    private int idUsuario;
    private JComboBox<String> comboEspacos;
    private JDatePickerImpl datePicker;
    private JComboBox<String> comboHoraInicio;
    private JComboBox<String> comboHoraFim;
    private Map<String, Integer> mapaEspacos;
    private final Runnable onSuccess;
    private ReservaController reservaController;
    private EspacoController espacoController;

    public TelaNovaReserva(JFrame parent, int idUsuario, Runnable onSuccess) {
        super(parent, "Nova Reserva", true);
        this.idUsuario = idUsuario;
        this.onSuccess = onSuccess;
        this.reservaController = new ReservaController();
        this.espacoController = new EspacoController();
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(6, 2, 10, 10));

        // Simula os espaços com seus respectivos IDs
        mapaEspacos = new HashMap<>();
        List<model.Espaco> espacos = espacoController.listarTodos();
        System.out.println(espacos);
        for (model.Espaco espaco : espacos) {
            String nome = espaco.toString();
            mapaEspacos.put(nome, espaco.getId());
        }


        // Campo espaço
        add(new JLabel("Espaço:"));
        comboEspacos = new JComboBox<>();
        for (String nomeEspaco : mapaEspacos.keySet()) {
            comboEspacos.addItem(nomeEspaco);
        }
        add(comboEspacos);

        // Campo data
        add(new JLabel("Data:"));
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Hoje");
        p.put("text.month", "Mês");
        p.put("text.year", "Ano");

        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        add(datePicker);

        // Campo horário início
        add(new JLabel("Hora Início:"));
        comboHoraInicio = new JComboBox<>();
        add(comboHoraInicio);

        // Campo horário fim
        add(new JLabel("Hora Fim:"));
        comboHoraFim = new JComboBox<>();
        add(comboHoraFim);

        // Botões
        JButton btnReservar = new JButton("Reservar");
        btnReservar.addActionListener(e -> fazerReserva());
        add(btnReservar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());
        add(btnCancelar);

        // Carrega horários padrão
        carregarHorariosPadrao();

        setVisible(true);
    }

    private void carregarHorariosPadrao() {
        comboHoraInicio.removeAllItems();
        comboHoraFim.removeAllItems();

        String[] horarios = {
            "07:30", "08:20", "09:20", "10:10", "11:10", "12:00",
            "13:10", "14:00", "14:50", "15:50", "16:40", "17:30",
            "18:30", "19:20", "20:20", "21:10", "22:00"
        };

        for (String h : horarios) {
            comboHoraInicio.addItem(h);
            comboHoraFim.addItem(h);
        }
    }

    private void fazerReserva() {
        try {
            String nomeEspaco = comboEspacos.getSelectedItem().toString();
            int idEspaco = mapaEspacos.get(nomeEspaco);

            Date dataSelecionada = (Date) datePicker.getModel().getValue();
            if (dataSelecionada == null) {
                JOptionPane.showMessageDialog(this, "Selecione uma data.");
                return;
            }

            String dataFormatada = new SimpleDateFormat("MM-dd").format(dataSelecionada);

            String horaInicio = comboHoraInicio.getSelectedItem().toString().substring(0, 2);
            String horaFim = comboHoraFim.getSelectedItem().toString().substring(0, 2);

            String inicio = dataFormatada + "-" + horaInicio;
            String fim = dataFormatada + "-" + horaFim;

            Reserva reserva = new Reserva(idEspaco, idUsuario, inicio, fim);

            if (reservaController.realizarReserva(reserva)) {
                JOptionPane.showMessageDialog(this, "Reserva realizada com sucesso!");
                dispose();
                onSuccess.run();
            } else {
                JOptionPane.showMessageDialog(this, "Horário indisponível.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    // Formatter necessário para o JDatePicker
    public static class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
        private final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parse(text);
        }

        @Override
        public String valueToString(Object value) {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }
            return "";
        }
    }
}
