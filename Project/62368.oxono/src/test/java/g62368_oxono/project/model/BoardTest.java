package g62368_oxono.project.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    private Board board;

    @BeforeEach
    public void setUp() {
        board = new Board(6); // Initialisation d'un plateau 6x6
    }

    @Test
    public void testInitialization() {
        assertEquals(6, board.getSize());
        assertNotNull(board.getPiece(new Position(2, 2))); // Vérifie que le totem X est bien placé
        assertNotNull(board.getPiece(new Position(3, 3))); // Vérifie que le totem O est bien placé
    }

    @Test
    public void testIsInside() {
        assertTrue(board.isInside(new Position(0, 0)));
        assertTrue(board.isInside(new Position(4, 4)));
        assertFalse(board.isInside(new Position(-1, 0)));
        assertFalse(board.isInside(new Position(5, 6)));
    }

    @Test
    public void testIsEmpty() {
        assertTrue(board.isEmpty(new Position(0, 0))); // Position vide
        assertFalse(board.isEmpty(new Position(2, 2))); // Position du totem X
        assertFalse(board.isEmpty(new Position(3, 3))); // Position du totem O
    }

    @Test
    public void testGetTotemPosition() {
        assertEquals(new Position(2, 2), board.getTotemPosition(Mark.X));
        assertEquals(new Position(3, 3), board.getTotemPosition(Mark.O));
    }

    @Test
    public void testPlaceTotem() {
        Position newPos = new Position(2, 3);
        board.placeTotem(newPos,board.returnTheTotem(Mark.X));
        assertNotNull(board.getTotemPosition(Mark.X));
        assertEquals(newPos,board.getTotemPosition(Mark.X));
        System.out.println(board.getTotemPosition(Mark.X));
    }

    @Test
    public void testValidPositions() {

    }

    @Test
    public void testIsSurrounded() {
        Position pos = new Position(2, 2);
        assertFalse(board.isSurrounded(pos)); // Totem X ne devrait pas être entouré au début
    }

    @Test
    public void testEmptyDirectionPosition() {
        Pawn pawn = new Pawn(Mark.X,Color.BLACK);
        board.setpawn(new Position(2,1),pawn);
        board.setpawn(new Position(3,2),pawn);
        board.setpawn(new Position(2,3),pawn);
        board.setpawn(new Position(1,2),pawn);
        System.out.println(board.getPiece(new Position(2,2)).getMark());
        System.out.println(board.movesIfTotemSurrounded(new Position(2,2)));



    }

    @Test
    public void testAllEmptyPositions() {
        List<Position> emptyPositions = board.allEmptyPositions();

        // Vérifie que les positions retournées sont toutes vides
        for (Position p : emptyPositions) {
            assertTrue(board.isEmpty(p));
        }

        // Vérifie que la taille correspond (36 cases - 2 totems = 34 vides)
        assertEquals(34, emptyPositions.size());
    }

    @Test
    public void testIsWin() {

        Totem totem =board.returnTheTotem(Mark.X);
        Pawn pawn = new Pawn(Mark.X, Color.BLACK);
        System.out.println(board.getTotemPosition(Mark.X));

        board.placeTotem(new Position(2, 1), totem);
        board.placePawn(new Position(1, 1), pawn);

        board.placeTotem(new Position(2,2),totem);
        board.placePawn(new Position(1, 2), pawn);

        board.placeTotem(new Position(2, 3), totem);
        board.placePawn(new Position(1, 3), pawn);

        board.placeTotem(new Position(2, 4), totem);
        board.placePawn(new Position(1, 4), pawn);

        assertTrue(board.checkLine(1 , new Pawn(Mark.X, Color.BLACK)));

    }

    @Test
    public void testCheckLine() {
        Totem totem =board.returnTheTotem(Mark.X);
        Pawn pawn = new Pawn(Mark.X, Color.BLACK);
        System.out.println(board.getTotemPosition(Mark.X));

        board.placeTotem(new Position(2, 1), totem);
        board.placePawn(new Position(1, 1), pawn);

        board.placeTotem(new Position(2,2),totem);
        board.placePawn(new Position(1, 2), pawn);

        board.placeTotem(new Position(2, 3), totem);
        board.placePawn(new Position(1, 3), pawn);

        board.placeTotem(new Position(2, 4), totem);
        board.placePawn(new Position(1, 4), pawn);

        assertTrue(board.checkLine(1 , new Pawn(Mark.X, Color.BLACK)));
        assertFalse(board.checkLine(2 , new Pawn(Mark.X, Color.BLACK)));
    }

    @Test
    public void testCheckColumn() {
        Totem totem = board.returnTheTotem(Mark.X);
        Pawn pawn = new Pawn(Mark.X, Color.BLACK);
        System.out.println(board.getTotemPosition(Mark.X));

        board.placeTotem(new Position(0, 2), totem);
        board.placePawn(new Position(0,1 ), pawn);

        board.placeTotem(new Position(1, 2), totem);
        board.placePawn(new Position(1,1 ), pawn);

        board.placeTotem(new Position(2, 2), totem);
        board.placePawn(new Position(2,1 ), pawn);

        board.placeTotem(new Position(3, 2), totem);
        board.placePawn(new Position(3,1 ), pawn);

        assertTrue(board.checkColumn(1, new Pawn(Mark.X, Color.BLACK)));



    }

    @Test
    public void testPlacePawn() {
        Totem totem =board.returnTheTotem(Mark.X);
        board.placeTotem(new Position(2, 3), totem);

        // Cas valide : Placer un pion dans une position adjacente
        Pawn pawn = new Pawn(Mark.X, Color.BLACK);
        Position validPosition = new Position(2, 2);
        board.placePawn(validPosition, pawn);
        assertEquals(pawn, board.getPiece(validPosition));
        System.out.println(board.getPiece(validPosition));

        // Cas invalide : Position hors direction valide
        Position invalidPosition = new Position(5, 5);
        assertThrows(OxonoExecption.class, () -> board.placePawn(invalidPosition, pawn));
    }

    @Test
    public void testValidateMove_jump() {
        board.placeTotem(new Position(  0,2),board.returnTheTotem(Mark.X));
        board.placePawn(new Position(  0,3),new Pawn(Mark.X,Color.BLACK));
        System.out.println(board.getPiece(new Position(0,2)));

        board.placeTotem(new Position(  0,0),board.returnTheTotem(Mark.X));
        board.placePawn(new Position(  0,1),new Pawn(Mark.X,Color.BLACK));
        System.out.println(board.getPiece(new Position(0,0)));

        board.placeTotem(new Position(  1,0),board.returnTheTotem(Mark.X));
        board.placePawn(new Position(  2,0),new Pawn(Mark.X,Color.BLACK));

        board.placeTotem(new Position(  0,0),board.returnTheTotem(Mark.X));
        board.placePawn(new Position(  1,0),new Pawn(Mark.X,Color.BLACK));

        assertThrows(OxonoExecption.class, () -> board.placeTotem(new Position(  4,0),board.returnTheTotem(Mark.X)));

    }

    @Test

    public void lastTotem() {
        board.placeTotem(new Position(  0,3),board.returnTheTotem(Mark.O));
        System.out.println(board.getLastMoveTotem(new Totem(Mark.O)));
    }

    @Test
    void movesIfTotemSurrounded() {
        board.movesIfTotemSurrounded(new Position(2,2));
    }


}
