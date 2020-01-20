## Linux网络编程中的5中IO模型
- blocking IO（阻塞IO）
- non-blocking IO（非阻塞IO）
- IO multiplexing（多路复用IO）
- signal driven IO（信号驱动IO）
- asynchronous IO（异步IO）

## 概念区分
### 同步和异步

### 阻塞和非阻塞

### 面向流和面向缓冲

## nio核心概念
- Channel
- Buffer
- Selector

### Channel
基本上，所有的IO在NIO中都从一个Channel开始。数据可以从Channel读到Buffer中，也可以从Buffer写到Channel中
Channel和Buffer有好几种类型。下面是Java NIO中的一些主要Channel的实现：
- FileChannel(file)
- DatagramChannel(UDP)
- SocketChannel(TCP)
- ServerSocketChannel(TCP)
> 最后两个channel的关系。通过 ServerSocketChannel.accept() 方法监听新进来的连接。当 accept()方法返回的时候,它返回一个包含新进来的连接的 SocketChannel。因此, accept()方法会一直阻塞到有新连接到达。通常不会仅仅只监听一个连接,在while循环中调用 accept()方法.
```
//打开 ServerSocketChannel
ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
serverSocketChannel.socket().bind(new InetSocketAddress(9999));
while(true){
    SocketChannel socketChannel = serverSocketChannel.accept();
    //do something with socketChannel...
}
//关闭ServerSocketChannel
serverSocketChannel.close();
```
### Buffer
缓冲区本质上是一块可以写入数据，然后可以从中读取数据的内存。这块内存被包装成NIO Buffer对象，并提供了一组方法，用来方便的访问该块内存。
- Java NIO里关键的Buffer实现：
- ByteBuffer
- CharBuffer
- DoubleBuffer
- FloatBuffer
- IntBuffer
- LongBuffer
- ShortBuffer
为了理解Buffer的工作原理，需要熟悉它的三个属性：
- capacity
- position
- limit

#### capacity
代表Buffer的最大空间

#### position
写模式时，代表写到当前位置
读模式时，代表读到当前位置
写切换到读模式时，position变为0

#### limit
写模式时，Buffer的limit表示最多能往Buffer里写多少数据。写模式下，limit等于capacity。
读模式时，表示你最多能读到多少数据
写切换到读模式时，limit会被设置成写模式下的position值。

### Selector
Selector允许单线程处理多个 Channel
要使用Selector，得向Selector注册Channel，然后调用它的select()方法。这个方法会一直阻塞到某个注册的通道有事件就绪。一旦这个方法返回，线程就可以处理这些事件，事件例如有新连接进来，数据接收等。

## nio处理消息的核心思路
结合示例代码，总结NIO的核心思路：
1. NIO 模型中通常会有两个线程，每个线程绑定一个轮询器 selector ，在上面例子中serverSelector负责轮询是否有新的连接，clientSelector负责轮询连接是否有数据可读
2. 服务端监测到新的连接之后，不再创建一个新的线程，而是直接将新连接绑定到clientSelector上，这样就不用BIO模型中1w 个while循环在阻塞，参见(1)
3. clientSelector被一个 while 死循环包裹着，如果在某一时刻有多条连接有数据可读，那么通过clientSelector.select(1)方法可以轮询出来，进而批量处理，参见(2)

## nio-高效原理 
### 面向流、非阻塞、零拷贝、直接内存 

## nio-零拷贝，直接内存 
https://blog.csdn.net/y277an/article/details/98000132
https://www.cnblogs.com/qinghe123/p/12160405.html
零拷贝是指避免在用户态(User-space) 与内核态(Kernel-space) 之间来回拷贝数据的技术


## 理解epoll

## 理解reactor和proactor模型