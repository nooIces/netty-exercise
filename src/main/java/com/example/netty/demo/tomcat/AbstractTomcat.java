package com.example.netty.demo.tomcat;

import com.example.netty.demo.tomcat.netty.NettyServlet;
import com.example.netty.demo.tomcat.tradition.TraditionServlet;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public abstract class AbstractTomcat {

    protected int port = 8080;
    protected Map<String, TraditionServlet> servletMapping = new HashMap<>();
    protected Map<String, NettyServlet> nettyServletMapping = new HashMap<>();
    private Properties properties = new Properties();

    protected void init(){
        try {
            FileInputStream fis = new FileInputStream("src/main/java/com/example/netty/demo/tomcat/web.properties");
            properties.load(fis);
            for(Object k : properties.keySet()){
                String key = k.toString();
                if(key.startsWith("tradition") && key.endsWith(".url")){
                    String servletName = key.replaceAll("\\.url$", "");
                    String url = properties.getProperty(key);
                    String className = properties.getProperty(servletName + ".className");
                    TraditionServlet servlet = (TraditionServlet) Class.forName(className).getDeclaredConstructor().newInstance();
                    servletMapping.put(url, servlet);
                }
                if(key.startsWith("netty") && key.endsWith(".url")){
                    String servletName = key.replaceAll("\\.url$", "");
                    String url = properties.getProperty(key);
                    String className = properties.getProperty(servletName + ".className");
                    NettyServlet servlet = (NettyServlet) Class.forName(className).getDeclaredConstructor().newInstance();
                    nettyServletMapping.put(url, servlet);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
