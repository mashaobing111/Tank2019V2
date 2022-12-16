package com.msb.tank;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * @author: msb
 * @date: 2022/12/15 - 12 - 15 - 13:13
 * @description: com.msb.tank
 * @version: 1.0
 */
public class Audio extends Thread {
    private String path;
    private AudioInputStream audioStream;
    private AudioFormat audioFormat;
    private SourceDataLine sourceDataLine;

    public Audio(String path) {
        this.path = path;
    }

    @Override
    public void run() {
        byte[] b = new byte[1024];
        int len = 0;
        try {
            //获取音频输入流
            /*audioStream = AudioSystem.getAudioInputStream(new File(path));*/
            audioStream = AudioSystem.getAudioInputStream(Audio.class.getClassLoader().getResourceAsStream(path));
            //获取音频的编码格式
            audioFormat = audioStream.getFormat();
            DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class,
                    audioFormat,AudioSystem.NOT_SPECIFIED);

            sourceDataLine = (SourceDataLine)AudioSystem.getLine(dataLineInfo);
            sourceDataLine.open(audioFormat);
            sourceDataLine.start();
            //播放音频
            while ((len = audioStream.read(b,0,b.length)) != -1){
                sourceDataLine.write(b,0,len);
            }
            //播放结束
            sourceDataLine.drain();
            sourceDataLine.close();
            audioStream.close();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
