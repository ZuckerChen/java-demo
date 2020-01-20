package io.nio.zero_copy;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author: Zucker
 * @Date: 2020-01-16 14:19
 * @Description
 */
public class ZeroCopy {

    /**
     * filechannel进行文件复制（零拷贝）
     * 在不需要对数据文件进行处理时，直接使用零拷贝；如果既需要IO速度，又需要数据操作，则使用NIO的直接内存映射。
     *
     * @param fromFile
     * @param toFile
     */
    public static void fileCopyWithFileChannel(File fromFile, File toFile) {
        long l = System.currentTimeMillis();
        try {
            FileChannel fromFileChannel = new FileInputStream(fromFile).getChannel();

            FileChannel toFileChannel = new FileOutputStream(toFile).getChannel();

            fromFileChannel.transferTo(0, fromFileChannel.size(), toFileChannel);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("使用缓存零拷贝数据耗时:" + (System.currentTimeMillis() - l));
    }

    private static final int BUFFER_SIZE = 1024;

    /**
     * BufferedInputStream进行文件复制（用作对比实验）
     *
     * @param fromFile
     * @param toFile
     */
    public static void bufferedCopy(File fromFile, File toFile) {
        long l = System.currentTimeMillis();
        try (BufferedInputStream fbis = new BufferedInputStream(new FileInputStream(fromFile));
             BufferedOutputStream tbos = new BufferedOutputStream(new FileOutputStream(toFile))) {
            byte[] buf = new byte[BUFFER_SIZE];
            while (fbis.read(buf) > 0) {
                tbos.write(buf);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("使用流拷贝数据耗时:" + (System.currentTimeMillis() - l));
    }

    /**
     * 用直接内存读取映射文件
     *
     * @param file
     */
    public static void fileReadMmap(File file) {

        long l = System.currentTimeMillis();
        byte[] buf = new byte[BUFFER_SIZE];
        try (FileChannel fileChannel = new FileInputStream(file).getChannel()) {
            int len = (int) file.length();
            MappedByteBuffer byteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size());

            for (int offset = 0; offset < fileChannel.size(); offset += BUFFER_SIZE) {
                if (len - offset > BUFFER_SIZE) {
                    byteBuffer.get(buf);
                } else {
                    byteBuffer.get(new byte[len - offset]);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("使用直接内存读取数据耗时:" + (System.currentTimeMillis() - l));
    }

    public static void fileReadHeap(File file) {
        long l = System.currentTimeMillis();
        try (FileChannel fileChannel = new FileInputStream(file).getChannel()) {
            ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);

            while (fileChannel.read(buffer) > 0) {
                buffer.flip();
                buffer.clear();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("使用堆内存读取数据耗时:" + (System.currentTimeMillis() - l));
    }

    public static void main(String[] args) {
        File toFile = new File("/Users/oyo/Downloads/备份/DEEPBBS_GHOSTXPSP3_2013.06-bd.iso");
        File fromFile1 = new File("/Users/oyo/Downloads/DEEPBBS_GHOSTXPSP3_2013.06-bd1.iso");
        File fromFile2 = new File("/Users/oyo/Downloads/DEEPBBS_GHOSTXPSP3_2013.06-bd2.iso");
        fileCopyWithFileChannel(toFile, fromFile1);
        bufferedCopy(toFile, fromFile2);

        fileReadMmap(toFile);
        fileReadHeap(toFile);
    }
}
