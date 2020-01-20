package io.nio.chat_room;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author: Zucker
 * @Date: 2020-01-14 20:20
 * @Description
 */
public class MyServer {

    private int port = 8888;

    Charset charset = Charset.defaultCharset();

    // 接受缓冲区
    private ByteBuffer rBuffer = ByteBuffer.allocate(1024);

    // 发送缓冲区
    private ByteBuffer sBuffer = ByteBuffer.allocate(1024);

    // 存放客户端集合
    private Map<String, SocketChannel> clientMap = new HashMap<>();

    // 监听通道事件
    private Selector selector;

    public MyServer(int port) {
        this.port = port;
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void init() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务器启动，端口为：" + port);
    }

    private void listen() {
        while (true) {
            try {
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                for (SelectionKey selectionKey : selectionKeys) {
                    handle(selectionKey);
                }
                selectionKeys.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handle(SelectionKey selectionKey) {
        try {
            if (selectionKey.isAcceptable()) {
                accept(selectionKey);
            } else if (selectionKey.isReadable()) {
                read(selectionKey);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void accept(SelectionKey selectionKey) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
        SocketChannel clientChannel = serverSocketChannel.accept();
        System.out.println("client:" + clientChannel.toString() + " 请求连接中");
        clientChannel.configureBlocking(false);
        clientChannel.register(selector, SelectionKey.OP_READ);
        clientMap.put(clientChannel.toString(), clientChannel);
        System.out.println("client:" + clientChannel.toString() + " 连接成功");
    }

    private void read(SelectionKey selectionKey) throws IOException {
        SocketChannel clientChannel = (SocketChannel) selectionKey.channel();
        System.out.println("client:" + clientChannel.toString() + " 读取数据中");
        rBuffer.clear();
        int read = clientChannel.read(rBuffer);
        if (read > 0) {
            rBuffer.flip();
            String content = charset.decode(rBuffer).toString();
            System.out.println("client:" + clientChannel.toString() + " 读取数据完毕" + "content:" + content);
            dispatch(clientChannel, content);
        } else {
            selectionKey.cancel();
            System.out.println("client:" + selectionKey.toString() + " 客户端关闭");
        }
    }

    private void dispatch(SocketChannel channel, String content) {
        System.out.println("开始广播，数量" + clientMap.size());
        clientMap.forEach((k, clientSocket) -> {
            if (!clientSocket.equals(channel)) {
                try {
                    sBuffer.clear();
                    sBuffer.put(charset.encode(clientSocket.toString() + ":" + content));
                    sBuffer.flip();
                    clientSocket.write(sBuffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println("广播完毕");
    }


    public static void main(String[] args) {
        MyServer myServer = new MyServer(8888);
        myServer.listen();
    }
}
