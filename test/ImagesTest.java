import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author: msb
 * @date: 2022/12/10 - 12 - 10 - 14:44
 * @description: PACKAGE_NAME
 * @version: 1.0
 */
public class ImagesTest {
    @Test
    public void testLoadImage(){
        try {
            /*//绝对路径获取图片 但是绝对路径存在问题 一旦项目交付 换台机子也必须要从这个路径获取图片 如果没有这个路径 就读不出来了
            BufferedImage image = ImageIO.read(new File("D:\\java images\\tank\\images\\goodTankU.gif"));
            Assertions.assertNotNull(image);*/
            //相对路径
            BufferedImage image1 = ImageIO.read(ImagesTest.class.getClassLoader().getResourceAsStream("images\\bulletU1.gif"));
            //this.getClass()
            Assertions.assertNotNull(image1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRotateImage(){
        BufferedImage tankU = null;
        try {
            tankU = ImageIO.read(ImagesTest.class.getClassLoader().getResourceAsStream("images/goodTankU.gif"));
            BufferedImage tankR = rotateImage(tankU,90);
            Assertions.assertNotNull(tankR);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public BufferedImage rotateImage(final BufferedImage bufferedImage,
                                     final int degree) {
        int w = bufferedImage.getWidth();
        int h = bufferedImage.getHeight();
        int type = bufferedImage.getColorModel().getTransparency();
        BufferedImage img;
        Graphics2D graphics2d;
        (graphics2d = (img = new BufferedImage(w,h,type))
                .createGraphics()).setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2d.rotate(Math.toRadians(degree),w / 2, h / 2);
        graphics2d.drawImage(bufferedImage, 0, 0, null);
        graphics2d.dispose();
        return img;
    }
}
