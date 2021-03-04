import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SmartCrop {


    public static void cropPath(String source, String target){

        BufferedImage image = null;
        File file = null;

        //Read image:
        try {
            file = new File(source);
            image = ImageIO.read(file);

            System.out.println("Reading success");
        }
        catch (IOException i){
            System.out.println("Reading failed");
        }

        int[][] gradients = EdgeDetection.getGradientArray(source);

        Node[][] gradientPaths = sumLowestPaths(gradients);

        markMinPath(gradientPaths);

        BufferedImage newImage = null;

        //Write image:
        try{
            newImage = new BufferedImage(image.getWidth() - 1, image.getHeight(), BufferedImage.TYPE_INT_ARGB);

            setPixelsWithoutMarkedPaths(newImage, image, gradientPaths);

            File output = new File(target);

            ImageIO.write(newImage, "png", output);

            System.out.println("Writing success");
        }
        catch (IOException i){
            System.out.println("Writing failed");
        }
    }

    public static void setPixelsWithoutMarkedPaths(BufferedImage target, BufferedImage source, Node[][] markedPath) {

        //int height = target.getHeight();
        //int width = target.getWidth();
        int height = source.getHeight();
        int width = source.getWidth();

        System.out.println("height : " + height + "   width : " + width);
        System.out.println("height : " + source.getHeight() + "   width : " + source.getWidth());

        for (int i = 0; i < height; i++) {
            int advance = 0;
            for (int j = 0; j < width; j++) {

                if(markedPath[i][j].isMarked()) {
                //    System.out.println("Marked : i :" + i + " j : " + j);
                    advance++;
                }
                else {
                    //System.out.println("i : " + i + "  j : " + j);
                    target.setRGB(j - advance, i, source.getRGB(j, i));
                }
            }
        }
    }

    public static Node[][] sumLowestPaths(int[][] gradients) {

        int height = gradients.length;
        int width = gradients[0].length;

        Node[][] aggregatePaths = new Node[height][width];

        //First row
        for (int i = 0; i < width; i++) {
            aggregatePaths[0][i] = new Node(gradients[0][i], 0, i);
        }

        //Without the left and the right columns
        for (int i = 1; i < height; i++) {
            for (int j = 0; j < width; j++) {

                if (j == 0) {
                    if (aggregatePaths[i - 1][0].getValue() < aggregatePaths[i - 1][1].getValue()) {
                        aggregatePaths[i][0] = new Node(gradients[i][0] + aggregatePaths[i - 1][0].getValue(), i, j);
                        aggregatePaths[i][0].setParent(aggregatePaths[i - 1][0]);
                    }
                    else {
                        aggregatePaths[i][0] = new Node(gradients[i][0] + aggregatePaths[i - 1][1].getValue(), i, j);
                        aggregatePaths[i][0].setParent(aggregatePaths[i - 1][1]);
                    }
                }

                else if (j == width - 1) {
                    if (aggregatePaths[i - 1][width - 1].getValue() < aggregatePaths[i - 1][width - 2].getValue()) {
                        aggregatePaths[i][width - 1] = new Node(gradients[i][width - 1] + aggregatePaths[i - 1][width - 1].getValue(), i, j);
                        aggregatePaths[i][width - 1].setParent(aggregatePaths[i - 1][width - 1]);
                    }
                    else {
                        aggregatePaths[i][width - 1] = new Node(gradients[i][width - 1] + aggregatePaths[i - 1][width - 2].getValue(), i, j);
                        aggregatePaths[i][width - 1].setParent(aggregatePaths[i - 1][width - 2]);
                    }
                }

                else {
                    if(aggregatePaths[i - 1][j - 1].getValue() < Math.min(aggregatePaths[i - 1][j].getValue(), aggregatePaths[i - 1][j + 1].getValue())) {
                        aggregatePaths[i][j] = new Node(gradients[i][j] + aggregatePaths[i - 1][j - 1].getValue(), i, j);
                        aggregatePaths[i][j].setParent(aggregatePaths[i - 1][j - 1]);
                    }
                    else if (aggregatePaths[i - 1][j].getValue() < aggregatePaths[i - 1][j + 1].getValue()) {
                        aggregatePaths[i][j] = new Node(gradients[i][j] + aggregatePaths[i - 1][j].getValue(), i, j);
                        aggregatePaths[i][j].setParent(aggregatePaths[i - 1][j]);
                    }
                    else {
                        aggregatePaths[i][j] = new Node(gradients[i][j] + aggregatePaths[i - 1][j + 1].getValue(), i, j);
                        aggregatePaths[i][j].setParent(aggregatePaths[i - 1][j + 1]);
                    }
                }
            }
        }

        return  aggregatePaths;
    }

    public static void markMinPath(Node[][] aggregatedPaths) {

        int lastRow = aggregatedPaths.length - 1;
        int minPathColIndex = minUncheckedValInRow(aggregatedPaths, lastRow);

        //System.out.println("min blabla " + minPathColIndex);

        for (int i = lastRow; i >= 1 ; i--) {
            //System.out.println("marking : i : " + i + "  j : " + minPathColIndex );
            aggregatedPaths[i][minPathColIndex].setMarked(true);
            minPathColIndex = aggregatedPaths[i][minPathColIndex].getParent().getCol();
        }
        aggregatedPaths[0][minPathColIndex].setMarked(true);
    }

    /**
     *
     * @param arr
     * @param row
     * @return The column index of the minimum value in the given row, -1 if not exists
     * @throws IndexOutOfBoundsException
     */
    public static int minUncheckedValInRow(Node[][] arr, int row) throws IndexOutOfBoundsException{

        int index = -1;
        int minValue = Integer.MAX_VALUE;
        int width = arr[0].length;

        for (int i = 0; i < width; i++) {

            if(arr[row][i].getValue() < minValue && !arr[row][i].isMarked()){
                index = i;
                minValue = arr[row][i].getValue();
            }
        }
        return index;
    }
}
