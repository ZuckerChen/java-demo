package io.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author: zucker
 * @description:
 * @date: 2019-07-12 14:16
 */
public class ChannelSample {
    public static void main(String[] args) throws IOException {
        RandomAccessFile accessFile = new RandomAccessFile("/Users/oyo/Documents/settings.xml", "rw");
        FileChannel inChannel = accessFile.getChannel();

        ByteBuffer buf = ByteBuffer.allocate(64);

        int bytesRead = inChannel.read(buf);
        while (bytesRead != -1) {
            buf.flip();
            while (buf.hasRemaining()) {
                System.out.print((char) buf.get());
            }

            buf.clear();
            bytesRead = inChannel.read(buf);
        }
        accessFile.close();
    }

}
