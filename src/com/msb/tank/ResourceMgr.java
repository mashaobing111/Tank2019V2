package com.msb.tank;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author: msb
 * @date: 2022/12/10 - 12 - 10 - 16:39
 * @description: com.msb.tank
 * @version: 1.0
 */
public class ResourceMgr {
    public static BufferedImage goodTankU, goodTankD, goodTankL, goodTankR;
    public static BufferedImage badTankU, badTankD, badTankL, badTankR;
    public static BufferedImage bulletU, bulletD, bulletL, bulletR;
    public static BufferedImage [] explodes = new BufferedImage[9];

    static{

        try {
            goodTankU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/goodTankU.gif"));
            goodTankD = ImageUtil.rotateImage(goodTankU,180);
            goodTankR = ImageUtil.rotateImage(goodTankU,90);
            goodTankL = ImageUtil.rotateImage(goodTankU,270);

            badTankU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/badTankU.gif"));
            badTankD = ImageUtil.rotateImage(badTankU,180);
            badTankR = ImageUtil.rotateImage(badTankU,90);
            badTankL = ImageUtil.rotateImage(badTankU,270);

            bulletU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/bulletU.gif"));
            bulletD = ImageUtil.rotateImage(bulletU,180);
            bulletR = ImageUtil.rotateImage(bulletU,90);
            bulletL = ImageUtil.rotateImage(bulletU,270);

            for (int i = 0; i <9 ; i++) {
                explodes[i] = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/explosion" + (i+1) +".gif"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
