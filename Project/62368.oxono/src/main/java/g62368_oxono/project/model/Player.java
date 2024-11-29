package g62368_oxono.project.model;

import java.util.Stack;

public class Player {
    private Color color;
    private Stack<Pawn> pawnsX;
    private Stack<Pawn> pawnsO;

    public Player(Color color) {
        this.color = color;
        this.pawnsO = new Stack<>();
        this.pawnsX = new Stack<>();
        for (int i = 0; i < 8; i++) {
            pawnsO.add(new Pawn(Mark.O, color));
            pawnsX.add(new Pawn(Mark.X, color));
        }
    }

    public boolean hasPawn(Mark mark) {
        return (mark == Mark.X) ? !pawnsX.empty() : !pawnsO.empty();
    }

    public void addPawn(Pawn pawn) {
        if (pawn.getMark() == Mark.X) {
            pawnsX.add(pawn);
        } else {
            pawnsO.add(pawn);

        }
    }

    public void removePawn(Pawn pawn) {
        if (pawn.getMark() == Mark.X && !pawnsX.empty()) {
            pawnsX.pop();
        } else if(!pawnsO.empty()) {
            pawnsO.pop();
        }

    }
    public int getRemainingPawns(Mark mark){
       return mark == Mark.X ? pawnsX.size() : pawnsO.size();
    }


    public void setdraw(){
        pawnsX.clear();
        pawnsO.clear();
    }

    public Color getColor() {
        return color;
    }

    public Stack<Pawn> getPawnsX() {
        return pawnsX;
    }

    public Stack<Pawn> getPawnsO() {
        return pawnsO;
    }
     public void reset() {
        pawnsX.clear();
        pawnsO.clear();
        for (int i = 0; i < 8; i++) {
            pawnsO.add(new Pawn(Mark.O, color));
            pawnsX.add(new Pawn(Mark.X, color));
        }
     }

    //    public Stack<Pawn> getPion_X_du_joueur() {
//        return pion_X_du_joueur;
//    }
//
//    public Stack<Pawn> getPion_O_du_joueur() {
//        return pion_O_du_joueur;
//    }


}
