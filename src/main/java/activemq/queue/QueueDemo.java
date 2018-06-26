package activemq.queue;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by chenchang on 16/10/31.
 */
public class QueueDemo {
    private static String user = ActiveMQConnection.DEFAULT_USER;     //默认 用户
    private static String password = ActiveMQConnection.DEFAULT_PASSWORD;    //默认 密码
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL; //默认的是localhost:8080

    public static void main(String[] args) {
        InitProducter();

        InitConsumer();
    }


    public static void InitProducter() {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user, password, url);
        try {
            Connection connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue("cz");
            MessageProducer messageProducer = session.createProducer(destination);
            sendMessage(session, messageProducer);

            session.close();
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void InitConsumer() {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user, password, url);
        try {
            Connection connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue("cz");
            MessageConsumer messageConsumer = session.createConsumer(destination);
            Message message = messageConsumer.receive();

            while (true) {
                TextMessage textMessage = (TextMessage) messageConsumer.receive(100000);
                if (textMessage != null) {
                    System.out.println("收到的消息:" + textMessage.getText());
                } else {
                    break;
                }
            }

            messageConsumer.close();
            session.close();
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 发送消息
     *
     * @param session
     * @param messageProducer 消息生产者
     * @throws Exception
     */
    public static void sendMessage(Session session, MessageProducer messageProducer) throws Exception {
        for (int i = 0; i < 10; i++) {
            //创建一条文本消息
            TextMessage message = session.createTextMessage("ActiveMQ 发送消息" + i);
            System.out.println("发送消息：Activemq 发送消息" + i);
            //通过消息生产者发出消息
            messageProducer.send(message);
        }

    }
}
