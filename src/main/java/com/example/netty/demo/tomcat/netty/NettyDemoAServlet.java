package com.example.netty.demo.tomcat.netty;

import com.example.netty.demo.tomcat.tradition.TraditionRequest;
import com.example.netty.demo.tomcat.tradition.TraditionResponse;

public class NettyDemoAServlet extends NettyServlet {

    @Override
    public void doGet(NettyRequest request, NettyResponse response) throws Exception {
        this.doPost(request, response);
    }

    @Override
    public void doPost(NettyRequest request, NettyResponse response) throws Exception {
        response.write("this is netty servlet!");
    }
}
