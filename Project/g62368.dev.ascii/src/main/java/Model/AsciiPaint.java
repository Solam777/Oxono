package Model;

public class AsciiPaint {
    Drawing shape;
    int width;
    int height;

    public AsciiPaint( int width, int height) {
        this.shape = new Drawing(width, height);

    }

    public Drawing getShape() {
        return shape;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void addCircle(Point center, double radius, char color){
         shape.add(new Cercle(color,radius,center));

    }
    public void addRectangle(double upperLeftX, double upperLefty, double width, double height, char color ){
        shape.add(new Rectangle(color,width,height, new Point(upperLeftX,upperLefty)));

    }
    public void addSquare(double upperleftX, double upperleftY,double side,char color){
        shape.add(new Square(color, side,side,new Point(upperleftX,upperleftY) ));
    }
    public char getColorAt(int x , int y){
        return shape.getColorAt(x,y);

    }

    public int getwidth(){
        return shape.getWidth();
    }

    public int getheigth(){
        return shape.getHeight();
    }
    public void setColor(  int index , char color ){
        Shape forme = shape.getShapeAt(index);
        if (forme != null) {
            forme.setColor(color);
        }
        else {
            System.out.println("Aucune forme trouvée à l'index: " + index);

        }

    }

    public void moveShape( int index ,  double dx, double dy){
        Shape forme = shape.getShapeAt(index);
        if (forme != null) {
            forme.move(dx, dy);
        }
        else {
            System.out.println("Aucune forme trouvée à l'index: " + index);
        }
    }

    public void removeShape(int index){
        shape.remove(index);
    }
}
