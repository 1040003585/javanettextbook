import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class _4_1_1����Socket���� {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Socket socket = new Socket("127.0.0.1", 9090);
			InetAddress address=socket.getInetAddress();
			System.out.println(address);
			System.out.println("hello");
		} catch (UnknownHostException e) {
			System.out.println("hello2");
//			e.printStackTrace();
			System.err.println(e);
		} catch (IOException e) {
			System.out.println("hello3");
			e.printStackTrace();
			System.err.println(e);
		}
		

	}

}
