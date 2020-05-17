import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author zucker
 * @description
 * @date: 2020/5/3 3:09 PM
 */
public class LocalJDBCTransApplication {
    public static void main(String[] args) throws SQLException {
        Connection connection = getConnection();
    }

    private static Connection getConnection() throws SQLException {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/test";
        String username="root";
        String password="oyo123456";

        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager .getConnection(url,username,password);
    }
}
