package model;

public class SalaDeReuniao extends Espaco{
    public String tipoMesa;

    public SalaDeReuniao(int id, int capacidade, String localizacao, boolean disponivel, String tipoMesa){
        super(id, capacidade, localizacao, disponivel);
        this.setTipoMesa(tipoMesa);
    }

    public void setTipoMesa(String tipoMesa){
        this.tipoMesa = tipoMesa;
    }

    public String getTipoMesa(){
        return this.tipoMesa;
    }
}
