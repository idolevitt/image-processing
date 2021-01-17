import jdk.jshell.ImportSnippet;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;

public class Image {

    public static void main(String[] args) {

        int width = 930;
        int height = 800;

        BufferedImage image = null;

        //Reading file:

        try {
            File input = new File("src/input.jpg");

            image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

            image = ImageIO.read(input);

            System.out.println("Reading success");
        }
        catch (IOException i){
            System.out.println("Reading failed");
        }

        //Writing file:

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
