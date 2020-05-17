package pool.jdbcpool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author: Zucker
 * @Date: 2020/3/17 2:14 PM
 * @Description 自定义简单线程池
 */
public class MyConnectionPool {
    /**
     * 闲置队列
     */
    private static BlockingQueue<Connection> idleQueue = new LinkedBlockingQueue();

    /**
     * 正在使用连接的列表
     */
    private static BlockingQueue<Connection> occupiedQueue = new LinkedBlockingQueue();

    public static final JDBCConfig config = null;

    /**
     * 获取连接
     * 1、idleQueue不为空，则直接取
     * 2、idleQueue为空，且正在使用列表未达到最大
     * 3、idleQueue不为空，且正在使用队列已达到最大，等待一段时间
     * 4、等待时间内有连接释放则直接获取，超时则抛出异常
     * @return
     */
    public static Connection getConnection() {

        return null;
    }

    private void initConnPool() {
        //获取配置
        int maxConn = JDBCConfig.getMax_conn();
        int minConn = JDBCConfig.getMin_conn();

        //加载驱动
        try {
            Class.forName(JDBCConfig.getDirver());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //初始化连接池
        for (int i = 0; i < minConn; i++) {
            try {
                idleQueue.put(createConnection());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Connection createConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(JDBCConfig.getUrl(), JDBCConfig.getUsername(), JDBCConfig.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }

}
