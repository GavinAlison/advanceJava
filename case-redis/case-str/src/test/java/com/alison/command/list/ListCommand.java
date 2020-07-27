package com.alison.command.list;

import com.alison.command.BaseConnection;
import org.junit.Test;
import redis.clients.jedis.ListPosition;

import java.time.LocalDateTime;

public class ListCommand extends BaseConnection {

    /**
     * 将所有指定的值插入到存于 key 的列表的头部。如果 key 不存在，那么在进行 push 操作前会创建一个空列表。 如果 key 对应的值不是一个 list 的话，那么会返回一个错误。
     * LPUSH key value [value ...]
     * 返回值：在 push 操作后的 list 长度。
     *
     * @see #rPop()
     */
    @Test
    public void lPush() {
    }

    /**
     * 向存于 key 的列表的尾部插入所有指定的值。如果 key 不存在，那么会创建一个空的列表然后再进行 push 操作。 当 key 保存的不是一个列表，那么会返回一个错误。
     * RPUSH key value [value ...]
     * 返回值：在 push 操作后的列表长度。
     * 命令：
     *
     * @see #rPop()
     */
    @Test
    public void rPush() {
    }

    /**
     * 只有当 key 已经存在并且存着一个 list 的时候，在这个 key 下面的 list 的头部插入 value。 与 LPUSH 相反，当 key 不存在的时候不会进行任何操作。
     * LPUSHX key value
     * 返回值：在 push 操作后的列表长度。
     * 命令：
     *
     * @see #rPop()
     */
    @Test
    public void lPushx() {
    }

    /**
     * 将值 value 插入到列表 key 的表尾, 当且仅当 key 存在并且是一个列表。 和 RPUSH 命令相反, 当 key 不存在时，RPUSHX 命令什么也不做。
     * RPUSHX key value
     * 返回值：
     * 命令：
     *
     * @see #rPop()
     */
    @Test
    public void rPushx() {
    }

    /**
     * 移除并且返回 key 对应的 list 的第一个元素。
     * LPOP key
     * 返回值：返回第一个元素的值，或者当 key 不存在时返回 nil。
     * 命令：
     *
     * @see #rPop()
     */
    @Test
    public void lPop() {
    }

    /**
     * 移除并返回存于 key 的 list 的最后一个元素。
     * RPOP key
     * 返回值： 最后一个元素的值，或者当 key 不存在的时候返回 nil。
     * 命令：
     * lpushx listKey headValue
     * rpushx listKey endValue
     * llen listKey
     * <p>
     * lpush listKey headValue1 headValue2
     * rpush listKey endValue1 endValue2
     * lpushx listKey headValue
     * rpushx listKey endValue
     * llen listKey
     * <p>
     * lpop listKey
     * rpop listKey
     */
    @Test
    public void rPop() {
        jedis.lpushx("listKey", "headValue");
        jedis.rpushx("listKey", "endValue");

        jedis.lpush("listKey", "headValue1", "headValue2");
        jedis.rpush("listKey", "endValue1", "endValue2");

        listOperations.leftPushIfPresent("listKey", "headValue");
        listOperations.rightPushIfPresent("listKey", "endValue");

        System.out.println(jedis.llen("listKey"));
        System.out.println(listOperations.size("listKey"));

        System.out.println(jedis.lpop("listKey"));
        System.out.println(jedis.rpop("listKey"));

        System.out.println(listOperations.leftPop("listKey"));
        System.out.println(listOperations.rightPop("listKey"));

    }

    /**
     * 返回存储在 key 的列表里指定范围内的元素。
     * LRANGE key start stop
     * 返回值： 指定范围里的列表元素。
     * 命令：
     * rpush lRangeKey 1 2 3 4 5
     * lRange lRangeKey 0 -1
     */
    @Test
    public void lRange() {
        jedis.rpush("lRangeKey", "1", "2", "3", "4", "5");

        System.out.println(jedis.lrange("lRangeKey", 0, -1));

        //spring redisTemplate
        System.out.println(listOperations.range("lRangeKey", 0, -1));
    }

