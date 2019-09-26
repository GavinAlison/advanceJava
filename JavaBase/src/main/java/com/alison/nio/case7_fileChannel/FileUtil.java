package com.alison.nio.case7_fileChannel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class FileUtil {
    private static final long MAX_TRANSFER_SIZE = 1024 * 10;

    /**
     * Copy a single file using NIO.
     * @throws IOException
     */
    private static void nioCopy(FileOutputStream fos, FileInputStream fis)
            throws IOException {
        FileChannel outChannel = fos.getChannel();
        FileChannel inChannel = fis.getChannel();
        long length = inChannel.size();
        long offset = 0;
        while(true) {
            long remaining = length - offset;
            long toTransfer = remaining < MAX_TRANSFER_SIZE ? remaining : MAX_TRANSFER_SIZE;
            long transferredBytes = inChannel.transferTo(offset, toTransfer, outChannel);
            offset += transferredBytes;
            length = inChannel.size();
            if(offset >= length) {
                break;
            }
        }
    }
}
