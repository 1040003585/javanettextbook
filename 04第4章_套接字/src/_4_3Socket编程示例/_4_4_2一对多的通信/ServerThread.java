//------------------- 文件名ServerThread.java ------------------------------------------------
package _4_3Socket编程示例._4_4_2一对多的通信;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * _4_3Socket编程示例._4_4_2一对多的通信;
 * Copyright ? 2016 Authors. All rights reserved.
 *
 * FileName: ServerThread.java
 * @author : Wu_Being <1040003585@qq.com>
 * Date/Time: 2016-6-7/下午11:11:32
 * Description: 利用本线程来完成服务器与客户端的通信工程
 */
public class ServerThread extends Thread {

	private Socket connectToClientSocket;
	private DataInputStream inFromClient;
	private DataOutputStream outToClient;
	private int clientname=1;	//不能用static，否则所用线程会共用clienti变量
	
	/**
	 * 构造函数1
	 * @param socket
	 * @throws IOException
	 */
	public ServerThread(Socket socket) throws IOException {
		super();
		connectToClientSocket=socket;
		inFromClient=new DataInputStream(connectToClientSocket.getInputStream());
		outToClient=new DataOutputStream(connectToClientSocket.getOutputStream());
		System.out.println("S连接请求来自："+connectToClientSocket.getInetAddress().getHostAddress());
		start();		//启动run()方法
	}
	/**
	 * 构造函数2
	 * @param socket
	 * @param ci 连接的第i个客户端
	 * @throws IOException
	 */
	public ServerThread(Socket socket,int cname) throws IOException {
		super();
		connectToClientSocket=socket;
		clientname=cname;
		inFromClient=new DataInputStream(connectToClientSocket.getInputStream());
		outToClient=new DataOutputStream(connectToClientSocket.getOutputStream());
		System.out.println("S连接请求来自"+clientname+"："+connectToClientSocket.getInetAddress().getHostAddress());
		start();		//启动run()方法
	}
	
	/**
	 * 在run()方法与客户端通信
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		System.out.println("S连接Clinet"+clientname+"......");
		try {
			boolean goon=true;
			while(goon){		
				String string=inFromClient.readUTF();			//阻塞等待从socket中读取数据
				if(string.equals("end")==false){
					string=dealWith(string,clientname);					//服务器执行特定功能
					outToClient.writeUTF(string);				//向socket dos写数据
					outToClient.flush();						//清空缓冲区，立即发送
					System.out.println("SC"+clientname+"计算结果（"+string+"）已经发送");
				}else{
					goon=false;
					outToClient.writeUTF("end");
					outToClient.flush();
					System.out.println("SC"+clientname+"服务结束！！！");
				}

			}

			//关闭socket和流
			connectToClientSocket.close();
			inFromClient.close();
			outToClient.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * 服务器执行特定功能函数，这里是个计算圆面积的功能
	 * @param string 圆的半径
	 * @return 圆的面积
	 */
	public static String dealWith(String string,int ci){
		System.out.print("SC"+ci+"接收到的半径值（"+string+"）；");	
		double radius=0.0;
		try {
			radius=Double.parseDouble(string);			
		} catch (NumberFormatException e) {
			return "输入数据格式不对！";
		}
		if(radius<0)return "数据数据不能小于0！";
		double area=radius*radius*Math.PI;
		return Double.toString(area);
	}
	
}

