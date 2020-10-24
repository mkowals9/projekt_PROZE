package elements;

import config.AppState;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import uimanager.UIManager;

import java.util.ArrayList;

/** Class representing horizontal* monster on map.
 * moving left-right
 * */
public class HorizontalMonsterBlock extends Block {

    private boolean direction = true;

    public HorizontalMonsterBlock(int posX, int posY, int maxX, int maxY) {
        super(posX, posY, maxX, maxY);
        active = true;
        setVisible(true);
        movable = true;
        resizeHeight(UIManager.getInstance().getGamePaneHeight(), UIManager.getInstance().getGamePaneHeight()); // initial resize, to fit default window size
        resizeWidth(UIManager.getInstance().getGamePaneWidth(), UIManager.getInstance().getGamePaneWidth()); // initial resize, to fit default window size
    }

    public void resizeStroke() {
        int stroke = (int) ((getHeight() + getWidth()) / 60);
        Image image = UIManager.getInstance().getImageManager().getHorizontalMonster();
        if (image != null) {
            setFill(new ImagePattern(image));
        }
        else {
            setStyle(String.format("-fx-fill: black; -fx-stroke: red; -fx-stroke-width: %d;", stroke));
            System.out.println("LOG M setting default background");
        }
    }

    public void handle() {
        if (direction) {
            moveRight();
        } else
            moveLeft();
    }

    /** moves monster to the right side, checking earlier if movement in that direction
     * is possible*/
    private void moveRight() {
        ArrayList<Block> blocks = AppState.getInstance().getCurrentGame().getBlocks();
        double speed = AppState.getInstance().getCurrentGame().getBotSpeedHorizontal();
        if (canMoveRight(speed, blocks))
            setX(getX() + speed);
        else
            direction = !direction;
    }

    /** moves monster to the left side, checking earlier if movement in that direction
     * is possible*/
    private void moveLeft() {
        ArrayList<Block> blocks = AppState.getInstance().getCurrentGame().getBlocks();
        double speed = AppState.getInstance().getCurrentGame().getBotSpeedHorizontal();
        if (canMoveLeft(speed, blocks))
            setX(getX() - speed);
        else
            direction = !direction;
    }

    public void interactWithPlayer(){
        CharacterBlock player = AppState.getInstance().getCurrentGame().getPlayer();
        player.interactWithMonster();
    }

    public void interactWithExplosion() {
        AppState.getInstance().getCurrentGame().getBlocksToBeRemoved().add(this);
    }

}

