package gfx;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/*
    This class simply loads images for the game
*/

public class ImageLoader {

    //returns an image(buffered image) at the provided path
    public static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);  
        }
        return null;
    }
    
}
