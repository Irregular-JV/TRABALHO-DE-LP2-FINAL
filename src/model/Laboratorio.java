package model;

public class Laboratorio extends Espaco{
    public String tipoLaboratorio;
    public int quantidadeComputadores;

    public Laboratorio(int id, int capacidade, String localizacao, boolean disponivel, String tipoLaboratorio, int quantidadeComputadores){
        super(id, capacidade, localizacao, disponivel);
        this.setTipoLaboratorio(tipoLaboratorio);
        this.setQuantidadeComputadores(quantidadeComputadores);
    }               
            
    public void setTipoLaboratorio(String tipoLaboratorio){
        this.tipoLaboratorio = tipoLaboratorio;
    }

    public String getTipoLaboratorio(){
        return this.tipoLaboratorio;
    }

    public void setQuantidadeComputadores(int quantidadeComputadores){
        this.quantidadeComputadores = quantidadeComputadores;
    }

    public int getQuantidadeComputadores(){
        return this.quantidadeComputadores;
    }
}
