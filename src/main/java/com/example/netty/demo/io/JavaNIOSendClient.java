package com.example.netty.demo.io;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

@Slf4j
public class JavaNIOSendClient {

    public static void startClient() throws IOException {
        InetSocketAddress address = new InetSocketAddress("localhost", 8080);
        SocketChannel socketChannel = SocketChannel.open(address);
        socketChannel.configureBlocking(false);
        while (!socketChannel.finishConnect()) {
            log.info("努力连接中...");
        }
        log.info("connect success");
        ByteBuffer buffer = ByteBuffer.allocate(26);
        buffer.put("abcdefghijklmnopqrstuvwxyz".getBytes(StandardCharsets.UTF_8));
        buffer.flip();
        socketChannel.write(buffer);
        socketChannel.shutdownOutput();
        socketChannel.close();
    }

    public static void main(String[] args) throws IOException {
        startClient();
    }

}
