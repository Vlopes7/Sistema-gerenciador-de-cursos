package DAO;
import java.sql.*;
import java.sql.PreparedStatement;
import Factory.ConnectionFactory;
import java.sql.SQLException;
import Modelo.Curso;
public class cadCurso {
    private Connection connection;
    
    public cadCurso(){
        this.connection = new ConnectionFactory().getConnection();
    }
    
    public void adicionarCurso(Curso curso){
        String sql = "INSERT INTO curso(nome, ch, limite) VALUES(?,?,?)";
        
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, curso.getNome());
            stmt.setString(2, curso.getCarga());
            stmt.setString(3, curso.getLimite()); 
            stmt.execute();
            stmt.close();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
    
    public void removerCurso(String nome){
        String sql = "DELETE FROM curso WHERE nome = ?";
        
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.execute();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
    
    public void removerAlunos(String curso){
        String sql = "UPDATE aluno SET curso = NULL WHERE curso = ?";
        
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, curso);
            stmt.execute();
            stmt.close();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
}
