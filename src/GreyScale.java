import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class GreyScale {

    public static BufferedImage greyScale(String url) {

        BufferedImage image = null;
        File file = null;

        //Read image:
        try {
            file = new File(url);
            image = ImageIO.read(file);

            System.out.println("Reading success");
        }
        catch (IOException i){
            System.out.println("Reading failed");
        }

        //Set image pixels to greyscale:

        int height = image.getHeight();
        int width = image.getWidth();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                int pixel = image.getRGB(x, y);

                int alpha = (pixel >> 24) & 0xff;
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = pixel & 0xff;

                int avg = (red + green + blue) / 3;

                int greyPixel = alpha << 24 | avg << 16 | avg << 8 | avg;

                image.setRGB(x, y, greyPixel);
            }
        }

        return image;
    }
}
