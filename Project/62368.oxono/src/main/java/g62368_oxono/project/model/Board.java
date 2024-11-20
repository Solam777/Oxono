package g62368_oxono.project.model;

import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final Piece[][] board;
    private Position posTotemX;
    private Position posTotemO;
    private final int size;
    private Totem lastMoveTotem;
    private Pawn recentPawnPlace;

    public Board(int size) {
        this.size = size;
        board = new Piece[size][size];
        initialize();
    }

    private void initialize() {
        posTotemX = new Position(2, 2);
        posTotemO = new Position(3, 3);
        board[posTotemO.x()][posTotemO.y()] = new Totem(Mark.O);
        board[posTotemX.x()][posTotemX.y()] = new Totem(Mark.X);
    }

    public int getSize() {
        return size;
    }

    public Totem getLastMoveTotem() {
        return lastMoveTotem;
    }



    /*
    verifie si on peut placer le totem si oui il le place
    * */
    public void placeTotem(Position position, Totem totem) {
        Position oldPosition = getTotemPosition(totem.getMark());
        validateMove(oldPosition, totem);
        updateTotem(position,totem,oldPosition);


    }

    private void updateTotem(Position position, Totem totem, Position oldPosition) {
        board[oldPosition.x()][oldPosition.y()] = null;
        board[position.x()][position.y()] = totem;
        if (totem.getMark() == Mark.X) {
            posTotemX = position;
        } else if (totem.getMark() == Mark.O) {
            posTotemO = position;
        }
        lastMoveTotem = totem;
    }

    /*
     * prend la postion du totem
     * */
    public Position getTotemPosition(Mark mark) {
        return Mark.O == mark ? posTotemO : posTotemX;
    }

    /*
     * verifie si ou l'on veut placer son toteme est bien dans le board
     * */
    public boolean isInside(Position position) {
        return position.y() >= 0 && position.x() >= 0 && position.y() < size && position.x() < size;
    }

    /*verifie si la postion est valid et ou on veut placer notre */
    public void validateMove( Position newPosition,Totem totem) {
        if (!isInside(newPosition) || (!isEmpty(newPosition))) {
            throw new OxonoExecption("La position est en dehors des limites ou deja occupe");
        }
        Position oldPosition = getTotemPosition(totem.getMark());
        if (isSurrounded(oldPosition)){
            if (!movesIfTotemSurrounded(oldPosition).contains(newPosition)){
                throw new OxonoExecption("position is bound");
            }
        }
        if ( newPosition.x() == oldPosition || )
    }

    /*
     *
     * */
    public boolean isEmpty(Position position) {
        return board[position.x()][position.y()] == null;
    }

    /*
     * nous donnes une liste de postion valide ou lon peut placer une piece
     * */
