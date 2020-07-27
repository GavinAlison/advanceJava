package com.alison.nio.case3_channelTransfers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

public class TestChannelTransfers {

    private static void transfer() {
        String fromPath = TestChannelTransfers.class.getResource("/fromFile.txt").getPath();
        String toPath = TestChannelTransfers.class.getResource("/toFile.txt").getPath();
        try (RandomAccessFile fromFile = new RandomAccessFile(fromPath, "rw");
             RandomAccessFile toFile = new RandomAccessFile(toPath, "rw");
             FileChannel fromchannel = fromFile.getChannel();
             FileChannel toChannel = toFile.getChannel()) {
//            transferFrom()方法可以将数据从源通道传输到FileChannel中。
//            how similar the example is to the previous. The only real difference
//            is the which FileChannel object the method is called on. The rest is the same.
//            方法的输入参数position表示从position处开始向目标文件写入数据，count表示最多传输的字节数。
//            如果源通道的剩余空间小于 count 个字节，则所传输的字节数要小于请求的字节数。此外要注意，
//            在SoketChannel的实现中，SocketChannel只会传输此刻准备好的数据（可能不足count字节）。
//            因此，SocketChannel可能不会将请求的所有数据(count个字节)全部传输到FileChannel中。
            toChannel.transferFrom(fromchannel, 0, fromchannel.size());
            fromchannel.transferTo(0, fromchannel.size(), toChannel);
            System.out.println(toChannel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void transferTo() {
        String fromFileName = "/fromFile.txt";
        String toFileName = "/toFile.txt";
        fromFileName = TestChannelTransfers.class.getResource(fromFileName).getPath();
        toFileName = TestChannelTransfers.class.getResource(toFileName).getPath();
        try (FileChannel in = new FileInputStream(fromFileName).getChannel();
             FileChannel out = new FileOutputStream(toFileName).getChannel()) {
            // 只是将in transfer to out，变化的是class下的文件，对于resources下的文件没有变
            in.transferTo(0, (int) in.size(), out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        transfer();
        transferTo();
    }
}
