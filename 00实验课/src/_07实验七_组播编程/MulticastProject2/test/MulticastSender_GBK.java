package _07实验七_组播编程.MulticastProject2.test;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Date;

/**
 *  用gbk格式发送中文数据后，接收会有乱码
 * Copyright ? 2016 Authors. All rights reserved.
 *
 * FileName: .java
 * @author : Wu_Being <1040003585@qq.com>
 * Date/Time: 2016-6-14/下午08:58:36
 * Description:
 */
public class MulticastSender_GBK {
	   
    public static void server() throws Exception{  
        InetAddress group = InetAddress.getByName("239.1.2.3");//组播地址  
        int port = 22363;  
        MulticastSocket mss = null;  
        try {  
            mss = new MulticastSocket(port);  
            mss.joinGroup(group);  
            System.out.println("发送数据启动send data starting..."+new Date()+")");  
              
            while(true){  
                String message = "Hello##你好###"+new Date();  
                byte[] buffer = message.getBytes();  
                DatagramPacket dp = new DatagramPacket(buffer, buffer.length,group,port);  
                mss.send(dp);  
                System.out.println("send（"+message+"） to "+group+":"+port);  
                Thread.sleep(1);  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }finally{  
            try {  
                if(mss!=null){  
                    mss.leaveGroup(group);  
                    mss.close();  
                }  
            } catch (Exception e2) {  
                // TODO: handle exception  
            }  
        }  
    }  
      
    public static void main(String[] args) throws Exception {  
        server();  
    }  
}
