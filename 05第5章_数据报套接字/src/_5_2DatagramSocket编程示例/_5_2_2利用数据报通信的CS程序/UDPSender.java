package _5_2DatagramSocket编程示例._5_2_2利用数据报通信的CS程序;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;

/**
 * 
 * Copyright ? 2016 Authors. All rights reserved.
 * 
 * FileName: .java
 * 
 * @author : Wu_Being <1040003585@qq.com> Date/Time: 2016-6-10/下午10:36:20
 *         Description:
 */
public class UDPSender {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		while (true) {
			Scanner scanner = new Scanner(System.in);
			try {
				System.out.print("请输入要发送的数据：");
				String string = scanner.nextLine();
				DatagramSocket sendSocket = new DatagramSocket();// 端口号随机（同port=0）
				// byte[] databyte=new byte[100]
				byte[] databyte = string.getBytes();
				//创建一个要发送数据的DatagramPacket对象
				DatagramPacket sentPacket = new DatagramPacket(databyte,
						databyte.length, InetAddress.getByName("127.0.0.1"),
						5000);
				//发送数据
				sendSocket.send(sentPacket);
				System.out.println("send the data:" + string);
			} catch (SocketException e) {
				System.out.println("不能打开数据报Socket,或数据报Socket无法与指定端口连接");
			} catch (IOException e) {
				System.out.println("网络通信出现问题，问题在于：" + e.toString());
			}

		}
	}
}
