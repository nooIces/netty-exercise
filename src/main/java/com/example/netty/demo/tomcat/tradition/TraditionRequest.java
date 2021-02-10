package com.example.netty.demo.tomcat.tradition;

import java.io.InputStream;

public class TraditionRequest {

    private String method;
    private String url;

    public TraditionRequest(InputStream input){
        try {
            String content = "";
            byte[] buffer = new byte[1024];
            int len;
            if((len = input.read(buffer)) > 0){
                content = new String(buffer, 0, len);
            }
            String line = content.split("\\n")[0];
            String[] arr = line.split("\\s");
            this.method = arr[0];
            this.url = arr[1].split("\\?")[0];
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getMethod(){
        return this.method;
    }

    public String getUrl(){
        return this.url;
    }
}
