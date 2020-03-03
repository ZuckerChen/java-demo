package zkapi;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Description:
 *
 * @version 1.0 17/9/27 下午8:10 by zucker
 */
public class ZKTest {
    private static final Logger logger = LoggerFactory.getLogger(ZKTest.class);

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        logger.info("log 测试");
        System.out.println(ZKApi.createZNode("/test", "创建测试节点", new TestWatcher()));
        System.out.println(ZKApi.readZNodeData("/test"));
        System.out.println(ZKApi.updateZNodeData("/test", "update测试节点",new TestWatcher()));
        System.out.println(ZKApi.readZNodeData("/test"));
        ZKApi.deleteZNode("/test",new TestWatcher());

    }
    static class TestWatcher implements Watcher {

        public void process(WatchedEvent event) {
            if (Event.EventType.NodeCreated.getIntValue() == event.getType().getIntValue()) {
                logger.info(event.getType().name());
            } else if (Event.EventType.NodeDataChanged.getIntValue() == event.getType().getIntValue()) {
                logger.info(event.getType().name());
            } else if (Event.EventType.NodeDeleted.getIntValue() == event.getType().getIntValue()) {
                logger.info(event.getType().name());
            } else if (Event.EventType.NodeChildrenChanged.getIntValue() == event.getType().getIntValue()) {
                logger.info(event.getType().name());
            } else {
                logger.info(event.getType().name());
            }
        }
    }
}
