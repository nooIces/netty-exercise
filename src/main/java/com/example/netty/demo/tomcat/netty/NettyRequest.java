package com.example.netty.demo.tomcat.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;


public class NettyRequest {

    private ChannelHandlerContext ctx;
    private HttpRequest request;

    public NettyRequest(ChannelHandlerContext ctx, HttpRequest request){
        this.ctx = ctx;
        this.request = request;
    }

    public String getUrl(){
        return request.uri();
    }

    public String getMethod(){
        return request.method().name();
    }
}
