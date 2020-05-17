package pool.jdbcpool;

/**
 * @Author: Zucker
 * @Date: 2020/3/17 2:54 PM
 * @Description
 */

public class JDBCConfig {
    /**
     * 最小连接数
     */
    public static final int min_conn=5;

    /**
     * 最大连接数
     */
    public static final int max_conn=15;

    /**
     * 最小空闲连接数
     */
    public static final int min_idle=5;

    /**
     * 获取连接最大等待时间
     */
    public static final long max_wait=1000;

    public static final String dirver="";

    public static final String url="";

    public static final String username="";

    public static final String password="";

    public static int getMin_conn() {
        return min_conn;
    }

    public static int getMax_conn() {
        return max_conn;
    }

    public static int getMin_idle() {
        return min_idle;
    }

    public static long getMax_wait() {
        return max_wait;
    }

    public static String getDirver() {
        return dirver;
    }

    public static String getUrl() {
        return url;
    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }
}
