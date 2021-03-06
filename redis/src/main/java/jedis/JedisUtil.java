package jedis;

import redis.clients.jedis.Jedis;

/**
 * Created by chenzhang on 2016/9/29.
 */
public class JedisUtil {
    public static Jedis createJedis(String host, int port, String password) {
        Jedis jedis = new Jedis(host, port);
        if (null != password && !password.isEmpty()) {
            jedis.auth(password);
        }
        return jedis;
    }

    public static Jedis createJedis(String host, int port) {
        return createJedis(host, port, null);
    }
}
