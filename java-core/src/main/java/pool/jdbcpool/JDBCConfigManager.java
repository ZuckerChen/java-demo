package pool.jdbcpool;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Author: Zucker
 * @Date: 2020/3/17 2:05 PM
 * @Description
 */
public class JDBCConfigManager {
    Properties properties = new Properties();

    public JDBCConfigManager() {
        InputStream is = JDBCConfigManager.class.getClassLoader().getResourceAsStream("database.properties");
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class Holder {
        private static JDBCConfigManager INSTANCE = new JDBCConfigManager();
    }

    public static JDBCConfigManager getInstance() {
        return Holder.INSTANCE;
    }

    public String getVal(String key) {
        return properties.getProperty(key);
    }
}
