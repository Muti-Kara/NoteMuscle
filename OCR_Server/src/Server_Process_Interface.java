import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
/**
 * Server_Process_Interface
 */
public interface Server_Process_Interface {

    final String IMG_PATH = System.getProperty("user.dir") + "\\image_buffer\\inprocess.png";

    static BufferedImage getImgInProcess(){
        try {
            return ImageIO.read(new File(IMG_PATH));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public String getProcessedString();
}