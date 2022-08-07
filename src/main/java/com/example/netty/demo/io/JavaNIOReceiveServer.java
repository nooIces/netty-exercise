package com.example.netty.demo.io;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

@Slf4j
public class JavaNIOReceiveServer {

    private static final String path = "src/main/java/com/example/netty/demo/io/";

    private Charset charset = StandardCharsets.UTF_8;

    private ByteBuffer buf = ByteBuffer.allocate(10);

    public void startServer() throws IOException {
        // 获取选择器
        Selector selector = Selector.open();
        // 获取通道
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverChannel.socket();
        // 设置为非阻塞
        serverChannel.configureBlocking(false);
        // 绑定连接
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 8080);
        serverSocket.bind(address);
        log.info("bind success, {} {} {}", address.getHostName(), address.getHostString(), address.getPort());
        // 注册到选择器上，并注册IO事件为"接收新连接"
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        int num = 0;
        while ((num = selector.select()) > 0) {
            log.info("selector num: {}", num);
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            while (it.hasNext()) {
                SelectionKey key = it.next();
                log.info("get a key: {}", key);
                if (key.isAcceptable()) {
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = server.accept();
                    if (socketChannel == null) {
                        continue;
                    }
                    log.info("{} connect success", socketChannel.getRemoteAddress());
                } else if (key.isReadable()) {
                    processData(key);
                    key.cancel();
                }
                it.remove();
                log.info("remove key");
            }
        }
        log.info("finish");
    }

    private void processData(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        int length = -1;
        File copyFile = new File(path + "remoteCopyFile.txt");
        if (!copyFile.exists()) {
            boolean isCreateSuccess = copyFile.createNewFile();
            log.info("create file {} success: {}", copyFile.getName(), isCreateSuccess);
        }
        FileOutputStream outputStream = null;
        FileChannel outChannel = null;
        try {
            outputStream = new FileOutputStream(copyFile);
            outChannel = outputStream.getChannel();
            int totalLength = 0;
            while ((length = socketChannel.read(buf)) != -1) {
                buf.flip();
                int outLength = 0;
                while ((outLength = outChannel.write(buf)) != 0) {
                    log.info("write into {} {} byte", copyFile.getName(), outLength);
                }
                buf.clear();
                totalLength += length;
                if (totalLength >= 26) {
                    break;
                }
            }
            // 强制刷新到磁盘
            outChannel.force(true);
        } finally {
            if(outputStream != null) {
                outputStream.close();
            }
            if (outChannel != null) {
                outChannel.close();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        JavaNIOReceiveServer server = new JavaNIOReceiveServer();
        server.startServer();
    }
}
