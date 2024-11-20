package g62368_oxono.project.model;

public abstract class Piece {
    private Mark mark;

    public Piece(Mark mark) {
        this.mark = mark;
    }

    public Mark getMark() {
        return mark;
    }
}
