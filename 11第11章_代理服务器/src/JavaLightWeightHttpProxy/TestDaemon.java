package JavaLightWeightHttpProxy;

import javax.swing.plaf.SliderUI;

public class TestDaemon {
	public static int count = 0;

	public static void main(String[] args) {

		Thread thread = new Thread(new Runnable() {
			public void run() {
				System.out.println("334234");
				while (true) {
					count++;
					System.out.println(count);
				}
			}
		});
		thread.setDaemon(true);
		thread.start();
		while(true){
			try {
				Thread.sleep(10);
				System.out.println("fjfffffffffffffffffffffffffffffffffffff");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
