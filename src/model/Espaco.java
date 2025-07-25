package model;

public abstract class Espaco {
    public int id;
    public int capacidade;
    public String localizacao;
    public boolean disponivel;

    public Espaco(int id, int capacidade, String localizacao, boolean disponivel){
        this.setId(id);
        this.setCapacidade(capacidade);
        this.setLocalizacao(localizacao);
        this.setDisponivel(disponivel);
    }

    public void setId(int id){
        this.id = id;
    }

    public void setCapacidade(int capacidade){
        this.capacidade = capacidade;
    }

    public void setLocalizacao(String localizacao){
        this.localizacao = localizacao;
    }

    public void setDisponivel(boolean disponivel){
        this.disponivel = disponivel;
    }

    public int getId(){
        return this.id;
    }

    public int getCapacidade(){
        return this.capacidade;
    }

    public String getLocalizacao(){
        return this.localizacao;
    }

    public boolean getDisponivel(){
        return this.disponivel;
    }
}
