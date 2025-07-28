package controller;

import model.Reserva;
import model.ReservaDAO;
import model.UsuarioDAO;
import model.Espaco;
import java.io.*;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RelatorioController {
    private ReservaDAO reservaDAO;
    private UsuarioDAO usuarioDAO;

    public RelatorioController(){
        this.reservaDAO = new ReservaDAO();
        this.usuarioDAO = new UsuarioDAO();
    }

    public static void registrarLog(String mensagem){
        File dir = new File("docs");
        if(!dir.exists()) dir.mkdirs();
        try(FileWriter writer = new FileWriter("docs/log.txt", true)){
            // String timestamp = java.time.LocalTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
            writer.write("[" + timestamp + "] " + mensagem + "\n");
        } catch (IOException e){
            System.err.println("Erro ao registrar log: " + e.getMessage());
        }
    }

    // Gerar comprovante txt da Reserva
    public String gerarComprovanteReserva(Reserva reserva) {
        String nomeArquivo = "docs/comprovante_reserva_" + reserva.getId() + ".txt";

        try (PrintWriter writer = new PrintWriter(nomeArquivo)) {
            writer.println("=== Comprovante de Reserva ===");
            writer.println("ID da Reserva: " + reserva.getId());
            writer.println("Espaço ID: " + reserva.getIdEspaco());
            writer.println("Usuário ID: " + reserva.getIdUsuario());
            writer.println("Data: " + reserva.getData());
            writer.println("Início: " + reserva.getHoraFim());
            writer.println("Fim: " + reserva.getHoraFim());
            return "Comprovante gerado: " + nomeArquivo;
        } catch (IOException e) {
            return "Erro ao gerar comprovante: " + e.getMessage();
        }
    }

    // Exportar reservas para CSV
    public String exportarReservasCSV(String nomeArquivo) {
        List<Reserva> reservas = reservaDAO.listar();

        String caminho = "docs/" + nomeArquivo;

        try (PrintWriter writer = new PrintWriter(caminho)) {
            writer.println("ID,EspacoID,UsuarioID,Data,Inicio,Fim");
            for (Reserva r : reservas) {
                writer.printf("%d,%d,%d,%s,%s,%s%n", r.getId(), r.getIdEspaco(), r.getIdUsuario(), r.getData(), r.getHoraInicio(), r.getHoraFim());
            }
            return "Arquivo CSV gerado com sucesso: " + caminho;
        } catch (IOException e) {
            return "Erro ao exportar CSV: " + e.getMessage();
        }
    }

    public int totalReservas() {
        return reservaDAO.listar().size();
    }
}
