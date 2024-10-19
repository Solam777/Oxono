import Controller.AsciiController;
import Model.AsciiPaint;
import Model.Point;
import View.View;

public class App {
    public static void main(String[] args) {
        AsciiPaint paint = new AsciiPaint(50,50);
        View vue = new View();
        AsciiController controller = new AsciiController(paint,vue);
        controller.start();


    }
}
