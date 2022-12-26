package com.msb.nettycodec1;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author: msb
 * @date: 2022/12/23 - 12 - 23 - 14:12
 * @description: com.msb.nettycode
 * @version: 1.0
 */
public class ServerFrame extends Frame {
    public static final ServerFrame INSTANCE = new ServerFrame();
    TextArea taServer = new TextArea();
    TextArea taClient = new TextArea();
    private Server server = new Server();
    private ServerFrame(){
        this.setSize(800,700);
        this.setLocation(200,200);
        this.setTitle("msbServer");
        //panel 面板容器  gridLayout 网格布局
        Panel p = new Panel(new GridLayout(1,2));
        p.add(taServer);
        p.add(taClient);
        this.add(p);

        taServer.setFont(new Font("consolas",Font.PLAIN,20));
        taClient.setFont(new Font("consolas",Font.PLAIN,20));
        this.updateServerMsg("server:");
        this.updateClientMsg("client:");

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

    }

    public void updateServerMsg(String str){
        taServer.setText(taServer.getText() + str + System.getProperty("line.separator"));
    }
    public void updateClientMsg(String str){
        taClient.setText(taClient.getText() + str + System.getProperty("line.separator"));
    }

    public static void main(String[] args) {
        ServerFrame.INSTANCE.setVisible(true);
        ServerFrame.INSTANCE.server.serverStart();
    }
}
