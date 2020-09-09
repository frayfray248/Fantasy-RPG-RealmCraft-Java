package gfx;

import java.awt.image.BufferedImage;


/*
    holds a buffered image and allows cropping of that image. Used for images with many
    sprites on it.
*/

public class SpriteSheet {

    private BufferedImage sheet;

    public SpriteSheet(BufferedImage sheet){
        this.sheet = sheet;
    }

    //crops out the image using the x and y cordinates as the upper left corner
    public BufferedImage crop(int x, int y, int width, int height) {
        return sheet.getSubimage(x, y, width, height);
    }
}