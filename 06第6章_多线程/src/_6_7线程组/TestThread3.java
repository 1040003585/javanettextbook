package _6_7线程组;

public class TestThread3 extends TestThread1 {

	public TestThread3(ThreadGroup g, String name) {
		super(g, name);
		start();
	}

	public void run() {
		// 1.用getParent()向上移动两级到xGroup1name
		ThreadGroup group = getThreadGroup().getParent().getParent();
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>00");
		group.list();// output
		// 2.调用activeCount()查询这个线程组及所有子线程组内有多少个线程，
		// 从而创建由指定Thread的句柄构成一个数组。
		Thread[] groupThreads = new Thread[group.activeCount()];
		// 3.enumerate()方法将指向所有这些线程的句柄置入数组groupThreads里
		group.enumerate(groupThreads);
		// 4.然后调用每个线程的f()方法，同时修改优先级
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>11");
		for (int i = 0; i < groupThreads.length; i++) {
			groupThreads[i].setPriority(MIN_PRIORITY);
			((TestThread1) groupThreads[i]).f();// output
		}
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>22");
		group.list();// output
	}

}
