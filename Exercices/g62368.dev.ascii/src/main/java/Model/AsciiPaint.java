package Model;

import Model.Commandes.*;

import java.util.ArrayList;
import java.util.List;

public class AsciiPaint {
    Drawing shape;
    int width;
    int height;
    CommandManager commandManager = new CommandManager();

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
         Cercle cercle = new Cercle(color,radius,center);
        AddCommand addCommand = new AddCommand(shape,cercle);
        commandManager.do_(addCommand);

    }
    public void addRectangle(double upperLeftX, double upperLefty, double width, double height, char color ){
        Rectangle rectangle = new Rectangle(color,width,height,new Point(upperLeftX,upperLefty));
        AddCommand addCommand = new AddCommand(shape,rectangle);
        commandManager.do_(addCommand);

    }
    public void addSquare(double upperleftX, double upperleftY,double side,char color){
        Square square = new Square(color, side,side,new Point(upperleftX,upperleftY) );
        AddCommand addCommand = new AddCommand(shape,square);
        commandManager.do_(addCommand);

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
            ChangeColorCommand changeColorCommand = new ChangeColorCommand(forme,color);
            commandManager.do_( changeColorCommand);
        }
        else {
            System.out.println("nothing find at: " + index);
        }

    }

    public void moveShape( int index ,  double dx, double dy){
        Shape forme = shape.getShapeAt(index);
        if (forme != null) {
            Movecommand movecommand = new Movecommand(forme,dx,dy);
            commandManager.do_(movecommand);
        }
        else {
            System.out.println("nothing forme at: " + index);
        }

    }

    public void removeShape(int index){
        Shape forme = shape.getShapeAt(index);
        if (forme != null) {

            DeleteCommand deleteCommand =new DeleteCommand(forme, shape, index);
            commandManager.do_(deleteCommand);
        }
    }

    public void group(List<Integer> indices, char color) {
        GroupCommand groupCommand = new GroupCommand(shape,indices,color);
        commandManager.do_(groupCommand);


    }

    public void ungroup(int index) {
        UngroupCommande ungroupCommande = new UngroupCommande(index,shape);
        commandManager.do_(ungroupCommande);
    }

    public void undo(){
        commandManager.undo();

    }
    public void redo(){
        commandManager.redo();
    }
}
