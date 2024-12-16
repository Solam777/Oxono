package g62368_oxono.project.model;

import java.util.Stack;

public class Player {
    private Color color;
    private Stack<Pawn> pawnsX;
    private Stack<Pawn> pawnsO;

    /**
     * Constructs a {@code Player} with a specific color and initializes
     * their pawns (8 marked X and 8 marked O).
     *
     * @param color the color of the player
     */
    public Player(Color color) {
        this.color = color;
        this.pawnsO = new Stack<>();
        this.pawnsX = new Stack<>();
        for (int i = 0; i < 8; i++) {
            pawnsO.add(new Pawn(Mark.O, color));
            pawnsX.add(new Pawn(Mark.X, color));
        }
    }

    /**
     * Checks if the player has any pawns left of the specified mark.
     *
     * @param mark the mark to check (X or O)
     * @return {@code true} if the player has at least one pawn of the specified mark,
     *         {@code false} otherwise
     */
    public boolean hasPawn(Mark mark) {
        return (mark == Mark.X) ? !pawnsX.empty() : !pawnsO.empty();
    }


    /**
     * Adds a pawn to the player's stack of pawns based on its mark.
     *
     * @param pawn the pawn to add
     */
    public void addPawn(Pawn pawn) {
        if (pawn.getMark() == Mark.X) {
            pawnsX.add(pawn);
        } else {
            pawnsO.add(pawn);

        }
    }

    /**
     * Removes a pawn from the player's stack of pawns based on its mark.
     *
     * @param pawn the pawn to remove
     */
    public void removePawn(Pawn pawn) {
        if (pawn.getMark() == Mark.X && !pawnsX.empty()) {
            pawnsX.pop();
        } else if(!pawnsO.empty()) {
            pawnsO.pop();
        }

    }
    /**
     * Retrieves the number of remaining pawns of the specified mark.
     *
     * @param mark the mark of the pawns to count (X or O)
     * @return the number of remaining pawns of the specified mark
     */
    public int getRemainingPawns(Mark mark){
       return mark == Mark.X ? pawnsX.size() : pawnsO.size();
    }
    /**
     * Retrieves the player's color.
     *
     * @return the player's color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Retrieves the stack of pawns marked X.
     *
     * @return the stack of pawns marked X
     */
    public Stack<Pawn> getPawnsX() {
        return pawnsX;
    }

    /**
     * Retrieves the stack of pawns marked O.
     *
     * @return the stack of pawns marked O
     */
    public Stack<Pawn> getPawnsO() {
        return pawnsO;
    }
}
