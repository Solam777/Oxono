package g62368_oxono.project.model;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final Piece[][] board;
    private Position posTotemX;
    private Position posTotemO;
    private final int size;
    Totem lastMoveTotem;
    private Pawn recentPawnPlace;

    public Board(int size) {
        this.size = size;
        board = new Piece[size][size];
        initialize();
    }

    public void initialize() {
        posTotemX = new Position(2, 2);
        posTotemO = new Position(3, 3);
        board[posTotemO.x()][posTotemO.y()] = new Totem(Mark.O);
        board[posTotemX.x()][posTotemX.y()] = new Totem(Mark.X);
    }

    public int getSize() {
        return size;
    }

    public Position getPosLastMoveTotem(Totem totem) {
        if (totem.getMark() == Mark.X) {
            return posTotemX =  getTotemPosition(Mark.X);
        }else {
            return posTotemO = getTotemPosition(Mark.O);
        }
    }

    /*
     * renvoie la position du totem
     * */
    public Position getTotemPosition(Mark mark) {
        return Mark.O == mark ? posTotemO : posTotemX;
    }

    /*
   place le totem sur le bord si toutes les conditions y sont*/
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


    /*
     * verifie si où l'on veut placer son toteme est bien dans le board
     * */
    public boolean isInside(Position position) {
        return position.y() >= 0 && position.x() >= 0 && position.y() < size && position.x() < size;
    }

    /*verifie si la postion est valid ou l'on veut placer notre totem ou sil est entrourre ou pas */
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

    public boolean isEmpty(Position position) {
        return board[position.x()][position.y()] == null;
    }

    /*
     * verifie si la postion ou on va placer le pion sera encercle
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
    public boolean isWin(Position position, Pawn pawn) {
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
            if (occurenceColor >= 4 || occurenceMark >= 4) {
                return true;
            }
        }
        return false; // Aucun alignement détecté
    }

    /*
     * check if each case in the column is same(about color or mark )*/
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
        recentPawnPlace = pawn;
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

    public void removePawn(Position pawnPosition) {
        if (!isEmpty(pawnPosition)) {
            board[pawnPosition.x()][pawnPosition.y()] = null;
        }
    }
    public boolean isTotem(Piece piece) {
        return piece instanceof Totem;
    }

    public void setTotem(Position position, Totem totem){
        Position old =getTotemPosition(totem.getMark());

        board[position.x()][position.y()] = totem;

        updateTotem(position, totem, old);

    }

    public void setPawn(Position position, Pawn pawn) {
        board[position.x()][position.y()] = pawn;
    }

    public List<Position> getValidMovesTotem(Totem totem) {
        Position totemPosition = getTotemPosition(totem.getMark());
        if (isSurrounded(totemPosition)) {
            return movesIfTotemSurrounded(totemPosition);
        } else {
            return movesIfNotTotemSurrounded(totemPosition);
        }
    }

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

}
