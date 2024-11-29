package g62368_oxono.project.model;

public class Totem extends Piece{

    public Totem(Mark mark) {
        super(mark);
    }

    @Override
    public String toString() {
        return " "+ getMark() ;
    }
}
