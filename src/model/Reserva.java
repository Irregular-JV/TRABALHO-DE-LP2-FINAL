package model;

import java.time.LocalDateTime;

public class Reserva {
    private int id;
    private int idEspaco;
    private int idUsuario;
    private LocalDateTime inicio;
    private LocalDateTime fim;

    public Reserva(int idEspaco, int idUsuario, LocalDateTime inicio, LocalDateTime fim) {
        this.idEspaco = idEspaco;
        this.idUsuario = idUsuario;
        this.inicio = inicio;
        this.fim = fim;
    }

    public int getId() {
        return id;
    }

    public int getIdEspaco() {
        return idEspaco;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public LocalDateTime getInicio() {
        return inicio;
    }

    public LocalDateTime getFim() {
        return fim;
    }

    public void setId(int id) {
        this.id = id;
    }
}
