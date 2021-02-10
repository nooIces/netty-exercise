package com.example.netty.demo.tomcat.tradition;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class TraditionResponse {

    private OutputStream output;

    public TraditionResponse(OutputStream output){
        this.output = output;
    }

    public void write(String s) throws Exception {
        String str = "HTTP/1.1 200 OK\n" +
                "Content-Type: text/html;\n" +
                "\r\n" +
                s;
        output.write(str.getBytes(StandardCharsets.UTF_8));
    }
}
