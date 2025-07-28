package model;

public class Reserva {
    private int id;
    private int idEspaco;
    private int idUsuario;
    private String data;
    private String horaInicio;
    private String horaFim;

    public Reserva(int idEspaco, int idUsuario, String data, String horaInicio, String horaFim) {
        this.idEspaco = idEspaco;
        this.idUsuario = idUsuario;
        this.data = data;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
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

    public String getData() {
        return data;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public String getHoraFim() {
        return horaFim;
    }

    public void setId(int id) {
        this.id = id;
    }
}
