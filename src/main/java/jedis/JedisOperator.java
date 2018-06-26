package jedis;

import redis.clients.jedis.Client;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenzhang on 2016/9/29.
 */
public class JedisOperator {
    public static void main(String[] args) {
        //save();
        //saveMap();
        //saveList();
        //pubSub();

    }
    private static void save(){
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                Jedis j = JedisPoolUtil.getJedis();
                j.sync();
                System.err.println(j.toString());
                for (int k = 0; k < 10000; k++) {
                    j.set(j.toString()+k,String.valueOf(k));
                    //System.out.println(j.toString());
                }
                j.close();
            }).start();
        }
    }

    private static void saveMap(){
        Jedis j = JedisUtil.createJedis("localhost",6379);

        Map m = new HashMap();
        m.put("name","chenzhang");
        m.put("age","18");
        j.hmset("hash",m);

        j.hset("hash","address","shanghai");
        j.hset("hash","age","24");
        System.out.println(j.hgetAll("hash"));
        System.out.println(j.hmget("hash","name"));
    }

    private static void saveList(){
        Jedis j = JedisUtil.createJedis("localhost",6379);

        j.lpush("list","name","age","address","sex");
        while (true){
            String s = j.lpop("list");
            if(null == s || s.isEmpty()){
                System.out.println(s);
                break;
            }else{
                System.out.println(s);
            }
        }
    }

    private static void pubSub(){
        Jedis j = new Jedis("localhost",6379, 0);
        Client c = j.getClient();
        TestPubSub sp = new TestPubSub();

        sp.proceed(c,"news.share", "news.blog");
        System.out.println(sp.isSubscribed());
        System.out.println(sp.getSubscribedChannels());

        j.disconnect();
    }
}
