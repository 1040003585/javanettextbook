package _6_7�߳���;

public class TestThread1 extends Thread {
	private int i;
	TestThread1(ThreadGroup g, String name) {
		super(g, name);
	}
	void f() {
		i++;
		System.out.println(getName() + " f()");
	}
}
