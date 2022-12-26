package com.msb.net.io.bio;

import java.io.*;
import java.net.Socket;

/**
 * @author: msb
 * @date: 2022/12/20 - 12 - 20 - 14:45
 * @description: com.msb.net.bio
 * @version: 1.0
 */
public class Client {
    public static void main(String[] args) throws Exception{
        Socket s = new Socket("localhost",8888);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
        bw.write("msb");
        bw.newLine();
        //防止缓冲区未满 强制发送数据并清空缓冲区
        bw.flush();

        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));

        System.out.println(br.readLine());
        bw.close();


    }
}
