public class main {

    public static void main(String[] args) {

        String url = "src/input.jpg";
        //Invert.invertColors(url);

        for (int i = 0; i < 50; i++) {
            SmartCrop.cropPath("output.jpg" , "output.jpg");
        }

    }
}
