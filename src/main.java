import java.awt.image.BufferedImage;

public class main {

    public static void main(String[] args) {

        String url = "src/input.jpg";
        Invert.invertColors(url);
        //Image.writeImage(image , "output.png");


        //for (int i = 0; i < 150; i++) {
        //    SmartCrop.cropPath("input.jpg", "input.jpg");
        //}

    }
}
