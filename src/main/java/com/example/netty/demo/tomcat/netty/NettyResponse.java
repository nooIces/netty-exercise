package com.example.netty.demo.tomcat.netty;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

import java.nio.charset.StandardCharsets;

public class NettyResponse {

    private ChannelHandlerContext ctx;
    private HttpRequest request;

    public NettyResponse(ChannelHandlerContext ctx, HttpRequest request){
        this.ctx = ctx;
        this.request = request;
    }

    public void write(String out) throws Exception {
        try {
            if (out == null || out.length() == 0){
                return;
            }
            FullHttpResponse response = new DefaultFullHttpResponse(
                    HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK,
                    Unpooled.wrappedBuffer(out.getBytes(StandardCharsets.UTF_8)));
            response.headers().set("Content-Type", "text/html;");
            ctx.write(response);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            ctx.flush();
            ctx.close();
        }
    }
}
