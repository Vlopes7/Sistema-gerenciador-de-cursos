package Modelo;
public class Aluno {
    private String cpf;
    private String nome;
    private String email;
    private String nascimento;
    private String curso;

    public String getCpf() {
        return cpf;
    }

    public String getCurso() {
        return curso;
    }

    public String getEmail() {
        return email;
    }

    public String getNascimento() {
        return nascimento;
    }

    public String getNome() {
        return nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNascimento(String nascimento) {
        this.nascimento = nascimento;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    
}
