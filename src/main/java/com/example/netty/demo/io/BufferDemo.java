package com.example.netty.demo.io;

import lombok.extern.slf4j.Slf4j;

import java.nio.IntBuffer;

@Slf4j
public class BufferDemo {

    public static void main(String[] args) {
        // 分配内存
        IntBuffer intBuffer = IntBuffer.allocate(20);
        printBufferAttributes(intBuffer, "allocate");
        // 写入数据
        for (int i = 0; i < 5; i++) {
            intBuffer.put(i);
        }
        printBufferAttributes(intBuffer, "put");
        // 转换为读模式
        intBuffer.flip();
        printBufferAttributes(intBuffer, "flip");
        // 读取数据
        for (int i = 0; i < 2; i++) {
            log.info("print: {}", intBuffer.get());
        }
        printBufferAttributes(intBuffer, "get 2");
        for (int i = 0; i < 3; i++) {
            log.info("print: {}", intBuffer.get());
        }
        printBufferAttributes(intBuffer, "get 3");
        // 重新读取数据
        intBuffer.rewind();
        printBufferAttributes(intBuffer, "rewind");

        // mark()方法配合reset()方法可以从mark的地方重新读取
        for (int i = 0; i < 2; i++) {
            log.info("print: {}", intBuffer.get());
        }
        printBufferAttributes(intBuffer, "again get 2");
        intBuffer.mark();
        printBufferAttributes(intBuffer, "mark");
        for (int i = 0; i < 3; i++) {
            log.info("print: {}", intBuffer.get());
        }
        printBufferAttributes(intBuffer, "again get 3");
        intBuffer.reset();
        printBufferAttributes(intBuffer, "reset");
        // 压缩未读数据
        intBuffer.compact();
        printBufferAttributes(intBuffer, "compact");
        for (int i = 0; i < 5; i++) {
            intBuffer.put(i + 5);
        }
        printBufferAttributes(intBuffer, " compact put");
        intBuffer.flip();
        for (int i = 0; i < 2; i++) {
            log.info("print: {}", intBuffer.get());
        }
        printBufferAttributes(intBuffer, "again and again 2");
        // 清空数据
        intBuffer.clear();
        printBufferAttributes(intBuffer, "clear");
    }

    private static void printBufferAttributes(IntBuffer intBuffer, String operation) {
        log.info("after {}------------------", operation);
        log.info("position: {}", intBuffer.position());
        log.info("limit: {}", intBuffer.limit());
        log.info("capacity: {}", intBuffer.capacity());
    }

}