    /**
     * 把 value 插入存于 key 的列表中在基准值 pivot 的前面或后面。当 key 不存在时，这个list会被看作是空list，任何操作都不会发生。当 key 存在，但保存的不是一个list的时候，会返回error。
     * LINSERT key BEFORE|AFTER pivot value
     * 返回值：经过插入操作后的list长度，或者当 pivot 值找不到的时候返回 -1。
     * 命令：
     * rpush lInsertKey 1 2 3 4 5
     * linsert lInsertKey before 3 22
     * linsert lInsertKey after 3 33
     * lrange lInsertKey 0 -1
     */
    @Test
    public void lInsert() {
        jedis.rpush("lInsertKey", "1", "2", "3", "4", "5");

        jedis.linsert("lInsertKey", ListPosition.BEFORE, "3", "22");
        jedis.linsert("lInsertKey", ListPosition.AFTER, "3", "33");

        System.out.println(jedis.lrange("lInsertKey", 0, -1));
    }

    /**
     * 原子性地返回并移除存储在 source 的列表的最后一个元素（列表尾部元素）， 并把该元素放入存储在 destination 的列表的第一个元素位置（列表头部）。
     * RPOPLPUSH source destination
     * 返回值：
     * 命令：
     * rpush rPoplPushKey 1 2 3 4 5
     * rpoplpush rPoplPushKey rPoplPushKey
     * lrange rPoplPushKey 0 -1
     */
    @Test
    public void rPoplPush() {
        jedis.rpush("rPoplPushKey", "1", "2", "3", "4", "5");

        jedis.rpoplpush("rPoplPushKey", "rPoplPushKey");
        listOperations.rightPopAndLeftPush("rPoplPushKey", "rPoplPushKey");

        System.out.println(jedis.lrange("rPoplPushKey", 0, -1));
    }

    /**
     * 返回列表里的元素的索引 index 存储在 key 里面。 下标是从0开始索引的
     * LINDEX key index
     * 返回值：请求的对应元素，或者当 index 超过范围的时候返回 nil。
     * 命令：
     * rpush lIndexKey 1 2 3
     * lindex lIndexKey 1
     * lindex lIndexKey 2
     */
    @Test
    public void lIndex() {
        jedis.rpush("lIndexKey", "1", "2", "3");

        System.out.println(jedis.lindex("lIndexKey", 1));
        System.out.println(listOperations.index("lIndexKey", 2));
    }

    /**
     * 设置 index 位置的list元素的值为 value。 当index超出范围时会返回一个error。
     * LSET key index value
     * 返回值：
     * 命令：
     * rpush lSetKey 1 2 3
     * lset lSetKey 0 4
     * lset lSetKey 2 6
     * lrange lSetKey 0 -1
     */
    @Test
    public void lSet() {
        jedis.rpush("lSetKey", "1", "2", "3");

        jedis.lset("lSetKey", 0, "4");
        listOperations.set("lSetKey", 2, "6");

        System.out.println(jedis.lrange("lSetKey", 0, -1));
    }


    /**
     * 返回存储在 key 里的list的长度。 如果 key 不存在，那么就被看作是空list，并且返回长度为 0。 当存储在 key 里的值不是一个list的话，会返回error。
     * LLEN key
     * 返回值：key对应的list的长度。
     * 命令：
     * rpush lLenKey 1 2 3
     * llen lLenKey
     * llen key
     */
    @Test
    public void lLen() {
        jedis.rpush("lLenKey", "1", "2", "3");

        System.out.println(jedis.llen("lLenKey"));
        System.out.println(listOperations.size("lLenKey"));
    }


