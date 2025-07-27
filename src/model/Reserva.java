package model;

import java.time.LocalDateTime;

public class Reserva {
    private int id;
    private int idEspaco;
    private int idUsuario;
    private String inicio;
    private String fim;

    public Reserva(int idEspaco, int idUsuario, String inicio, String fim) {
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

    public String getInicio() {
        return inicio;
    }

    public String getFim() {
        return fim;
    }

    public void setId(int id) {
        this.id = id;
    }
}
