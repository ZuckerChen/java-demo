package io.nio.big_file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * @Author: Zucker
 * @Date: 2020/3/9 10:49 AM
 * @Description 参考：https://github.com/yongzhidai/ToolClass/blob/master/src/main/java/cn/dyz/tools/file/BigFileReader.java
 */
public class BigFileReader {
    private int threadSize;
    private String charset;
    private int bufferSize;
    private ExecutorService executorService;
    private long fileLength;
    private Consumer handler;
    private Set<StartEndPair> startEndPairs;
    private RandomAccessFile randomAccessFile;

    private BigFileReader(File file, Consumer handler, String charset, int bufferSize, int threadSize) {
        this.fileLength = file.length();
        this.charset = charset;
        this.threadSize = threadSize;
        this.bufferSize = bufferSize;
        this.handler = handler;

        try {
            randomAccessFile = new RandomAccessFile(file, "r");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.executorService = Executors.newFixedThreadPool(threadSize);

        startEndPairs = new HashSet<>();
    }

    public void start(){

    }

    public void shutdown(){

    }

    public void calculateStartEnd(long start,long size){

    }

    public static class Builder{
        private int threadSize=1;
        private String charset = null;
        private int bufferSize = 1024*1024;
        private Consumer handler;
        private File file;
        public Builder(String file,Consumer handler){
            this.file = new File(file);
            if(!this.file.exists()){
                throw new IllegalArgumentException("文件不存在！");
            }
            this.handler = handler;
        }

        public Builder setThreadSize(int threadSize) {
            this.threadSize = threadSize;
            return this;
        }

        public Builder setCharset(String charset) {
            this.charset = charset;
            return this;
        }

        public Builder setBufferSize(int bufferSize) {
            this.bufferSize = bufferSize;
            return this;
        }

        public BigFileReader build(){
            return new BigFileReader(this.file,this.handler,this.charset,this.bufferSize,this.threadSize);
        }
    }

    private class SliceReaderTask implements Runnable{

        @Override
        public void run() {

        }
    }


    private static class StartEndPair {
        public long start;
        public long end;

        @Override
        public String toString() {
            return "start=" + start + ";end=" + end;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            StartEndPair that = (StartEndPair) o;
            return start == that.start &&
                    end == that.end;
        }

        @Override
        public int hashCode() {
            return Objects.hash(start, end);
        }
    }
}
