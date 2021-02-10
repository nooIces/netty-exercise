package com.example.netty.demo.tomcat.tradition;

public class TraditionDemoAServlet extends TraditionServlet{

    @Override
    public void doGet(TraditionRequest request, TraditionResponse response) throws Exception {
        this.doPost(request, response);
    }

    @Override
    public void doPost(TraditionRequest request, TraditionResponse response) throws Exception {
        response.write("this is traditional servlet!");
    }
}
