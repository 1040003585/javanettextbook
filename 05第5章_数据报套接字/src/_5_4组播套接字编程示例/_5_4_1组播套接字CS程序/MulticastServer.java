package _5_4�鲥�׽��ֱ��ʾ��._5_4_1�鲥�׽���CS����;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;

public class MulticastServer {

	public static void main(String[] args) {
		
		try {
			MulticastSocket multicastSocket=new MulticastSocket();
			DatagramPacket p=new DatagramPacket("sfsd".getBytes(),1);
			multicastSocket.receive(p);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
