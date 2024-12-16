package g62368_oxono.project.model;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final Piece[][] board;
    private Position posTotemX;
    private Position posTotemO;
    private final int size;
    Totem lastMoveTotem;;

    public Board(int size) {
        this.size = size;
        board = new Piece[size][size];
        initialize();
    }

    /**
     * Initializes the board by placing the starting Totems on the center positions.
     */
    public void initialize() {
        posTotemX = new Position(size/2-1, size/2-1);
        posTotemO = new Position(size/2, size/2);
        board[posTotemO.x()][posTotemO.y()] = new Totem(Mark.O);
        board[posTotemX.x()][posTotemX.y()] = new Totem(Mark.X);
    }

    /**
     * Returns the size of the board.
     *
     * @return the size of the board
     */
    public int getSize() {
        return size;
    }

    /**
     * Returns the position of the last moved {@link Totem}.
     *
     * @param totem the {@link Totem} for which to get the position
     * @return the position of the last moved {@link Totem}
     */
    public Position getPosLastMoveTotem(Totem totem) {
        if (totem.getMark() == Mark.X) {
            return posTotemX =  getTotemPosition(Mark.X);
        }else {
            return posTotemO = getTotemPosition(Mark.O);
        }
    }

    /**
     * Returns the position of a Totem based on its mark.
     *
     * @param mark the mark of the Totem (X or O)
     * @return the position of the specified Totem
     */
    public Position getTotemPosition(Mark mark) {
        return Mark.O == mark ? posTotemO : posTotemX;
    }

    /**
     * Validates and places a {@link Totem} on the board at the specified position.
     *
     * @param position the position where to place the {@link Totem}
     * @param totem the {@link Totem} to place
     * @throws OxonoExecption if the move is invalid
     */
    public void placeTotem(Position position, Totem totem) {
        Position oldPosition = getTotemPosition(totem.getMark());
        validateMoveTotem(position, totem);
        movesIfNotTotemSurrounded(position);
        updateTotem(position,totem,oldPosition);

    }

    /*mets a jour le bord en y placent le totem */
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


    /**
     * Checks if a specific position is inside the board.
     *
     * @param position the position to check
     * @return true if the position is inside the board, false otherwise
     */
    public boolean isInside(Position position) {
        return position.y() >= 0 && position.x() >= 0 && position.y() < size && position.x() < size;
    }

    /**
     * Validates a {@link Totem}'s move to the specified position.
     *
     * @param newPosition the new position for the {@link Totem}
     * @param totem       the {@link Totem} to move
     * @throws OxonoExecption if the move is invalid
     */
    public void validateMoveTotem(Position newPosition, Totem totem) {
        if (!isInside(newPosition) || (!isEmpty(newPosition))) {
            throw new OxonoExecption("La position est en dehors des limites ou deja occupe");
        }
        Position oldPosition = getTotemPosition(totem.getMark());

        // Si le totem est entouré, on vérifie uniquement movesIfTotemSurrounded
        if (isSurrounded(oldPosition)) {
            if (!movesIfTotemSurrounded(oldPosition).contains(newPosition)) {
                throw new OxonoExecption("La position choisie n'est pas valide car le totem est entouré.");
            }
            return; // On sort de la méthode car le mouvement est valide
        }

        // Si le totem n'est pas entouré, on vérifie movesIfNotTotemSurrounded
        if (!movesIfNotTotemSurrounded(oldPosition).contains(newPosition)) {
            throw new OxonoExecption("position n'est pas valide ou pas libre");
        }
    }

    /**
     * Checks if a specific position is empty (contains no {@link Piece}).
     *
     * @param position the position to check
     * @return true if the position is empty, false otherwise
     */
    public boolean isEmpty(Position position) {
        return board[position.x()][position.y()] == null;
    }

    /**
     * Checks if a specific position is surrounded by other {@link Piece}s.
     *
     * @param position the position to check
     * @return true if the position is surrounded, false otherwise
     */
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

    /**
     * Finds valid positions for a surrounded {@link Totem} to move to.
     *
     * @param position the position of the {@link Totem}
     * @return a list of valid positions for the {@link Totem}
     */
    public List<Position> movesIfTotemSurrounded(Position position) {
        List<Position> validPositions = new ArrayList<>();
        boolean anyEmptyFound = false;


        for (Direction dir : Direction.values()) {
            int currentX = position.x();
            int currentY = position.y();

            while (true) {
                // Aller à la case suivante dans la direction
                currentX += dir.getX();
                currentY += dir.getY();

                Position nextPosition = new Position(currentX, currentY);

                // Vérifier si la position est en dehors du plateau
                if (!isInside(nextPosition)) break;

                // Si une case vide est trouvée, l'ajouter et sortir de cette direction
                if (isEmpty(nextPosition)) {
                    validPositions.add(nextPosition);
                    anyEmptyFound = true;
                    break;
                }
            }
        }

        // Si aucune position vide n'est trouvée dans toutes les directions
        if (!anyEmptyFound) {
            return allEmptyPositions();
        }
        return validPositions;
    }


    /**
     * Finds valid positions for a non-surrounded {@link Totem} to move to.
     *
     * @param position the position of the {@link Totem}
     * @return a list of valid positions for the {@link Totem}
     */
    public List<Position> movesIfNotTotemSurrounded(Position position) {
        List<Position> emptyPositions = new ArrayList<>();
        for (Direction dir : Direction.values()) {
            int newX = position.x() + dir.getX(); // on recupere le x de la direction
            int newY = position.y() + dir.getY(); // on recupere le y de la direction

            Position newPOsition = new Position(newX,newY);
            while (isInside(newPOsition)) {
                if (isEmpty(newPOsition)) {
                    emptyPositions.add(newPOsition);
                    //System.out.println(newPOsition);
                }
                else {
                    break;
                }
                newX += dir.getX();
                newY += dir.getY();
                newPOsition = new Position(newX,newY);
            }
        }
        return emptyPositions;
    }


    /**
     * Returns a list of all empty positions on the board.
     *
     * @return a list of all empty positions on the board
     */
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

    /**
     * Checks if a specific position is a winning position for a {@link Pawn}.
     *
     * @param position the position to check
     * @param pawn the {@link Pawn} to check
     * @return true if the position is a winning position for the {@link Pawn}, false otherwise
     */
    public boolean isWin(Position position, Pawn pawn) {
        return checkLine(position.x(), pawn) || checkColumn(position.y(), pawn);
    }

    /**
     * Checks if a specific line (row or column) contains a winning sequence of {@link Piece}s.
     *
     * @param x the index of the line (row or column) to check
     * @param pawn the {@link Piece} to check
     * @return true if the line contains a winning sequence of {@link Piece}s, false otherwise
     */
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
            if (occurenceColor >= 4 || occurenceMark >= 4) {
                return true;
            }
        }
        return false; // Aucun alignement détecté
    }

    /**
     * Checks if a specific column contains a winning sequence of {@link Piece}s.
     *
     * @param y the index of the column to check
     * @param pawn the {@link Piece} to check
     * @return true if the column contains a winning sequence, false otherwise
     */
    public boolean checkColumn(int y, Pawn pawn) {
        int occurenceColor = 0;
        int occurenceMark = 0;
        for (int i = 0; i < size; i++) {
            Piece piece = getPiece(new Position(i, y)); // Récupérer la pièce à la position
            // ignorer les cases vides
            if (piece == null) {
                occurenceMark = 0;
                occurenceColor = 0;
                continue; // On passe directement à la prochaine itération
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
            if (occurenceColor >= 4 || occurenceMark >= 4) {
                return true;
            }
        }
        return false; // Aucun alignement détecté
    }

    public Piece getPiece(Position position) {
        return board[position.x()][position.y()];
    }

    /**
     * Validates and places a {@link Pawn} on the board at the specified position.
     *
     * @param position the position where to place the {@link Pawn}
     * @param pawn the {@link Pawn} to place
     * @throws OxonoExecption if the move is invalid
     */
    public void placePawn(Position position, Pawn pawn) {
        if (lastMoveTotem == null) {
            throw new OxonoExecption("Aucun totem n'a été déplacé ou initialisé.");
        }
        Position totemPosition = getTotemPosition(lastMoveTotem.getMark());

        if (isSurrounded(totemPosition)) {
            // Si le totem est entouré, le pion peut être placé sur n'importe quelle case vide
            if (!allEmptyPositions().contains(position)) {
                throw new OxonoExecption("La position choisie n'est pas vide.");
            }
        } else {
            // Si le totem n'est pas entouré, le pion doit être placé sur une case adjacente vide
            if (!movesIfNotTotemSurrounded(totemPosition).contains(position)) {
                throw new OxonoExecption("La position choisie n'est pas adjacente au totem ou n'est pas vide");
            }
        }

        if (lastMoveTotem.getMark() != pawn.getMark()) {
            throw new OxonoExecption("Le pion ne peut pas être placé si la mark du totem est différente.");
        }
        if (!getValidMovesPawn(lastMoveTotem).contains(position)) {
             throw new OxonoExecption("La position choisie n'est pas une des positions valides pour ce pion.");
        }

        board[position.x()][position.y()] = pawn;

        System.out.println("Pion placé à la position : " + position);
    }


    // trouve le totem avec la mark souhaite
    public Totem returnTheTotem(Mark mark) {
       if (mark == Mark.O){
         return  (Totem) getPiece(posTotemO);
       }
       else {
           return (Totem) getPiece(posTotemX);
       }
    }

    /**
     * Removes a {@link Pawn} from the board at the specified position.
     *
     * @param pawnPosition the position where to remove the {@link Pawn}
     * @throws OxonoExecption if there is no {@link Pawn} at the specified position
     */
    public void removePawn(Position pawnPosition) {
        if (!isEmpty(pawnPosition)) {
            board[pawnPosition.x()][pawnPosition.y()] = null;
        }
    }
    /**
     * Checks if a specific piece is a Totem.
     *
     * @param piece the piece to check
     * @return true if the piece is a Totem, false otherwise
     */
    public boolean isTotem(Piece piece) {
        return piece instanceof Totem;
    }

    /**
     * Sets a Totem on the board at the specified position.
     *
     * @param position the position where to place the Totem
     * @param totem the Totem to place
     */
    public void setTotem(Position position, Totem totem){
        Position old = getTotemPosition(totem.getMark());

        board[position.x()][position.y()] = totem;

        updateTotem(position, totem, old);
    }

/**
 * Sets a Pawn on the board at the specified position.
 *
 * @param position the position where to place the Pawn
 * @param pawn the Pawn to place
 */
public void setPawn(Position position, Pawn pawn) {
    board[position.x()][position.y()] = pawn;
}

    /**
     * Returns a list of valid positions for a {@link Totem} to move.
     *
     * @param totem the {@link Totem} for which to get the valid moves
     * @return a list of valid positions for the {@link Totem} to move
     */
    public List<Position> getValidMovesTotem(Totem totem) {
        Position totemPosition = getTotemPosition(totem.getMark());
        if (isSurrounded(totemPosition)) {
            return movesIfTotemSurrounded(totemPosition);
        } else {
            return movesIfNotTotemSurrounded(totemPosition);
        }
    }

    /**
     * Returns a list of valid positions for a {@link Pawn} to move.
     *
     * @param totem the {@link Totem} for which to get the valid moves
     * @return a list of valid positions for the {@link Pawn} to move
     */
    public List<Position> getValidMovesPawn(Totem totem) {
        Position positionTotem = getPosLastMoveTotem(totem);
        List<Position> posValid = new ArrayList<>();

        for (Direction dir : Direction.values()) {
            int currentX = positionTotem.x() + dir.getX();
            int currentY = positionTotem.y() + dir.getY();
            Position posPossible = new Position(currentX, currentY);

            // Vérifiez si la position est à l'intérieur du plateau et vide
            if (isInside(posPossible) && isEmpty(posPossible)) {
                posValid.add(posPossible);
            }
        }
        if (posValid.isEmpty()) {
            posValid= allEmptyPositions();
            return posValid;
        }
        else {
            return posValid;
        }
    }

    /**
    * Sets a Pawn on the board at the specified position.
    *
    * @param position the position where to place the Pawn
    * @param pawn the Pawn to place
    */
    public void setpawn(Position position, Pawn pawn) {
    board[position.x()][position.y()] = pawn;
    }
}
