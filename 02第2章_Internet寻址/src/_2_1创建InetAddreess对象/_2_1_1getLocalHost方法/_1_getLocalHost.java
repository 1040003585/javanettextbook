package _2_1创建InetAddreess对象._2_1_1getLocalHost方法;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class _1_getLocalHost {

	/**
	 * @param args
	 **/
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("hello world!!!");
		try {
			InetAddress localAddress = InetAddress.getLocalHost();
			System.out.println(localAddress);
		} catch (UnknownHostException el) {
			el.printStackTrace();
		}
	}
	/*
	 * output:
	 * hello world!!!
	 * wubeing-EC18-Series/127.0.1.1
	 */

}
