package _3_3URL类的应用._3_3_2浏览器的设计;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.HyperlinkEvent;


public class MyBrowser implements ActionListener {

	JLabel msgLbl;
	JTextField urlText; // 给用户输入URL
	JEditorPane content; // 显示网页内容
	JScrollPane jSPane;
	JPanel panel;
	Container con;
	JFrame mainJfFrame;

	/**
	 * 构造方法，用于程序界面的布局
	 */
	public MyBrowser() {
		mainJfFrame=new JFrame("我的浏览器");
		mainJfFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		con=mainJfFrame.getContentPane();
		msgLbl=new JLabel("请输入地址：");
		urlText=new JTextField();
		urlText.setColumns(20);
		urlText.addActionListener(this);//actionPerformed
		
		System.out.println("hello");//
	}

	/**
	 * 当用户按下回车键后，调用此方法
	 * 
	 * @param e
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			URL url = new URL(urlText.getText()); // 根据用户输入构造URL对象
			content.setPage(url);// 获取网页内容并显示
		} catch (MalformedURLException e1) {
			System.out.println(e1);
		} catch (IOException e2) {
			JOptionPane.showMessageDialog(mainJfFrame, "连接错误");
		}
	}

	/**
	 * 实现hyperlinkUpdate方法，当用户点击网页上的链接时，系统将调用此方法
	 * 
	 * @param e
	 */
	public void hyperlinkUpdate(HyperlinkEvent e) {
		if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
			try {
				URL url = e.getURL(); // 获取用户点击的URL
				content.setPage(url); // 跳转到新页面
				urlText.setText(e.getURL().toString()); // 更新用户输入框的URL
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(mainJfFrame, "连接错误");
			}

		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MyBrowser();

	}
}