package View;

import Model.AsciiPaint;

public class View {
    public void display(AsciiPaint paint){
        int width = paint.getwidth();
        int height = paint.getheigth();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                char color = paint.getColorAt(x, y);
                if (color == ' ') {
                    System.out.print(' ');
                } else {
                    System.out.print(color);
                }

            }
            System.out.println(" ");

        }

    }
}