    /**
     * 从存于 key 的列表里移除前 count 次出现的值为 value 的元素。
     * count > 0: 从头往尾移除值为 value 的元素。
     * count < 0: 从尾往头移除值为 value 的元素。
     * count = 0: 移除所有值为 value 的元素。
     * LREM key count value
     * 返回值：被移除的元素个数。
     * 命令：
     * rpush lRemKey 0 1 0 2 0 3 0
     * lrem lRemKey 1 0
     * lrange lRemKey 0 -1
     * <p>
     * lrem lRemKey -1 0
     * lrange lRemKey 0 -1
     * <p>
     * lrem lRemKey 0 0
     * lrange lRemKey 0 -1
     */
    @Test
    public void lRem() {
        jedis.rpush("lRemKey", "0", "1", "0", "2", "0", "3", "0");

        jedis.lrem("lRemKey", 1, "0");
        System.out.println("使用jedis移除头部值为0的1个元素，结果：" + jedis.lrange("lRemKey", 0, -1));

        listOperations.remove("lRemKey", -1, "0");
        System.out.println("使用jedis移除尾部值为0的1个元素，结果：" + listOperations.range("lRemKey", 0, -1));

        listOperations.remove("lRemKey", 0, "0");
        System.out.println("使用jedis移除所有值为0的1个元素，结果：" + listOperations.range("lRemKey", 0, -1));
    }


    /**
     * 修剪(trim)一个已存在的 list(获取一个子list,类似于subList方法)。详见：https://redis.io/commands/ltrim
     * LTRIM key start stop
     * 返回值：
     * 命令：
     * rpush lTrimKey 1 2 3 4 5
     * ltrim lTrimKey 1 3
     * lrange lTrimKey 0 -1
     * <p>
     * ltrim lTrimKey 1 2
     * lrange lTrimKey 0 -1
     */
    @Test
    public void lTrim() {
        jedis.rpush("lTrimKey", "1", "2", "3", "4", "5");

        jedis.ltrim("lTrimKey", 1, 3);
        System.out.println(jedis.lrange("lTrimKey", 0, -1));

        listOperations.trim("lTrimKey", 1, 2);
        System.out.println(listOperations.range("lTrimKey", 0, -1));
    }


    /**
     * BLPOP 是阻塞式列表的弹出原语。 它是命令 LPOP 的阻塞版本，这是因为当给定列表内没有任何元素可供弹出的时候， 连接将被 BLPOP 命令阻塞。详见：https://redis.io/commands/blpop
     * BLPOP key [key ...] timeout
     * 返回值：
     * 命令：
     * blpop blPopKey 2
     * <p>
     * blpop blPopKey 0
     * <p>
     * 开启另外一个客户端插入
     * lpush blPopKey value
     */
    @Test
    public void blPop() {
        System.out.println(LocalDateTime.now());
        System.out.println(jedis.blpop(2, "blPopKey"));

        System.out.println(LocalDateTime.now());
        //开启线程进行插入
        new Thread(() -> {
            try {
                Thread.sleep(3000);
                //jedis.lpush("blPopKey", "value");这个jedis客户端会被阻塞
                listOperations.leftPush("blPopKey", "value");
                System.out.println("redisTemplate插入完成:" + LocalDateTime.now());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        System.out.println(LocalDateTime.now() + ",阻塞结束:" + jedis.blpop(0, "blPopKey"));
    }

    /**
     * BRPOP 是一个阻塞的列表弹出原语。 它是 RPOP 的阻塞版本，因为这个命令会在给定list无法弹出任何元素的时候阻塞连接。
     * BRPOP key [key ...] timeout
     * 返回值：
     * 命令：
     */
    @Test
    public void brPop() {
    }

    /**
     * BRPOPLPUSH 是 RPOPLPUSH 的阻塞版本。 当 source 包含元素的时候，这个命令表现得跟 RPOPLPUSH 一模一样。
     * 当 source 是空的时候，Redis将会阻塞这个连接，直到另一个客户端 push 元素进入或者达到 timeout 时限。
     * timeout 为 0 能用于无限期阻塞客户端。
     * BRPOPLPUSH source destination timeout
     * 返回值：
     * 命令：
     */
    @Test
    public void brPoplPush() {

    }
}

