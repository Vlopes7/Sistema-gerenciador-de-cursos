package Factory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    public Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost/LP", "root", "fatec");
        } catch (ClassNotFoundException | SQLException excecao) {
            throw new RuntimeException(excecao);
        }
    }
}
