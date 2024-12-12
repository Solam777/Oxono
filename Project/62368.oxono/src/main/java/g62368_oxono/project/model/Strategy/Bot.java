package g62368_oxono.project.model.Strategy;

import g62368_oxono.project.View.FxView;
import g62368_oxono.project.model.Game;
import g62368_oxono.project.model.Mark;
import g62368_oxono.project.model.Position;
import g62368_oxono.project.model.Totem;

import java.util.List;

public class Bot {
    FxView fxView;
    Game game;
    Totem lastTotemPlay;

    public Bot() {
        this.fxView = new FxView();
        this.game = new Game();
        this.lastTotemPlay = game.getLastMoveTotem();
    }

    public void PlayBot() {

        List<Position> positionsValideTotem = game.getFreeposTotem(lastTotemPlay);
        List<Position> positionsValidePawn = game.getFreeposPawn(lastTotemPlay);

        int indexpawn= getRandom(0, positionsValidePawn.size());
        Position posPawn = positionsValidePawn.get(indexpawn);
        positionsValidePawn.remove(indexpawn);

        int indexTotem= getRandom(0, positionsValideTotem.size());
        Position posTotem = positionsValidePawn.get(indexTotem);
        positionsValidePawn.remove(indexTotem);

        int indexChoice = getRandom(0, 1);


        if(indexChoice==0){
            lastTotemPlay =new Totem(Mark.O);
        }
        else{
            lastTotemPlay =new Totem(Mark.X);
        }

        game.playTotem(posTotem,lastTotemPlay);
        game.playPawn(posPawn);
        fxView.updateBoard(game); // Mettez à jour la vue après l'action
    }

    public static int getRandom(int min, int max) {

        int range = (max - min) + 1;
        int random = (int) ((range * Math.random()) + min);
        return random;
    }

}
