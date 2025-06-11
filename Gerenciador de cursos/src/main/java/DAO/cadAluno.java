package DAO;
import java.sql.*;
import java.sql.PreparedStatement;
import Factory.ConnectionFactory;
import java.sql.SQLException;
import Modelo.Aluno;
import java.sql.ResultSet;
import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
public class cadAluno {
    private Connection connection;
    
    public cadAluno(){
        this.connection = new ConnectionFactory().getConnection();
    }
    
    public void adicionarAluno(Aluno aluno){
        String sql = "INSERT INTO aluno(nome, cpf, email, nascimento, curso) VALUES(?,?,?,?,?)";
        
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getCpf());
            stmt.setString(3, aluno.getEmail());
            stmt.setString(4, aluno.getNascimento());
            stmt.setString(5, aluno.getCurso());
            stmt.execute();
            stmt.close();
            JOptionPane.showMessageDialog(null, "Aluno cadastrado com sucesso");
            
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
    
    public void desativarAluno(Aluno aluno){
        String sql = "UPDATE aluno SET status_aluno = 'desativado' WHERE cpf = ?";
        
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, aluno.getCpf());
            stmt.execute();
            stmt.close();
            
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
    
    public void ativarAluno(Aluno aluno){
        String sql = "UPDATE aluno SET status_aluno = 'ativo' WHERE cpf = ?";
        
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, aluno.getCpf());
            stmt.execute();
            stmt.close();
            
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
    
