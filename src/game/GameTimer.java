package game;

import elements.Block;
import elements.CharacterBlock;
import elements.ExplosionBlock;
import javafx.animation.AnimationTimer;
import config.AppState;
import java.util.ArrayList;

/** Class responsible for in-game animations */
public class GameTimer extends AnimationTimer {

    public ArrayList<String> input;
    private final Game game;
    private float timePlay;
    private volatile boolean isRunning;

    public GameTimer(ArrayList<String> input, Game game) {
        this.input = input;
        this.game = game;
        isRunning=true;
        timePlay=0;
    }

    /** called 60 times per second, responsible for in-game animations */
    @Override
    public void handle(long now) {
        handlePlayersInput();
        activeBlocksActions();
        handlePlayerInteractionsWithActiveBlocks();
        game.removeDeletedBlocks();
        manageTimeProperty();
    }

    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public void stop(){
        super.stop();
        isRunning=false;
    }

    @Override
    public void start(){
        super.start();
        isRunning=true;
    }

    public void pause(){
        if (isRunning){
            stop();
        }
        else{
            start();
        }
    }

    public float getTimePlayInSeconds() {
        return timePlay / 60;
    }

    /** responsible for handling input from keyboard like moving or placing a bomb */
    private void handlePlayersInput() {
        if (input.contains("D")) {
            game.getPlayer().move("D",game.getBlocks());
        }
        if (input.contains("A")) {
            game.getPlayer().move("A",game.getBlocks());
        }
        if (input.contains("W")) {
            game.getPlayer().move("W",game.getBlocks());
        }
        if (input.contains("S")) {
            game.getPlayer().move("S",game.getBlocks());
        }
        if (input.contains("SPACE")) {
            if (game.canPlaceBomb())
                game.getPlayer().placeBomb();
            input.remove("SPACE");
        }
    }

    /** responsible for block's animations like monsters moving or bomb exploding */
    private void activeBlocksActions() {
        for (Block block: game.getActive_blocks()) {
            block.handle();
        }
        for(ExplosionBlock blockE: game.getExplosion_blocks()){
            blockE.handle();
        }

    }

    /** responsible for interactions with active blocks like monsters/power-ups/exits etc. */
    private void handlePlayerInteractionsWithActiveBlocks() {
        for (Block block: game.getActive_blocks()) {
            if (block instanceof CharacterBlock)
                continue;
            if (game.getPlayer().intersects(block.getBoundsInParent()))
                block.interactWithPlayer();
        }
    }

    private void manageTimeProperty() {
        timePlay+=1;
        game.timePlayPropertyProperty().setValue(Math.round(timePlay/60));
    }

}
