public class SmartCrop {


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
            for (int j = 1; j < width - 1; j++) {
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

        for (int i = 1; i < height; i++) {

            if (aggregatePaths[i - 1][0].getValue() < aggregatePaths[i - 1][1].getValue()) {
                aggregatePaths[i][0] = new Node(gradients[i][0] + aggregatePaths[i - 1][0].getValue(), i, 0);
                aggregatePaths[i][0].setParent(aggregatePaths[i - 1][0]);
            }
            else {
                aggregatePaths[i][0] = new Node(gradients[i][0] + aggregatePaths[i - 1][1].getValue(), i, 0);
                aggregatePaths[i][0].setParent(aggregatePaths[i - 1][1]);
            }

            if (aggregatePaths[i - 1][width - 1].getValue() < aggregatePaths[i - 1][width - 2].getValue()) {
                aggregatePaths[i][width - 1] = new Node(gradients[i][width - 1] + aggregatePaths[i - 1][width - 1].getValue(), i, width - 1);
                aggregatePaths[i][width - 1].setParent(aggregatePaths[i - 1][width - 1]);
            }
            else {
                aggregatePaths[i][width - 1] = new Node(gradients[i][width - 1] + aggregatePaths[i - 1][width - 2].getValue(), i, width - 1);
                aggregatePaths[i][width - 1].setParent(aggregatePaths[i - 1][width - 2]);
            }
        }

        return  aggregatePaths;
    }

    public static Node[][] markMinPath(Node[][] aggregatedPaths) {

        int lastRow = aggregatedPaths.length - 1;
        int minPathColIndex =minValInRow(aggregatedPaths, lastRow);

        for (int i = lastRow; i >= 0 ; i--) {
            aggregatedPaths[i][minPathColIndex].setMarked(true);
            minPathColIndex = aggregatedPaths[i][minPathColIndex].getParent().getCol();
        }

        return aggregatedPaths;
    }

    //return the column index of the minimum value int the given row
    public static int minValInRow(Node[][] arr, int row) throws IndexOutOfBoundsException{

        int index = 0;
        int minValue = arr[row][0].getValue();
        int width = arr[0].length;

        for (int i = 0; i < width; i++) {

            if(arr[row][i].getValue() < minValue){
                index = i;
                minValue = arr[row][i].getValue();
            }
        }
        return index;
    }
}
