//--------------- 文件名：Client.java ----------------------------------------------------------------
package _4_3Socket编程示例._4_4_2一对多的通信;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * _4_3Socket编程示例._4_4_2一对多的通信;
 * Copyright ? 2016 Authors. All rights reserved.
 *
 * FileName: .java
 * @author : Wu_Being <1040003585@qq.com>
 * Date/Time: 2016-6-8/上午12:15:10
 * Description: 客户端输入圆的半径，发送到用服务器计算，并收到结果
 */
public class Client {
	
	public static void main(String[] args) {
		
		try {
			//创建连接到服务器的socket，服务器IP和端口如下
			Socket connectToServerSocket = new Socket("localhost",9955);
			//将数据输入输出流连接到socket上
			DataInputStream inFromServer = new DataInputStream(connectToServerSocket.getInputStream());
			DataOutputStream outToServer = new DataOutputStream(connectToServerSocket.getOutputStream());
			
			System.out.println("C输入半径数值发送到服务器，输入end结束");
			boolean goon=true;
			//数据从终端输入
			BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
			//反复读用户的数据并计算
			while(goon){
				String outString=bf.readLine();		//数据从终端输入
				outToServer.writeUTF(outString);			//写到Socket dos中
				outToServer.flush();						//清空缓冲区，立即发送
				String inString=inFromServer.readUTF();		//从Socket dis中读数据
				if(!inString.equals("end")){
					System.out.println("C从服务器返回的结果是：（"+inString+"）");
				}else{
					goon=false;
					System.out.println("C请求结束！！！");
				}
			}
			//关闭socket和流
			connectToServerSocket.close();
			inFromServer.close();
			outToServer.close();
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
