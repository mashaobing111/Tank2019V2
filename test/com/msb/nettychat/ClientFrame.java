package com.msb.nettychat;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author: msb
 * @date: 2022/12/22 - 12 - 22 - 13:48
 * @description: com.msb.nettychat
 * @version: 1.0
 */
public class ClientFrame extends Frame {

    public static final ClientFrame INSTANCE = new ClientFrame();
    private TextArea ta = new TextArea();
    private TextField tf = new TextField();

    private Client c = null;

    private ClientFrame(){
        this.setSize(500,700);
        this.setLocation(200,200);
        this.add(ta,BorderLayout.CENTER);
        this.add(tf,BorderLayout.SOUTH);
        this.setTitle("msb.com");
        tf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {//文本框按下会吃会调用这个事件
                c.send(tf.getText());
                /*ta.setText(ta.getText() + tf.getText() + "\n");*/
                tf.setText("");
            }
        });
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                c.closeConnection();
                System.exit(0);
            }
        });
    }

    public void connectToServer(){
        c = new Client();
        c.connect();
    }
    public static void main(String[] args) {
        ClientFrame f = ClientFrame.INSTANCE;
        f.setVisible(true);
        f.connectToServer();
    }

    public void updateText(String str) {
        ta.setText(ta.getText() + str + "\n");
    }
}
