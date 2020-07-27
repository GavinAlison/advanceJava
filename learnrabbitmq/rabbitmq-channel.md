## channel 的参数详解

> 记录一下，一遍忘记

**消费消息**

消费消息分为推模式和拉模式

**推模式**

`String basicConsume(String queue, boolean autoAck, String consumerTag, boolean noLocal, boolean exclusive, Map<String, Object> arguments, Consumer callback) throws IOException;`

queue：队列名称
autoAck：是否自动确认，建议关闭自动确认
consumerTag：消费者标签，用来区分多个消费者
noLocal：设置为true则表示不能将同一个Connection中生产者发送的消息传送给这个Connection中的消费者
exclusive：设置是否排他
arguments：设置消费者的其他参数
callback：设置消费者的回调函数。用来处理RabbitMQ推送过来的消息，比如DefaultConsumer，使用时需要客户端重写(override)其中的方法。

`public interface Consumer {    void handleConsumeOk(String consumerTag);    void handleCancelOk(String consumerTag);    void handleCancel(String consumerTag) throws IOException;    void handleShutdownSignal(String consumerTag, ShutdownSignalException sig);    void handleRecoverOk(String consumerTag);    void handleDelivery(String consumerTag,                        Envelope envelope,                        AMQP.BasicProperties properties,                        byte[] body)        throws IOException;}`

handleShutdownSignal：当Channel或者Connection关闭的时候会调用。
handleConsumeOk：会在其他方法之前调用，返回消费者标签
handleCancelOk：消费端可以在显式地或者隐式地取消订阅的时候调用
handleCancel：消费端可以在显式地或者隐式地取消订阅的时候调用
handleDelivery：用来处理队列推送的消息

消费者客户端的这些callback会被分配到与Channel不同的线程池上，这意味着消费者客户端可以安全地调用这些阻塞方法，比如 channel.queueDeclare、channel.basicCancel等。

**拉模式**

从消息队列中主动拉取一条message     

`GetResponse basicGet(String queue, boolean autoAck) throws IOException;`

queue：队列名称
autoAck：是否自动确认

**消费确认和拒绝**

RabbitMQ服务提供了消息确认机制，将autoAck参数设置为false，消费者可以有足够的时间处理消息。

`void basicAck(long deliveryTag, boolean multiple) throws IOException;`

deliveryTag：消息编号，64位的长整型值，最大值是9223372036854775807。
multiple：true:确认该消息编号之前的所有消息，false：只确认该消息编号的消息。

`void basicReject(long deliveryTag, boolean requeue) throws IOException;`

deliveryTag：消息编号，64位的长整型值，最大值是9223372036854775807。
requeue：是否重入队列。true:重新入队，false:直接删除消息

`void basicNack(long deliveryTag, boolean multiple, boolean requeue) throws IOException;`


批量拒绝消息

deliveryTag：消息编号，64位的长整型值，最大值是9223372036854775807。
requeue：是否重入队列。true:重新入队，false:直接删除消息
multiple：true:拒绝该消息编号之前的所有消息，false：只拒绝该消息编号的消息。

`Basic.RecoverOk basicRecover(boolean requeue) throws IOException;`

该方法用来请求RabbitMQ重新发送还未被确认的消息。如果requeue参数设置为true，则未被确认的消息会被重新加入到队列中，这样对于同一条消息来说，可能会被分配给与之前不同的消费者。 如果requeue参数设置为false，那么同一条消息会被分配给与之前相同的消费者。默认情况下，如果不设置requeue这个参数，相当于channel.basicRecover(true)，即requeue默认为true。

**关闭连接**

Connection和Channel所具备的生命周期如下所述：

open：开启状态，代表该对象可用
closing：正在关闭状态，当前对象被显式地通知调用关闭方法，这样就产生了一个关闭请求让其内部对象进行相应的操作，并等待这些关闭操作的完成。
closed：关闭状态。当前对象己经接收到所有的内部对象己完成关闭动作的通知，并且其也关闭了自身。
可以为Connection和Channel添加一个ShutdownListener，当Connection和Channel关闭以后，将会通知ShutdownListener监听器。

Connection和Channel的getCloseReason方法可以获取关闭的原因。
`@FunctionalInterfacepublic interface ShutdownListener extends EventListener {    void shutdownCompleted(ShutdownSignalException cause);}`

cause的isHardError的方法如果返回true代表是Connection的错误，如果是false是Channel的错误。

cause的getReference可以获取Connection或者Channel。


>来源： https://cloud.tencent.com/developer/article/1469326

