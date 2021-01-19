import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Invert {

    public static void invert(String url) {

        BufferedImage image= null;
        File file = null;

        //Read image:
        try {
            file = new File(url);
            image = ImageIO.read(file);

            System.out.println("Reading succeeded");
        }
        catch (IOException i) {
            System.out.println("Reading failed");
        }

        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                image.setRGB(j, i, image.getRGB(j, i) ^ 0xffffffff);
            }
        }

        //Write image:

        try{
            File output = new File("output.png");

            ImageIO.write(image, "png", output);

            System.out.println("Writing success");
        }
        catch (IOException i){
            System.out.println("Writing failed");
        }

    }
}
