package _5_2DatagramSocket编程示例._5_2_1利用DatagramSocket查询端口占用情况;


import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * 
 * Copyright ? 2016 Authors. All rights reserved.
 *
 * FileName: .java
 * @author : Wu_Being <1040003585@qq.com>
 * Date/Time: 2016-6-10/上午12:36:45
 * Description: 查询端口的占用情�?
 */
public class UDPScan {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		//2014~65535
		for (int port = 1024; port < 65536; port++) {//0~0xFFFF
			try {
				DatagramSocket server = new DatagramSocket(port);
				server.close();
			} catch (SocketException e) {
				System.out.println("there is a server in port:" + port + ".");
			}

			
		}

	}

}
