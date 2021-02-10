package com.example.netty.demo;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class BufferSlice {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        for(int i = 0; i < 10; i++){
            buffer.put((byte) i);
        }
        // create child buffer
        buffer.position(3);
        buffer.limit(7);
        ByteBuffer slice = buffer.slice();

        for(int i = 0; i < slice.capacity(); i++){
            slice.put(i, (byte)(slice.get()*10));
        }
        buffer.clear();
        while(buffer.hasRemaining()){
            System.out.println(buffer.get());
        }
    }
}
