package model;

public class SalaDeReuniao extends Espaco{
    public String tipoMesa;

    public SalaDeReuniao(int id, int capacidade, String localizacao, String tipoMesa){
        super(id, capacidade, localizacao);
        this.setTipoMesa(tipoMesa);
    }

    public void setTipoMesa(String tipoMesa){
        this.tipoMesa = tipoMesa;
    }

    public String getTipoMesa(){
        return this.tipoMesa;
    }
}
