package com.example.netty.demo.tomcat.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpRequest;

import java.util.Map;

public class NettyTomcatHandler extends ChannelInboundHandlerAdapter {

    protected Map<String, NettyServlet> servletMapping;

    public NettyTomcatHandler(Map<String, NettyServlet> servletMapping){
        this.servletMapping = servletMapping;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof HttpRequest){
            HttpRequest req = (HttpRequest) msg;
            NettyRequest request = new NettyRequest(ctx, req);
            NettyResponse response = new NettyResponse(ctx, req);
            String url = request.getUrl();
            if(servletMapping.containsKey(url)){
                servletMapping.get(url).service(request, response);
            } else {
                response.write("404 - Not Found");
            }
        }
    }

}
