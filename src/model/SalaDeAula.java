package model;

public class SalaDeAula extends Espaco{
    public int quantidadeCarteiras;

    public SalaDeAula(int id, int capacidade, String localizacao, int quantidadeCarteiras){
        super(id, capacidade, localizacao);
        this.setQuantidadeCarteiras(quantidadeCarteiras);
    }

    public void setQuantidadeCarteiras(int quantidadeCarteiras){
        this.quantidadeCarteiras = quantidadeCarteiras;
    }

    public int getQuantidadeCarteiras(){
        return this.quantidadeCarteiras;
    }

    @Override
    public String toString() {
        return "Sala de aula" + " - " + getLocalizacao();
    }
}
