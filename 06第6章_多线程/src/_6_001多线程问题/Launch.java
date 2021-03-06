package _6_001多线程问题;

/**
 * 
 * Copyright ? 2016 Authors. All rights reserved.
 *
 * FileName: .java
 * @author : Wu_Being <1040003585@qq.com>
 * Date/Time: 2016-6-8/下午11:05:29
 * Description:
 */
public class Launch {
	public static void main(String[] args) {
		Son son = new Son("son");
		// son.start();
		son.startup();
		/**
		 * 虽然调用的是super.start(),但是调用这个方法的依旧是son这个子类对象， 线程执行的是son这个对象的run()方法，
		 * 你在son类里已经重写了run方法所以，执行的就是son这个子类的run()方法了。
		 * 
		 * 想打印出mother必须调用super.run();
		 */
		// Mother mother = new Mother("mother");
		// mother.start();
	}
}