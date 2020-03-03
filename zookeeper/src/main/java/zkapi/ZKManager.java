package zkapi;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Description:
 *
 * @version 1.0 17/9/27 下午5:10 by zucker
 */
public class ZKManager {
    private static final Logger logger = LoggerFactory.getLogger(ZKManager.class);

    private int sessionTime = 2000;
    private String connectString = "127.0.0.1:2181";
    private ZooKeeper zooKeeper = null;

    public static ZKManager create() {
        return new ZKManager();
    }

    public static ZKManager custom() {
        return ZKManager.create();
    }

    public static ZooKeeper createDefault() {
        return ZKManager.create().build();
    }

    public ZooKeeper build() {
        logger.info("create zk connection begin.\n" + toString());
        try {
            if (zooKeeper == null) {
                zooKeeper = new ZooKeeper(connectString, sessionTime, new Watcher() {
                    public void process(WatchedEvent event) {
                        logger.info("收到事件通知：" + event.getState());
                    }
                });
            }
        } catch (IOException e) {
            logger.error("create zk connection error.", e);
            e.printStackTrace();
        }
        logger.info("create zk connection complete.\n" + zooKeeper.toString());
        return zooKeeper;
    }

    /**
     * close zk connection
     */
    public void closeZK() {
        try {
            zooKeeper.close();
        } catch (InterruptedException e) {
            logger.error("", e);
            e.printStackTrace();
        }
    }

    public int getSessionTime() {
        return sessionTime;
    }

    public ZKManager setSessionTime(int sessionTime) {
        this.sessionTime = sessionTime;
        return this;
    }

    public String getConnectString() {
        return connectString;
    }

    public ZKManager setConnectString(String connectString) {
        this.connectString = connectString;
        return this;
    }

    @Override
    public String toString() {
        return "ZKManager{" +
                "sessionTime=" + sessionTime +
                ", connectString='" + connectString + '\'' +
                '}';
    }
}
