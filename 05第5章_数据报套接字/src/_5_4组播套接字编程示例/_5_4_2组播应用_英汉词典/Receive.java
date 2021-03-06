
package _5_4组播套接字编程示例._5_4_2组播应用_英汉词典;


import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * Copyright ? 2016 Authors. All rights reserved.
 *
 * FileName: .java
 * @author : Wu_Being <1040003585@qq.com>
 * Date/Time: 2016-6-14/下午10:51:10
 * Description:
 */
@SuppressWarnings("serial")
public class Receive extends Frame implements Runnable, ActionListener {

    int port;
    InetAddress group = null;
    MulticastSocket socket = null;
    Button startRece, stopRece;
    Thread thread = null;
    TextArea showReceiving, showReceived;
    boolean stoped = false;

    public Receive() {
        super("定时接收信息");
        thread = new Thread(this);
        startRece = new Button("start receive");
        stopRece = new Button("stop receive");
        startRece.addActionListener(this);
        stopRece.addActionListener(this);
        showReceiving = new TextArea(10, 10);
        showReceiving.setForeground(Color.blue);
        showReceived = new TextArea(10, 10);
        Panel north = new Panel();
        north.add(startRece);
        north.add(stopRece);
        add(north, BorderLayout.NORTH);
        Panel center = new Panel();
        center.setLayout(new GridLayout(1, 2));
        center.add(showReceiving);
        center.add(showReceived);
        add(center, BorderLayout.CENTER);
        validate();
        port = 5000;
        try {
            group = InetAddress.getByName("239.255.0.0");
            socket = new MulticastSocket(port);
            socket.joinGroup(group);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Receive.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Receive.class.getName()).log(Level.SEVERE, null, ex);
        }
        setBounds(100, 50, 360, 380);
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }

        });
    }

    public static void main(String[] args) {
        new Receive();
    }

    public void run() {
        while (true) {
            byte[] data = new byte[8192];
            DatagramPacket packet = null;
            packet = new DatagramPacket(data, data.length);
            try {
                socket.receive(packet);
                String message = new String(packet.getData(), 0, packet.getLength());
                showReceiving.setText("正在接收的内容\n" + message);
                showReceived.append(message + "\n");
            } catch (IOException ex) {
                Logger.getLogger(Receive.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (stoped) {
                break;
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startRece) {
            startRece.setBackground(Color.blue);
            stopRece.setBackground(Color.gray);
            if (!(thread.isAlive())) {
                thread = new Thread(this);
            }
            thread.start();
            stoped = false;
        }
        if (e.getSource() == stopRece) {
            startRece.setBackground(Color.gray);
            stopRece.setBackground(Color.blue);
            thread.interrupt();
            stoped = true;
        }
    }

}