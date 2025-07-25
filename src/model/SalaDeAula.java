package model;

public class SalaDeAula extends Espaco{
    public int quantidadeCarteiras;

    public SalaDeAula(int id, int capacidade, String localizacao, boolean disponivel, int quantidadeCarteiras){
        super(id, capacidade, localizacao, disponivel);
        this.setQuantidadeCarteiras(quantidadeCarteiras);
    }

    public void setQuantidadeCarteiras(int quantidadeCarteiras){
        this.quantidadeCarteiras = quantidadeCarteiras;
    }

    public int getQuantidadeCarteiras(){
        return this.quantidadeCarteiras;
    }
}
