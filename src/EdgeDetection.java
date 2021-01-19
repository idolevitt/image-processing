import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class EdgeDetection {

    public static void EdgeDetection(String[] args) {

        BufferedImage image = null;
        File file = null;

        //Read image:
        try {
            file = new File("src/input.jpg");
            image = ImageIO.read(file);

            System.out.println("Reading success");
        }
        catch (IOException i){
            System.out.println("Reading failed");
        }

        //Separating color layers and calculating gradient array for each layer
        int[][] sobelRed = getSobelArray(separateColor("red", image));
        int[][] sobelGreen = getSobelArray(separateColor("green", image));
        int[][] sobelBlue = getSobelArray(separateColor("blue", image));

        int height = image.getHeight();
        int width = image.getWidth();


        //Setting image as greyscale from the three sobel color layers:
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                int avg = (sobelRed[y][x] + sobelGreen[y][x] + sobelBlue[y][x]) / 3;
                int alpha = (image.getRGB(x, y) >> 24) & 0xff;

                int greyPixel = alpha << 24 | avg << 16 | avg << 8 | avg;

                image.setRGB(x, y, greyPixel);
            }
        }


        //Write image:

        try{
            File output = new File("outputGS.png");

            ImageIO.write(image, "png", output);

            System.out.println("Writing success");
        }
        catch (IOException i){
            System.out.println("Writing failed");
        }


    }

    public static int[][] separateColor(String color, BufferedImage image){

        int shift = getShift(color);
        int height = image.getHeight();
        int width = image.getWidth();

        int[][] colorLayer = new int[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                colorLayer[y][x] = (image.getRGB(x, y) >> shift) & 0xff;
            }
        }

        return colorLayer;
    }

    public static int getShift(String color){

        if (color.equals("blue"))
            return 0;
        if (color.equals("green"))
            return 8;
        if (color.equals("red"))
            return 16;

        return -1;
    }

    public static  int[][] getSobelArray(int[][] pixels){

        int height = pixels.length;
        int width = pixels[0].length;

        int[][] sobelArr = new int[height][width];

        int[][] sobelMatrix = {{1, 0, -1}, {2, 0, -2}, {1, 0, -1}};
        int sobelVertical = 0;
        int sobelHorizontal = 0;

        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {

                //Calculate sobelVertical [x][y];
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        sobelVertical += sobelMatrix[i][j] * pixels[y + i - 1][x + j - 1];
                    }
                }

                //Calculate sobelHorizontal [x][y];
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        sobelHorizontal += sobelMatrix[j][i] * pixels[y + i - 1][x + j - 1];
                    }
                }

                sobelArr[y][x] = (Math.abs(sobelVertical) + Math.abs(sobelHorizontal)) / 2;//TODO: realize why / 2 works

                sobelVertical = 0;
                sobelHorizontal = 0;
            }
        }

        //Padding Top and Bottom:
        for (int i = 1; i < height - 1; i++) {
            sobelArr[i][0] = sobelArr[i][1];
            sobelArr[i][width - 1] = sobelArr[i][width - 2];
        }

        //Padding Left and Right:
        for (int i = 1; i < width - 1; i++) {
            sobelArr[0][i] = sobelArr[1][i];
            sobelArr[height - 1][i] = sobelArr[height - 2][i];
        }

        //Padding corners:
        sobelArr[0][0] = sobelArr[1][1];
        sobelArr[0][width - 1] = sobelArr[1][width - 2];
        sobelArr[height - 1][0] = sobelArr[height - 2][1];
        sobelArr[height - 1][width - 1] = sobelArr[height - 2][width - 2];

        return sobelArr;
    }
}
