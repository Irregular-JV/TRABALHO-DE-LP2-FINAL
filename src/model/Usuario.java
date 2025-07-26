package model;

public class Usuario {
    private int id;
    private String username;
    private String senha;
    private String nivelAcesso;

    // Construtores
    
    // Caso não precise do ID
    public Usuario(String username, String senha, String nivelAcesso) {
        this.username = username;
        this.senha = senha;
        this.nivelAcesso = nivelAcesso;
    }

    // Caso necessite do ID
    public Usuario(int id, String username, String senha, String nivelAcesso) {
        this.id = id;
        this.username = username;
        this.senha = senha;
        this.nivelAcesso = nivelAcesso;
    }
    
    // Getters e Setters

    public int getId(){
        return this.id;
    }
    public void setId(int id){
        this.id = id;
    }

    public String getUsername(){
        return this.username;
    }
    public void setUsername(String username){
        this.username = username;
    }

    public String getSenha(){
        return this.senha;
    }
    public void setSenha(String senha){
        this.senha = senha;
    }

     public String getNivelAcesso(){
        return this.nivelAcesso;
    }
    public void setNivelAcesso(String nivelAcesso){
        this.nivelAcesso = nivelAcesso;
    }
}
