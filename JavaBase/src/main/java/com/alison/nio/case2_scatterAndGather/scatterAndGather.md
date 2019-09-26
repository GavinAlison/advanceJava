## Scatter/Gather

-   分散（scatter）         
从 Channel 中读取是指在读操作时将读取的数据写入多个 buffer 中。因此，Channel 将从 Channel 中读取的数据 “分散（scatter）” 到多个 Buffer 中。
-   聚集（gather）          
写入 Channel 是指在写操作时将多个 buffer 的数据写入同一个 Channel，因此，Channel 将多个 Buffer 中的数据 “聚集（gather）” 后发送到 Channel。


scatter / gather 经常用于需要将传输的数据分开处理的场合，
例如传输一个由消息头和消息体组成的消息，你可能会将消息体和消息头分散到
不同的 buffer 中，这样你可以方便的处理消息头和消息体。



