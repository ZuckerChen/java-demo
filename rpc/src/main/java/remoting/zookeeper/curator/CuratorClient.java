package remoting.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Author: Zucker
 * @Date: 2020/3/30 11:49 AM
 * @Description
 */
public class CuratorClient {

    private final CuratorFramework client;
    private final Set<StateListener> stateListeners = new CopyOnWriteArraySet<>();


    public CuratorClient(String connectString) {
        RetryNTimes retryNTimes = new RetryNTimes(1, 1000);

        client = CuratorFrameworkFactory.builder()
                .connectString(connectString)
                .retryPolicy(retryNTimes)
                .connectionTimeoutMs(5000)
                .sessionTimeoutMs(5000)
                .build();

        client.getConnectionStateListenable().addListener(new CuratorConnectionStateListener());

        try {
            client.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    protected void stateChanged(int state) {
        for (StateListener stateListener : stateListeners) {
            stateListener.stateChanged(state);
        }
    }

    public void create(String nodePath, String nodeData) {
        try {
            client.create().creatingParentContainersIfNeeded().withMode(CreateMode.PERSISTENT)
                    .forPath(nodePath, nodeData.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(String nodePath) {
        try {
            client.delete().deletingChildrenIfNeeded().forPath(nodePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkExists(String nodePath) {
        try {
            if (null != client.checkExists().forPath(nodePath)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isConnection() {
        return client.getZookeeperClient().isConnected();
    }

    public void createTargetChildListener(String childPath, RpcCuratorWatch rpcCuratorWatch) {
        try {
            client.getData().usingWatcher(rpcCuratorWatch).forPath(childPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private class CuratorConnectionStateListener implements ConnectionStateListener {
        private final long UNKNOWN_SESSION_ID = -1L;

        private long lastSessionId;

        @Override
        public void stateChanged(CuratorFramework curatorFramework, ConnectionState connectionState) {
            long sessionId = UNKNOWN_SESSION_ID;
            try {
                sessionId = client.getZookeeperClient().getZooKeeper().getSessionId();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (connectionState == ConnectionState.LOST) {
                CuratorClient.this.stateChanged(StateListener.SESSION_LOST);
            } else if (connectionState == ConnectionState.SUSPENDED) {
                CuratorClient.this.stateChanged(StateListener.SUSPENDED);
            } else if (connectionState == ConnectionState.CONNECTED) {
                lastSessionId = sessionId;
                CuratorClient.this.stateChanged(StateListener.CONNECTED);
            } else if (connectionState == ConnectionState.RECONNECTED) {
                if (lastSessionId == sessionId && sessionId != UNKNOWN_SESSION_ID) {
                    CuratorClient.this.stateChanged(StateListener.RECONNECTED);
                } else {
                    CuratorClient.this.stateChanged(StateListener.NEW_SESSION_CREATED);
                }
            }
        }
    }

    public static void main(String[] args) {

        CuratorClient client = new CuratorClient("localhost:2181");
        System.out.println("是否连接：" + client.isConnection());

        System.out.println("节点是否存在" + client.checkExists("/dubbo/test"));
        client.create("/dubbo/test", "第一个节点");
        System.out.println("节点是否存在" + client.checkExists("/dubbo/test"));
        client.delete("/dubbo/test");
        System.out.println("节点是否存在" + client.checkExists("/dubbo/test"));
    }
}