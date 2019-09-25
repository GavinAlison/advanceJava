1. Channel and Buffer
2. Scatter and Gather
3. Channel Transfers
4. Socket and ServerSocket
5. DatagramChannel
6. Selector




##  说明文档
http://tutorials.jenkov.com/java-nio/channels.html

### nio channel 
-   FileChannel


## nio系列
-   channel and buffer  
标准的IO基于字节流和字符流进行操作的，而NIO是基于通道（Channel）和缓冲区（Buffer）进行操作，数据总是从通道读取到缓冲区中，或者从缓冲区写入到通道中。
-   nio 
Java NIO可以让你非阻塞的使用IO，例如：当线程从通道读取数据到缓冲区时，线程还是可以进行其他事情。
当数据被写入到缓冲区时，线程可以继续处理它。从缓冲区写入通道也类似。
-   Selectors       
Java NIO引入了选择器的概念，选择器用于监听多个通道的事件（比如：连接打开，数据到达）。
因此，单个的线程可以监听多个数据通道。
-   


