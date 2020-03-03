package curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

/**
 * @Author: Zucker
 * @Date: 2020/1/20 3:54 PM
 * 参考 http://www.throwable.club/2018/12/16/zookeeper-curator-usage/#Zookeeper%E5%AE%A2%E6%88%B7%E7%AB%AFCurator%E4%BD%BF%E7%94%A8%E8%AF%A6%E8%A7%A3
 * @Description
 */
public class CuratorDemo {
    private static CuratorFramework client;

    public static void main(String[] args) {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        client = CuratorFrameworkFactory.builder()
                .connectString("192.168.128.129:2181")
                .sessionTimeoutMs(5000)  // 会话超时时间
                .connectionTimeoutMs(5000) // 连接超时时间
                .retryPolicy(retryPolicy)
                .namespace("base") // 包含隔离名称
                .build();
        client.start();
    }


    public void createNode() throws Exception {
        client.create().creatingParentContainersIfNeeded() // 递归创建所需父节点
                .withMode(CreateMode.PERSISTENT) // 创建类型为持久节点
                .forPath("/nodeA", "init".getBytes()); // 目录及内容
    }


    public void delete() throws Exception {
        client.delete()
                .guaranteed()  // 强制保证删除
                .deletingChildrenIfNeeded() // 递归删除子节点
                .withVersion(10086) // 指定删除的版本号
                .forPath("/nodeA");
    }

    public void readNodeData() throws Exception {
        byte[] bytes = client.getData().forPath("/nodeA");
        System.out.println(new String(bytes));
    }

    public void readStat() throws Exception {
        Stat stat = new Stat();
        client.getData()
                .storingStatIn(stat)
                .forPath("/nodeA");
    }

    public void updateNodeData() throws Exception {
        client.setData()
                .withVersion(10086) // 指定版本修改
                .forPath("/nodeA", "data".getBytes());
    }
    public void transaction() throws Exception {
        client.inTransaction().check().forPath("/nodeA")
                .and()
                .create().withMode(CreateMode.EPHEMERAL).forPath("/nodeB", "init".getBytes())
                .and()
                .create().withMode(CreateMode.EPHEMERAL).forPath("/nodeC", "init".getBytes())
                .and()
                .commit();
    }

    public void isExists() throws Exception {
        client.checkExists() // 检查是否存在
                .forPath("/nodeA");
        client.getChildren().forPath("/nodeA"); // 获取子节点的路径
    }

}
