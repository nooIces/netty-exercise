package com.example.netty.demo.io;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

@Slf4j
public class ChannelDemo {

    private static final String path = "src/main/java/com/example/netty/demo/io/";

    public static void main(String[] args) throws IOException {
        fileChannelDemo();
    }

    public static void fileChannelDemo() throws IOException {
        File sourceFile = new File(path + "sourceCopyFile.txt");
        File copyFile = new File(path + "copyResultFile.txt");
        if (!copyFile.exists()) {
            boolean isCreateSuccess = copyFile.createNewFile();
            log.info("create file {} success: {}", copyFile.getName(), isCreateSuccess);
        }
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            inputStream = new FileInputStream(sourceFile);
            outputStream = new FileOutputStream(copyFile);
            inChannel = inputStream.getChannel();
            outChannel = outputStream.getChannel();
            int length = -1;
            ByteBuffer buf = ByteBuffer.allocate(10);
            while ((length = inChannel.read(buf)) != -1) {
                log.info("read {} byte from {}", length, sourceFile.getName());
                buf.flip();
                int outLength = 0;
                while ((outLength = outChannel.write(buf)) != 0) {
                    log.info("write into {} {} byte", copyFile.getName(), outLength);
                }
                buf.clear();
            }
            // 强制刷新到磁盘
            outChannel.force(true);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if(outputStream != null) {
                outputStream.close();
            }
            if (inChannel != null) {
                inChannel.close();
            }
            if (outChannel != null) {
                outChannel.close();
            }
        }

    }

}
