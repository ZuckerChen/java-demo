package activemq.topic;

import org.junit.Test;

/**
 * Created by chenchang on 16/10/31.
 */
public class TestTopicDemo {
    @Test
    public void test() throws Exception {
        ConsumerDemo consumer = new ConsumerDemo();

        ProducterDemo producer = new ProducterDemo();

        //接受消息方式一：通过监听Listener 开始监听

        consumer.consumeMessage();

        // 延时500毫秒之后发送消息

        Thread.sleep(500);

        producer.produceMessage("Hello, world!");

        //接受消息方式2：主动方式， 必须在producer产生消息后，去获取，否则获取不到

        //consumer.consumeMessage();

        producer.close();

        // 延时500毫秒之后停止接受消息

        Thread.sleep(500);

        consumer.close();

    }
}
