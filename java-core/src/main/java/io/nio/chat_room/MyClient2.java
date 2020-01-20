package io.nio.chat_room;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.Set;

/**
 * @Author: Zucker
 * @Date: 2020-01-14 20:31
 * @Description
 */
public class MyClient2 {

    // 服务端地址
    private InetSocketAddress SERVER;

    Charset charset = Charset.defaultCharset();

    // 接受缓冲区
    private ByteBuffer rBuffer = ByteBuffer.allocate(1024);

    // 发送缓冲区
    private ByteBuffer sBuffer = ByteBuffer.allocate(1024);

    // 监听通道事件
    private Selector selector;

    private MyClient2(int port) {
        SERVER = new InetSocketAddress("localhost", port);

        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void init() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_CONNECT);
        socketChannel.connect(SERVER);

        while (true) {
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            selectionKeys.forEach(selectionKey -> handle(selectionKey));
            selectionKeys.clear();
        }
    }

    private void handle(SelectionKey selectionKey) {
        try {
            if (selectionKey.isConnectable()) {
                SocketChannel clientChannel = (SocketChannel) selectionKey.channel();
                if (clientChannel.isConnectionPending()) {
                    clientChannel.finishConnect();
                    System.out.println("client:" + clientChannel.toString() + "连接成功");

                    //启动线程监听客户端输入
                    new Thread(() -> {
                        while (true) {
                            try {
                                Scanner scanner = new Scanner(System.in);
                                String content = scanner.nextLine();
                                sBuffer.clear();
                                sBuffer.put(charset.encode(content));
                                sBuffer.flip();

                                clientChannel.write(sBuffer);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }).start();
                    clientChannel.register(selector, SelectionKey.OP_READ);
                }

            } else if (selectionKey.isReadable()) {
                SocketChannel clientChannel = (SocketChannel) selectionKey.channel();
                rBuffer.clear();
                int read = clientChannel.read(rBuffer);
                if (read > 0) {
                    System.out.println("收到群消息:" + new String(rBuffer.array(), 0, read));
                }
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) throws IOException {
        new MyClient2(8888);
    }
}
