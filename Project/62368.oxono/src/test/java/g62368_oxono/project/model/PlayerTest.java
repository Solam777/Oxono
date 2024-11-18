package g62368_oxono.project.model;

import org.junit.jupiter.api.Test;

class PlayerTest {
    Player player = new Player(Color.BLACK);

    @Test
    void haspawn() {
        player.hasPawn(Mark.O);
        player.hasPawn(Mark.X);

    }

    @Test
    void addPawn() {
        player.addPawn(new Pawn(Mark.X,Color.BLACK));
        System.out.println(player.getRemainingPawns(Mark.X));
    }

    @Test
    void removepawn() {
       Player player1 = new Player(Color.PINK);
        System.out.println(player1.getRemainingPawns(Mark.O));
        System.out.println(player1.getRemainingPawns(Mark.X));
        player1.removePawn(new Pawn(Mark.O,player1.getColor()));
        System.out.println(player1.getRemainingPawns(Mark.O));
        System.out.println(player1.getRemainingPawns(Mark.X));
    }

    @Test
    void getpawn() {
        System.out.println(  player.getRemainingPawns(Mark.X));

    }

    }
