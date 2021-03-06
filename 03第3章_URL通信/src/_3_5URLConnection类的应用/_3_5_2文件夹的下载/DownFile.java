package _3_5URLConnection类的应用._3_5_2文件夹的下载;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * 
 * Copyright ? 2016 Authors. All rights reserved.
 *
 * FileName: .java
 * @author : Wu_Being <1040003585@qq.com>
 * Date/Time: 2016-6-6/下午10:09:39
 * Description:
 */
public class DownFile implements ActionListener {
	
	private JFrame mainJFrame;
	private Container con;
	private JLabel msgLbl;
	private JTextField urlText;
	private JButton btn;

	DownFile() {
		mainJFrame = new JFrame("文件下载");
		con = mainJFrame.getContentPane();
		msgLbl = new JLabel("请输入要下载的文件地址和名称");
		urlText = new JTextField();
		urlText.setColumns(15);
		btn = new JButton("下载");
		btn.addActionListener(this);
		con.setLayout(new FlowLayout());
		con.add(msgLbl);
		con.add(urlText);
		con.add(btn);
		mainJFrame.setSize(300, 200);
		mainJFrame.setVisible(true);
		mainJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new DownFile();
	}

	/**
	 * 监听button
	 */
	public void actionPerformed(ActionEvent e) {
		try {
			System.out.println(urlText.getText());//https://www.baidu.com
			URL url = new URL(urlText.getText());
			URLConnection connection = url.openConnection();
			int c;
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			BufferedWriter bw = new BufferedWriter(new FileWriter("temp.dat"));
			while((c = br.read()) != -1) {
				bw.write(c);
			}
			br.close();
			bw.close();
			JOptionPane.showMessageDialog(mainJFrame, "下载成功");
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}