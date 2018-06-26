package jedis;

import redis.clients.jedis.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenzhang on 2016/9/29.
 */
public class JedisPoolUtil {
    private static JedisPool pool;

    private static void createJedisPool(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(100);//空间连接
        config.setMaxWaitMillis(10000);//最大阻塞时间
        config.setMaxTotal(2);//最大连接

        pool = new JedisPool(config,"localhost");
    }

    /**
     * 多线程下同步初始化
     */
    private static synchronized void init(){
        if(null == pool){
            createJedisPool();
        }
    }

    public static Jedis getJedis(){
        if(null == pool){
            init();
        }
        return pool.getResource();
    }

    public static void returnResource(Jedis jedis){
        jedis.close();
    }

    public static ShardedJedis createShardJedis() {

        //建立服务器列表
        List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();

        //添加第一台服务器信息
        JedisShardInfo si = new JedisShardInfo("localhost", 6379);
        //si.setPassword("123");
        shards.add(si);

        //添加第二台服务器信息
        si = new JedisShardInfo("localhost", 6399);
        //si.setPassword("123");
        shards.add(si);
        //建立分片连接对象
        ShardedJedis jedis = new ShardedJedis(shards);

        //建立分片连接对象,并指定Hash算法
        //ShardedJedis jedis = new ShardedJedis(shards,selfHash);
        return jedis;
    }

    private static void createPool() {
        List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
        JedisShardInfo si = new JedisShardInfo("localhost", 6379);
        si.setPassword("123");
        shards.add(si);
        si = new JedisShardInfo("localhost", 6399);
        si.setPassword("123");
        ShardedJedisPool
        pool = new ShardedJedisPool(new JedisPoolConfig(), shards);
    }
}
