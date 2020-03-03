package zkapi;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description:
 *
 * @version 1.0 17/9/27 下午3:11 by zucker
 */
public class ZKApi {
    private static final Logger logger = LoggerFactory.getLogger(ZKApi.class);
    private static ZooKeeper zooKeeper = ZKManager.createDefault();

    //创建节点

    public static boolean createZNode(String path, String data, Watcher watcher) {
        logger.info("create znode begin path={} data={}", path, data);
        try {
            Stat stat = zooKeeper.exists(path, watcher);
            if(null == stat){
                zooKeeper.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (KeeperException e) {
            e.printStackTrace();
            return false;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        logger.info("create znode complete path={} data={}", path, data);
        return true;
    }

    //删除节点
    public static boolean deleteZNode(String path, Watcher watcher) {
        logger.info("delete znode begin path={}", path);
        try {
            Stat stat = zooKeeper.exists(path, watcher);
            if(null!=stat){
                zooKeeper.delete(path, -1);
            }else {
                return false;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        } catch (KeeperException e) {
            e.printStackTrace();
            return false;
        }
        logger.info("delete znode completed path={}", path);
        return true;
    }

    //更新节点数据
    public static boolean updateZNodeData(String path, String data, Watcher watcher) {
        logger.info("update znode begin path={} data={}", path, data);
        try {
            Stat stat = zooKeeper.exists(path, watcher);
            if(null!=stat) {
                zooKeeper.setData(path, data.getBytes(), -1);
            }else{
                return false;
            }
        } catch (KeeperException e) {
            e.printStackTrace();
            return false;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        logger.info("update znode begin path={} data={}", path, data);
        return true;
    }

    //读取节点
    public static String readZNodeData(String path) {
        logger.info("read znode begin path={}", path);
        String data = null;
        try {
            data = new String(zooKeeper.getData(path, false, null));
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("read znode begin path={}", path);
        return data;
    }

    //获取zk对象
    public static ZooKeeper getZooKeeper() {
        return zooKeeper;
    }

}
