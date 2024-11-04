package com.ariesninja.BlazeEngine.structs;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Texture {
    private BufferedImage image;

    public Texture(String filePath) throws IOException {
        image = ImageIO.read(new File(filePath));
    }

    public int getWidth() {
        return image.getWidth();
    }

    public int getHeight() {
        return image.getHeight();
    }

    public int getRGB(int x, int y) {
        return image.getRGB(x, y);
    }

    public BufferedImage getImage() {
        return image;
    }
}