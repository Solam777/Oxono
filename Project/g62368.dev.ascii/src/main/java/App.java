import Model.AsciiPaint;
import Model.Point;
import View.View;

public class App {
    public static void main(String[] args) {
        AsciiPaint paint = new AsciiPaint(50,50);
        View vue = new View();
        paint.addCircle(new Point(5,2),5,'V');
        paint.addRectangle(10,10,3,4,'B');
        paint.addSquare(5,5,3,'O');
        vue.display(paint);
        paint.moveShape(1,6,2);
        vue.display(paint);

    }
}
