package com.example.netty.demo.tomcat.tradition;

import com.example.netty.demo.tomcat.AbstractTomcat;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TraditionTomcat extends AbstractTomcat {
    private ServerSocket server;

    public void start(){
        init();
        try {
            server = new ServerSocket(this.port);
            System.out.println("Tradition Tomcat is started, listen on : " + this.port);
            while(true){
                Socket client = server.accept();
                process(client);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void process(Socket client) throws Exception {
        InputStream input = client.getInputStream();
        OutputStream output = client.getOutputStream();

        TraditionRequest request = new TraditionRequest(input);
        TraditionResponse response = new TraditionResponse(output);

        String url = request.getUrl();
        if(servletMapping.containsKey(url)){
            servletMapping.get(url).service(request, response);
        } else {
            response.write("404 - Not Found");
        }

        output.flush();
        output.close();

        input.close();
        client.close();
    }

    public static void main(String[] args) {
        TraditionTomcat tomcat = new TraditionTomcat();
        tomcat.start();
    }
}
