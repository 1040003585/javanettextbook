import java.net.InetAddress;
import java.net.UnknownHostException;

public class MyInetAddress {

	/**
	 * @param args
	 *
	 *import java.net.InetAddress;
	public final static class InetAddress extends Object implements Serializable{
		public static InetAddress getLocalHost() throws UnknownHostException {
			return null;
		}
		...
		public String toString(){
			return ((hostName!=null)?hostName:"")+"/"getHostAddress();
		}
	}*/
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
	 * hello world!!!
	 * wubeing-EC18-Series/127.0.1.1
	 */

}
