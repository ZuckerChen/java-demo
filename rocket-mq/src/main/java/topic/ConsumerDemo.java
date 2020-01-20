package activemq.topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by chenchang on 16/10/31.
 */
public class ConsumerDemo implements MessageListener {
    private static String user = ActiveMQConnection.DEFAULT_USER;     //默认 用户
    private static String password = ActiveMQConnection.DEFAULT_PASSWORD;    //默认 密码
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL; //默认的是localhost:8080
    private static String subject = "topic";
    private Destination destination = null;  //在点对点（PTP）消息传递域中，目的地被成为队列（queue）
    private Topic topic = null;//在发布/订阅（PUB/SUB）消息传递域中，目的地被成为主题（topic）
    private Connection connection = null;  //初始化 一个JMS客户端到JMS Provider的连接
    private Session session = null;  //初始化  一个发送消息的进程
    private MessageConsumer consumer = null;    //初始化 消息消费者 (它是由session 创建的)
    // 初始化

    private void initialize() throws JMSException, Exception {

        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user, password, url);

        connection = connectionFactory.createConnection();

        //false 参数表示 为非事务型消息，后面的参数表示消息的确认类型（见4.消息发出去后的确认模式）

        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //PTP消息方式   目的地被成为队列（queue）

//        destination = session.createQueue(subject);
//
//        consumer = session.createConsumer(destination);

        //在发布/订阅（PUB/SUB）消息，目的地被成为主题（topic）。

        topic = session.createTopic(subject);

        consumer = session.createConsumer(topic);


    }


    // 消费消息

    public void consumeMessage() throws JMSException, Exception {

        initialize();

        connection.start();

        System.out.println("Consumer:->Begin listening...");

        //接受消息方式1：消息的异步接收 监听 实现MessageListener接口，每当消息到达时，ActiveMQ会调用MessageListener中的onMessage函数。

        consumer.setMessageListener(this);

        //接受消息方式2：消息的同步  主动接受消息 用recieve方式

        /* TextMessage textMessage = (TextMessage)consumer.receive();

         if(null!=textMessage)

          System.out.println(textMessage.getText());

         else

          System.out.println("无"); */


    }


    // 关闭连接

    public void close() throws JMSException {

        System.out.println("Consumer:->Closing connection");

        if (consumer != null)

            consumer.close();

        if (session != null)

            session.close();

        if (connection != null)

            connection.close();

    }


    // 消息处理函数

    public void onMessage(Message message) {

        try {

            if (message instanceof TextMessage) {

                TextMessage txtMsg = (TextMessage) message;

                String msg = txtMsg.getText();

                System.out.println("Consumer:->Received: " + msg);

            } else {

                System.out.println("Consumer:->Received: " + message);

            }

        } catch (JMSException e) {

            e.printStackTrace();

        }

    }
}
