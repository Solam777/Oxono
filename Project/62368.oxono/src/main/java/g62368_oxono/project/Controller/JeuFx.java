//package g62368_oxono.project.Controller;
//
//import g62368_oxono.project.View.BoardFx;
//import g62368_oxono.project.View.FxView;
//import g62368_oxono.project.model.*;
//import g62368_oxono.project.model.Observer.ObservableEvent;
//import g62368_oxono.project.model.Observer.Observer;
//import javafx.scene.input.MouseEvent;
//
//public class JeuFx implements Observer {
//    private final FxView fxView;
//    private Game game;
//    private BoardFx boardFx;
//    private Position clickTotemPos;
//    private Position clickedPos;
//    private  boolean isPlacingTotem;
//    private Totem lasTotemPlay;
//
//    boolean canPlacePawn;
//
//    public JeuFx(Game game, FxView view) {
//        this.game = game;
//        this.boardFx = boardFx;
//        this.fxView = view;
//        fxView.setController(this);
//        game.addObserver(this);
//    }
//
//    public void start() {
//        game.initializeGame(6);
//    }
//
//
//
//
//
//    public void handleClick(Position position) {
//        System.out.println("Click at: " + position.y() + ", " + position.x());
//
//        try {
//            Piece clickedPiece = game.getPiece(position);
//
//            // Sélection d'un totem
//            if (!isPlacingTotem && clickedPiece instanceof Totem) {
//                clickTotemPos = position;
//                FxView.setStatus("Sélectionnez une destination pour le totem");
//                return;
//            }
//
//            // Déplacement du totem
//            if (!isPlacingTotem && clickTotemPos != null) {
//                if (game.isValidMove(position, (Totem) game.getPiece(clickTotemPos))) {
//                    lasTotemPlay = (Totem) game.getPiece(clickTotemPos);
//                    game.playTotem(position, lasTotemPlay);
//                    isPlacingTotem = true;
//                    clickTotemPos = null; // Réinitialise la sélection
//                    FxView.setStatus("Totem déplacé avec succès.");
//                    System.out.println("totem placet at "+ position);
//
//                }
//                return;
//            }
//
//            // Gestion des clics pour les pions
//
//            if (isPlacingTotem) {
//                Pawn pawn = game.getCurrentPlayer().getNextPawn(lasTotemPlay.getMark()); // Récupère le prochain pion à jouer
//                game.playPawn(position, pawn);
//                FxView.setStatus("Pion placé !");
//                System.out.println("pawn placet at "+ position);
//
//                game.switchPlayer();
//                return;
//            }
//
//            FxView.setStatus("Action non reconnue ou mouvement impossible.");
//        } catch (OxonoExecption e) {
//            FxView.setStatus("Erreur : " + e.getMessage());
//        }
//        boardFx.updateBoard(game);
//    }
//
//
//
//    @Override
//    public void update(ObservableEvent event) {
//        switch (event) {
//            case GAME_START:
//                fxView.updateBoard(game);
//                break;
//            case PLACE_PAWN:
//                fxView.updateBoard(game);
//                break;
//            case MOVE_TOTEM:
//                fxView.updateBoard(game);
//                break;
//            case WIN:
//
//                System.out.println("Le joueur " + game.getCurrentPlayer() + " a gagné!");
//                break;
//            case DRAW:
//                System.out.println("Match nul!");
//                break;
//            case QUIT:
//                System.out.println("Le jeu est terminé.");
//                break;
//            default:
//                break;
//        }
//    }
//}



package g62368_oxono.project.Controller;

import g62368_oxono.project.View.BoardFx;
import g62368_oxono.project.View.FxView;
import g62368_oxono.project.model.*;
import g62368_oxono.project.model.Observer.ObservableEvent;
import g62368_oxono.project.model.Observer.Observer;

public class JeuFx implements Observer {
    private final FxView fxView;
    private Game game;
    private BoardFx boardFx;
    private Position clickTotemPos;
    private boolean isPlacingTotem;
    private Totem lastTotemPlay;

    public JeuFx(Game game, FxView view) {
        this.game = game;
        this.fxView = view;
        this.boardFx = boardFx; // Fix: properly initialize boardFx
        fxView.setController(this);
        game.addObserver(this);
    }

    public void start() {
        game.initializeGame(6);
    }

    public void handleClick(Position position) {
        try {
            Piece clickedPiece = game.getPiece(position);

            if (!isPlacingTotem) {
                if (clickedPiece instanceof Totem) {
                    clickTotemPos = position;
                    FxView.setStatus("Sélectionnez une destination pour le totem");
                    return;
                }

                if (clickTotemPos != null) {
                    if (game.isValidMove(position, (Totem) game.getPiece(clickTotemPos))) {
                        lastTotemPlay = (Totem) game.getPiece(clickTotemPos);
                        game.playTotem(position, lastTotemPlay);
                        isPlacingTotem = true;
                        clickTotemPos = null;
                        FxView.setStatus("Totem déplacé avec succès.");
                    }
                    return;
                }
            }

            if (isPlacingTotem) {
                Pawn pawn = game.getCurrentPlayer().getNextPawn(lastTotemPlay.getMark());
                game.playPawn(position, pawn);
                FxView.setStatus("Pion placé !");
                isPlacingTotem = false; // Fix: reset isPlacingTotem after pawn placement
                game.switchPlayer();
                return;
            }

            FxView.setStatus("Action non reconnue ou mouvement impossible.");
        } catch (OxonoExecption e) {
            FxView.setStatus("Erreur : " + e.getMessage());
        }
        boardFx.updateBoard(game);
    }

    @Override
    public void update(ObservableEvent event) {
        switch (event) {
            case GAME_START:
            case PLACE_PAWN:
            case MOVE_TOTEM:
                fxView.updateBoard(game);
                break;
            case WIN:
                FxView.setStatus("Le joueur " + game.getCurrentPlayer() + " a gagné!");
                break;
            case DRAW:
                FxView.setStatus("Match nul!");
                break;
            case QUIT:
                FxView.setStatus("Le jeu est terminé.");
                break;
        }
    }
}