## commnad

client1
```
>subscribe CCTV1

reading message ... press Ctrl - C to quit
1) "subscribe"
2) "CCTV1"
3) (integer) 1 
```

client2
```
>subscribe CCTV1 CCTV2
reading message .. press Ctrl -C to quit
1) "subscribe"
2) "CCTV1"
3) (integer) 1 
1) "subscribe"
2) "CCTV2"
3) (integer) 2 
```


client3
```
>publish CCTV1 "cctv1 is good"
(integer) 2
```
client1  receive message
```
1) "message"
2) "CCTV1"
3) "cctv1 is good"
```
client1  receive message
```
1) "message"
2) "CCTV1"
3) "cctv1 is good"
```

## jedis pubsub
implement ---> PubsubService

## spring redis pubsub
>link: https://www.cnblogs.com/linjiqin/p/6277537.html---failure
>link: https://www.tianmaying.com/tutorial/springboot-redis-message -- success



