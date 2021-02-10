package com.example.netty.demo.tomcat.tradition;


public abstract class TraditionServlet {

    public void service(TraditionRequest request, TraditionResponse response) throws Exception {
        if("GET".equalsIgnoreCase(request.getMethod())){
            doGet(request, response);
        } else {
            doPost(request, response);
        }
    }

    public abstract void doGet(TraditionRequest request, TraditionResponse response) throws Exception;

    public abstract void doPost(TraditionRequest request, TraditionResponse response) throws Exception;
}
