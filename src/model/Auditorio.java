package model;

public class Auditorio extends Espaco {
    public boolean possuiPalco;

    public Auditorio(int id, int capacidade, String localizacao, boolean possuiPalco){
        super(id, capacidade, localizacao);
        this.setPossuiPalco(possuiPalco);
    }

    public void setPossuiPalco(boolean possuiPalco){
        this.possuiPalco = possuiPalco;
    }

    public boolean getPossuiPalco(){
        return this.possuiPalco;
    }

    @Override
    public String toString() {
        return "Auditório" + " - " + getLocalizacao();
    }
}
