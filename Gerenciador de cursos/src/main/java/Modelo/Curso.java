package Modelo;
public class Curso {
    private String nome;
    private String carga;
    private String limite;

    public String getCarga() {
        return carga;
    }

    public String getLimite() {
        return limite;
    }

    public String getNome() {
        return nome;
    }

    public void setCarga(String carga) {
        this.carga = carga;
    }

    public void setLimite(String limite) {
        this.limite = limite;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public boolean veriflimite(int limite){
        return (limite < 1) ? false:true;
    }
    
    public boolean verifCarga(int carga){
        return (carga < 20) ? false : true;
    }
}
