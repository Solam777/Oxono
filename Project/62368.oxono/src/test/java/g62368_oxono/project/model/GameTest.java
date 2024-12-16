package g62368_oxono.project.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private Game game;

    @BeforeEach
    public void setUp() {
        game = new Game();
    }



    @Test
    void playPawn() {

        game.initializeGame(6);
        Pawn pawn = new Pawn(Mark.X, Color.BLACK);

        game.playTotem(new Position(2, 1), new Totem(Mark.X));
        game.playPawn(new Position(2, 2));
        assertEquals(game.getPiece(new Position(2,2)),pawn);
    }

    @Test
    void playTotem() {
        Game game = new Game();
        game.initializeGame(6);
        Totem totem = new Totem(Mark.X);
        game.playTotem(new Position(2, 3), totem);
        assertEquals(totem, game.getPiece(new Position(2, 3)));
    }

    @Test
    void checkWin() {
        Game game = new Game();
        game.initializeGame(6);
        Pawn pawn = new Pawn(Mark.X, Color.BLACK);
        Totem totem = new Totem(Mark.X);

        game.playTotem(new Position(2, 1), totem);
        game.playPawn(new Position(1, 1));

        game.playTotem(new Position(2,2),totem);
        game.playPawn(new Position(1, 2));

        game.playTotem(new Position(2, 3), totem);
        game.playPawn(new Position(1, 3));

        game.playTotem(new Position(2, 4), totem);
        game.playPawn(new Position(1, 4));


        assertTrue(game.checkWin());

    }



    @Test
    void getRemainingPawns() {
    }

    @Test
    void switchPlayer() {

    }

    @Test
    void undo() {

        game.initializeGame(6);
        Pawn pawn = new Pawn(Mark.X, Color.BLACK);
        Totem totem = new Totem(Mark.X);
        game.playTotem(new Position(2,3),totem);
        game.playPawn(new Position(2, 4));
        game.undo();
        assertNull(game.getPiece(new Position(2, 4)));

    }

    @Test
    void redo() {
        game.initializeGame(6);
        Pawn pawn = new Pawn(Mark.X, Color.BLACK);
        Totem totem = new Totem(Mark.X);
        game.playTotem(new Position(2,3),totem);
        game.playPawn(new Position(2, 4));
        game.undo();
        game.redo();
        assertEquals(pawn, game.getPiece(new Position(2, 4)));
    }
}