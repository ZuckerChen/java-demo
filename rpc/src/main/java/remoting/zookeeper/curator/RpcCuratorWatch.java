package remoting.zookeeper.curator;

import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.zookeeper.WatchedEvent;

/**
 * @Author: Zucker
 * @Date: 2020/3/30 2:39 PM
 * @Description
 */
public class RpcCuratorWatch implements CuratorWatcher {
    @Override
    public void process(WatchedEvent watchedEvent) throws Exception {
        System.out.println("触发watcher，节点路径为：" + watchedEvent.getPath());
    }
}