//    public List<Position> validPositions(Position position) {
//        if (isSurrounded(position)) {
//            if (movesIfTotemSurrounded(position).isEmpty()) { // si la liste de positon est vide en fonction de sa direction on retourne toutes les potions vides
//                return allEmptyPositions();
//            }
//            return movesIfTotemSurrounded(position);
//        }
//        return allEmptyPositions();
//    }

    /*
     * verifie si la postion ou on va placer le pions sera encercle
     * */
    public boolean isSurrounded(Position position) {
        for (Direction dir : Direction.values()) {
            int newX = position.x() + dir.getX(); // on recupere le x de la direction
            int newY = position.y() + dir.getY(); // on recupere le y de la direction

            if (isInside(new Position(newX, newY))) { // on regarde si la postion est bien dans le tableau
                if (isEmpty(new Position(newX, newY))) {
                    return false; // return false si une position adjacente est vide
                }
            }
        }
        return true;
    }

    /*
     * nous donnes les positions vide de chaque direction en fonctions de la postion
     * */
    public List<Position> movesIfTotemSurrounded(Position position) {
        List<Position> emptyPositions = new ArrayList<>();
        for (Direction dir : Direction.values()) {
            int newX = position.x() + dir.getX(); // on recupere le x de la direction
            int newY = position.y() + dir.getY(); // on recupere le y de la direction

            while (isInside(new Position(newX, newY))) {
                if (isEmpty(new Position(newX, newY))) {
                    emptyPositions.add(new Position(newX, newY));
                    break; // Ajoute la première position vide trouvée dans cette direction
                }
                newX += dir.getX();
                newY += dir.getY();
            }
        }
        return emptyPositions;
    }
    public List<Position> emptypositionDirection(Position position){
        List<Position> emptyPositions = new ArrayList<>();
        for (Direction dir : Direction.values()) {
            int newX = position.x() + dir.getX(); // on recupere le x de la direction
            int newY = position.y() + dir.getY(); // on recupere le y de la direction

            if (isInside(new Position(newX, newY))) {
                if (isEmpty(new Position(newX, newY))) {
                    emptyPositions.add(new Position(newX, newY));
                }
            }
        }
        return emptyPositions;
    }

    /*
     * une liste de toutes les positions vides du board
     * */
    public List<Position> allEmptyPositions() {
        List<Position> emptyPos = new ArrayList<>();
        for (int i = 0; i < getSize(); i++) {
            for (int j = 0; j < getSize(); j++) {
                if (isEmpty(new Position(i, j))) {
                    emptyPos.add(new Position(i, j));
                }
            }
        }
        return emptyPos;
    }

    /*
     * verifie si la ligne est gagnante soit avec 4 couleur  ou 4 mark aligne */
    public boolean Iswin(Position position, Pawn pawn) {
        return checkLine(position.x(), pawn) || checkColumn(position.y(), pawn);
    }

    /*
     * check if each case in the line is same(about color or mark )*/
    public boolean checkLine(int x, Pawn pawn) {
        int occurenceColor = 0;
        int occurenceMark = 0;

        for (int i = 0; i < size; i++) {
            Piece piece = getPiece(new Position(x, i)); // Récupérer la pièce à la position

            // ignorer les cases vides
            if (piece == null) {
                occurenceMark = 0;
                occurenceColor = 0;
                continue; // On passe directement a la prochaine neumeration en sautant le restant du bloc
            }

            // Si c'est un Totem, réinitialiser les compteurs
            if (piece instanceof Totem) {
                occurenceMark = 0;
                occurenceColor = 0;
                continue;
            }

            // Vérification des propriétés si c'est un Pawn
            if (piece instanceof Pawn pawn1) {
                if (pawn.getMark() == pawn1.getMark()) {
                    occurenceMark++;
                } else {
                    occurenceMark = 0;
                }

                if (pawn.getColor() == pawn1.getColor()) {
                    occurenceColor++;
                } else {
                    occurenceColor = 0;
                }
            }
            // Si une séquence gagnante est détectée, retourner immédiatement true
            if (occurenceColor == 4 || occurenceMark == 4) {
                return true;
            }
        }
        return false; // Aucun alignement détecté
    }

    /*
     * check if each case in the column is same(about color or mark )*/
    public boolean checkColumn(int x, Pawn pawn) {
        int occurenceColor = 0;
        int occurenceMark = 0;

        for (int i = 0; i < size; i++) {
            Piece piece = getPiece(new Position(i, x)); // Récupérer la pièce à la position

            // ignorer les cases vides
            if (piece == null) {
                occurenceMark = 0;
                occurenceColor = 0;
                continue; // On passe directement a la prochaine neumeration en sautant le restant du bloc
            }
            // Si c'est un Totem, réinitialiser les compteurs
            if (piece instanceof Totem) {
                occurenceMark = 0;
                occurenceColor = 0;
                continue;
            }
            // Vérification des propriétés si c'est un Pawn
            if (piece instanceof Pawn pawn1) {
                if (pawn.getMark() == pawn1.getMark()) {
                    occurenceMark++;
                } else {
                    occurenceMark = 0;
                }

                if (pawn.getColor() == pawn1.getColor()) {
                    occurenceColor++;
                } else {
                    occurenceColor = 0;
                }
            }
            // Si une séquence gagnante est détectée, retourner immédiatement true
            if (occurenceColor == 4 || occurenceMark == 4) {
                return true;
            }
        }
        return false;
    }

    public Piece getPiece(Position position) {
        return board[position.x()][position.y()];
    }

    public void placePawn(Position position, Pawn pawn) {
        Totem totem = getLastMoveTotem();
        if (totem == null) {
            throw new OxonoExecption("Aucun totem n'a été déplacé ou initialisé.");
        }
        //recupere le totem a la derniere position
        Position totemPosition = getTotemPosition(totem.getMark());

        if (isSurrounded(totemPosition)) {
            if (!allEmptyPositions().contains(position)) {
                throw new OxonoExecption("La position choisie n'est pas vide.");
            }
        } else {
            if (!movesIfTotemSurrounded(totemPosition).contains(position)) {

                throw new OxonoExecption("La position choisie n'est pas vide ou pas adjacente au totem");
            }
        }
        board[position.x()][position.y()] = pawn;
        recentPawnPlace = pawn;
    }

    public Pawn getRecentPawnPlace() {
        return recentPawnPlace;
    }
    public Totem theTotem(Mark mark) {
       if (mark == Mark.O){
         return  (Totem) getPiece(posTotemO);
       }
       else {
           return (Totem) getPiece(posTotemX);
       }

    }

    public void setpawn(Position position, Pawn pawn) {
        board [position.x()][position.y()] = pawn ;
    }
    // peut bouger le totem au premiere endroit dans toute les positions s'il est encercle
    public void fisrtPositinDirection(Position position) {

    }

}
