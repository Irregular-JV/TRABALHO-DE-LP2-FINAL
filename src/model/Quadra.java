package model;

public class Quadra extends Espaco {
    public String tipoEsporte;
    public String tipoPiso;

    public Quadra(int id, int capacidade, String localizacao, String tipoEsporte, String tipoPiso){
        super(id, capacidade, localizacao);
        this.setTipoEsporte(tipoEsporte);
        this.setTipoPiso(tipoPiso);
    }

    public void setTipoEsporte(String tipoEsporte){
        this.tipoEsporte = tipoEsporte;
    }

    public void setTipoPiso(String tipoPiso){
        this.tipoPiso = tipoPiso;
    }

    public String getTipoEsporte(){
        return this.tipoEsporte;
    }

    public String getTipoPiso(){
        return this.tipoPiso;
    }
}
