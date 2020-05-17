package pool.jdbcpool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @Author: Zucker
 * @Date: 2020/3/17 2:10 PM
 * @Description
 */
public class JDBCUtil {

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        JDBCConfigManager config = JDBCConfigManager.getInstance();
        String driver = config.getVal("driver");
        String url = config.getVal("url");
        String username = config.getVal("username");
        String password = config.getVal("password");
        Connection connection = null;
        //com.mysql.cj.jdbc.Driver
        Class.forName(driver);
        connection = DriverManager.getConnection(url, username, password);
        return connection;
    }
}