    public boolean cpfSQL(String cpf){
        String sql = "SELECT * FROM aluno WHERE cpf = ?";
        
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
            
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
    
    public String sitSQL(String cpf) {
    String sql = "SELECT status_aluno FROM aluno WHERE cpf = ?";
    
    try {
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, cpf);
        
        ResultSet rs = stmt.executeQuery();
        String status = "CPF não encontrado";

        if (rs.next()) {
            status = rs.getString("status_aluno");
        }

        rs.close();
        stmt.close();
        
        return status;
        
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
}

    
    public boolean verifCurso(String nome){
        String sql = "SELECT * FROM curso WHERE nome = ?";
        
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, nome); 
            ResultSet rs = stmt.executeQuery(); 
        
            return rs.next(); 
        } catch (SQLException e) {
            throw new RuntimeException(e);
    }
    }
    
    public void removerCurso(Aluno aluno){
        String sql = "UPDATE aluno SET curso = NULL WHERE cpf = ?";
        
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, aluno.getCpf());
            stmt.execute();
            stmt.close();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
    
    public int limiteCurso(String curso2) {
        String sql = "SELECT COUNT(*) FROM aluno WHERE curso = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, curso2);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1); 
            } else {
                return 0;  
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
    }
}
    
    public int limitesql(String curso){
        String sql = "SELECT limite FROM curso WHERE nome = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, curso);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1); 
            } else {
                return 0;  
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
    }
    }
    
    public boolean verifCPF(String cpf){
         CPFValidator validador = new CPFValidator();
    try {
        validador.assertValid(cpf);
        return true;
    } catch (InvalidStateException e) {
        return false; 
    }   
    }
    
    public boolean verifEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        
        String regex = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
    
        return email.matches(regex);
}
    
    public boolean verifIdade(String data) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd / MM / yyyy");
        LocalDate nascimento = LocalDate.parse(data, formatter);
        int anoNascimento = nascimento.getYear();
        int anoAtual = LocalDate.now().getYear();

        return (anoAtual - anoNascimento) >= 16;
    }
    
    public DefaultTableModel carregarAlunos(String curso) {
        String sql = "SELECT * FROM aluno WHERE curso = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, curso);
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData meta = rs.getMetaData();

            int colunas = meta.getColumnCount();
            String[] nomesColunas = new String[colunas];
            for (int i = 1; i <= colunas; i++) {
                nomesColunas[i - 1] = meta.getColumnName(i);
            }

            DefaultTableModel modelo = new DefaultTableModel(nomesColunas, 0);

            while (rs.next()) {
                Object[] linha = new Object[colunas];
                for (int i = 1; i <= colunas; i++) {
                    linha[i - 1] = rs.getObject(i);
            }
                modelo.addRow(linha);
        }

            return modelo;

    } catch (SQLException e) {
        throw new RuntimeException("Erro ao carregar alunos: " + e.getMessage(), e);
    }
}
    
    public DefaultTableModel carregarAlunos2() {
        String sql = "SELECT * FROM aluno";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData meta = rs.getMetaData();

            int colunas = meta.getColumnCount();
            String[] nomesColunas = new String[colunas];
            for (int i = 1; i <= colunas; i++) {
                nomesColunas[i - 1] = meta.getColumnName(i);
            }

            DefaultTableModel modelo = new DefaultTableModel(nomesColunas, 0);

            while (rs.next()) {
                Object[] linha = new Object[colunas];
                for (int i = 1; i <= colunas; i++) {
                    linha[i - 1] = rs.getObject(i);
            }
                modelo.addRow(linha);
        }

            return modelo;

    } catch (SQLException e) {
        throw new RuntimeException("Erro ao carregar alunos: " + e.getMessage(), e);
    }
}
    
    public DefaultTableModel carregarAlunoCPF(String cpf){
        String sql = "SELECT * FROM aluno WHERE cpf = ?";
        
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData meta = rs.getMetaData();
            
            int colunas = meta.getColumnCount();
            String[] nomesColunas = new String[colunas];
            for (int i = 1; i <= colunas; i++) {
                nomesColunas[i - 1] = meta.getColumnName(i);
            }

            DefaultTableModel modelo = new DefaultTableModel(nomesColunas, 0);

            while (rs.next()) {
                Object[] linha = new Object[colunas];
                for (int i = 1; i <= colunas; i++) {
                    linha[i - 1] = rs.getObject(i);
                }
                modelo.addRow(linha);
            }

            return modelo;
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
    
    public DefaultTableModel carregarAlunosAtivosInativos(String dado){
         String sql = "SELECT * FROM aluno WHERE status_aluno = ?";
         
         try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, dado);
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData meta = rs.getMetaData();

            int colunas = meta.getColumnCount();
            String[] nomesColunas = new String[colunas];
            for (int i = 1; i <= colunas; i++) {
                nomesColunas[i - 1] = meta.getColumnName(i);
            }

            DefaultTableModel modelo = new DefaultTableModel(nomesColunas, 0);

            while (rs.next()) {
                Object[] linha = new Object[colunas];
                for (int i = 1; i <= colunas; i++) {
                    linha[i - 1] = rs.getObject(i);
                }
                modelo.addRow(linha);
            }

            return modelo;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao carregar alunos: " + e.getMessage(), e);
        }
    }
    
    public Aluno updateAlunos(String cpf){
        String sql = "SELECT * FROM aluno WHERE cpf = ?";
        Aluno al = new Aluno();

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                al.setNome(rs.getString("nome"));
                al.setEmail(rs.getString("email"));
                al.setNascimento(rs.getString("nascimento"));
                al.setCurso(rs.getString("curso"));
            }

            rs.close();
            stmt.close();

        } catch(SQLException e){
            throw new RuntimeException(e);
        }

        return al;
}    
    
    public void updateAlunosButton(Aluno aluno){
        String sql = "UPDATE aluno SET nome = ?, email = ?, nascimento = ?, curso = ? WHERE CPF = ?";
        
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, aluno.getNome());
            System.out.println("Passou aqui " + aluno.getNome());
            stmt.setString(2, aluno.getEmail());
            stmt.setString(3, aluno.getNascimento());
            stmt.setString(4, aluno.getCurso());
            stmt.setString(5, aluno.getCpf());
            System.out.println("Passou aqui tambem" + aluno.getCpf());
            stmt.execute();
            JOptionPane.showMessageDialog(null, "Dados atualizados com sucesso!");
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
    
    
    
    public DefaultTableModel exibirCursos() {
        String sqlCursos = "SELECT nome, ch, limite FROM curso";
        String sqlContagem = "SELECT COUNT(*) FROM aluno WHERE curso = ? AND status_aluno = 'ativo'";

        String[] colunas = {"Nome do Curso", "Carga Horária", "Limite de Alunos", "Alunos Matriculados"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);

        try (PreparedStatement stmtCursos = connection.prepareStatement(sqlCursos);
             ResultSet rsCursos = stmtCursos.executeQuery()) {

            PreparedStatement stmtContagem = connection.prepareStatement(sqlContagem);

            while (rsCursos.next()) {
                String nomeCurso = rsCursos.getString("nome");
                int cargaHoraria = rsCursos.getInt("ch");
                int limiteAlunos = rsCursos.getInt("limite");

                stmtContagem.setString(1, nomeCurso);
                ResultSet rsCount = stmtContagem.executeQuery();
                int qtdMatriculados = 0;
                if (rsCount.next()) {
                    qtdMatriculados = rsCount.getInt(1);
                }

                Object[] linha = {
                    nomeCurso,
                    cargaHoraria,
                    limiteAlunos,
                    qtdMatriculados
                };
                modelo.addRow(linha);
            }

            stmtContagem.close();
            return modelo;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao exibir cursos: " + e.getMessage(), e);
        }
}
    public boolean curso(String curso){
        String sql = "SELECT 1 FROM curso WHERE nome = ? LIMIT 1";
        
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, curso);
            
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
    
    public void alunoCurso(String curso, String cpf){
        String sql = "UPDATE aluno SET curso = ? WHERE CPF = ?";
        
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, curso);
            stmt.setString(2, cpf);
            stmt.execute();
            stmt.close();
            JOptionPane.showMessageDialog(null, "Aluno matriculado ao curso: " + curso);
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
          
    }
    
    public void removerAluno(String cpf){
        String sql = "UPDATE aluno SET curso = NULL WHERE cpf = ?";
        
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, cpf);
            stmt.execute();
            JOptionPane.showMessageDialog(null, "Aluno removido do curso atual");
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
    
}
