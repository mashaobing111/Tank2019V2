package com.msb.tank.net;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author: msb
 * @date: 2022/12/24 - 12 - 24 - 15:50
 * @description: com.msb.tank.net
 * @version: 1.0
 */
public class ServerFrame extends Frame {
    public static final ServerFrame INSTANCE = new ServerFrame();
    TextArea taServer = new TextArea();
    TextArea taClient = new TextArea();

    private Server server = new Server();
    private ServerFrame(){
        this.setTitle("Tank Server");
        this.setSize(600,700);
        this.setLocation(1400,200);
        Panel p = new Panel(new GridLayout(1,2));
        p.add(taServer);
        p.add(taClient);
        this.add(p);

        this.taServer.setFont(new Font(null,Font.PLAIN,15));
        this.taClient.setFont(new Font(null,Font.PLAIN,15));

        this.updateServer("Server:");
        this.updateClient("Client:");

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

    }
    public void updateServer(String str){
        taServer.setText(taServer.getText() + str + "\r\n");
    }
    public void updateClient(String str){
        taClient.setText(taClient.getText() + str + "\r\n" );
    }
    public static void main(String[] args) {
        ServerFrame sf = ServerFrame.INSTANCE;
        sf.setVisible(true);
        ServerFrame.INSTANCE.server.serverStart();
    }
}
