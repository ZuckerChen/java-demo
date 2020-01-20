package jedis;

import redis.clients.jedis.Jedis;

/**
 * Created by chenzhang on 2016/9/30.
 */
public class JedisClientTest {
    public static void main(String[] args) {
        Jedis j = new Jedis("localhost",6379);
        j.publish("news.share","publish news by chenz");
        j.publish("news.blog","publish news By chenz");
    }
}
