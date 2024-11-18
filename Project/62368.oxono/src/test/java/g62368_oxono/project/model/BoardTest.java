package g62368_oxono.project.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    private Board board;

    @BeforeEach
    public void setUp() {
        board = new Board(6); // Initialisation d'un plateau 5x5
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
        assertFalse(board.isInside(new Position(5, 5)));
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
        Position newPos = new Position(1, 1);
        board.placeTotem(newPos, new Totem(Mark.X));
        assertNotNull(board.getTotemPosition(Mark.X));
        assertEquals(newPos,board.getTotemPosition(Mark.X));


    }

    @Test
    public void testValidPositions() {
        Position pos = new Position(2, 2);
        List<Position> validPositions = board.validPositions(pos);

        for (Position p : validPositions) {
            assertTrue(board.isInside(p));
            assertTrue(board.isEmpty(p));
        }
    }

    @Test
    public void testIsSurrounded() {
        Position pos = new Position(2, 2);
        assertFalse(board.isSurrounded(pos)); // Totem X ne devrait pas être entouré au début
    }

    @Test
    public void testEmptyDirectionPosition() {
        Position pos = new Position(2, 2);
        List<Position> emptyPositions = board.emptyDirectionPosition(pos);

        // Vérifie que les positions vides adjacentes sont correctes
        for (Position p : emptyPositions) {
            assertTrue(board.isInside(p));
            assertTrue(board.isEmpty(p));
        }
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
        // Place des pions pour simuler une victoire sur une ligne
        board.placePawn(new Position(0, 0), new Pawn(Mark.X,Color.BLACK));
        board.placePawn(new Position(0, 1), new Pawn(Mark.X,Color.BLACK));
        board.placePawn(new Position(0, 2), new Pawn(Mark.X,Color.BLACK));
        board.placePawn(new Position(0, 3), new Pawn(Mark.X,Color.BLACK));

        assertTrue(board.Iswin(new Position(0, 0), new Pawn(Mark.X, Color.PINK)));
    }

    @Test
    public void testCheckLine() {
        // Place des pions sur une ligne
        board.placeTotem(new Position(0, 0), new Totem(Mark.X));
        board.placeTotem(new Position(0, 1), new Totem(Mark.X));
        board.placeTotem(new Position(0, 2), new Totem(Mark.X));
        board.placeTotem(new Position(0, 3), new Totem(Mark.X));

        assertTrue(board.checkLine(0, new Pawn(Mark.X, Color.BLACK)));
        assertFalse(board.checkLine(1, new Pawn(Mark.X, Color.BLACK)));
    }

    @Test
    public void testCheckColumn() {
        // Place des pions sur une colonne
        board.placePawn(new Position(0, 0), new Pawn(Mark.X,Color.BLACK));
        board.placePawn(new Position(0, 1), new Pawn(Mark.X,Color.BLACK));
        board.placePawn(new Position(0, 2), new Pawn(Mark.X,Color.BLACK));
        board.placePawn(new Position(0, 3), new Pawn(Mark.X,Color.BLACK));

        assertTrue(board.checkColumn(0, new Pawn(Mark.X, Color.BLACK)));
        assertFalse(board.checkColumn(1, new Pawn(Mark.X, Color.BLACK))); //passe
    }
}
