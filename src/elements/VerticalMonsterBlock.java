package elements;

import config.AppState;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import uimanager.UIManager;

import java.util.ArrayList;

/** Class representing vertical* monster on map *
 * moving up and down
 */
public class VerticalMonsterBlock extends Block {

    private boolean direction = true;

    public VerticalMonsterBlock(int posX, int posY, int maxX, int maxY) {
        super(posX, posY, maxX, maxY);
        active = true;
        setVisible(true);
        movable = true;
        resizeHeight(UIManager.getInstance().getGamePaneHeight(), UIManager.getInstance().getGamePaneHeight()); // initial resize, to fit default window size
        resizeWidth(UIManager.getInstance().getGamePaneWidth(), UIManager.getInstance().getGamePaneWidth()); // initial resize, to fit default window size
    }

    public void resizeStroke() {
        int stroke = (int) ((getHeight() + getWidth()) / 60);
        Image image = UIManager.getInstance().getImageManager().getVerticalMonster();
        if (image != null) {
            setFill(new ImagePattern(image));
        }
        else {
            setStyle(String.format("-fx-fill: black; -fx-stroke: red; -fx-stroke-width: %d;", stroke));
            System.out.println("LOG VerticalMonsterBlock setting default background");
        }
    }

    public void handle() {
        if (direction) {
            moveUp();
        } else
            moveDown();
    }

    /** moves monster up, checking earlier if movement in that direction
     * is possible*/
    private void moveUp() {
        double speed = AppState.getInstance().getCurrentGame().getBotSpeedVertical();
        ArrayList<Block> blocks = AppState.getInstance().getCurrentGame().getBlocks();

        if (canMoveUp(speed, blocks))
            setY(getY() - speed);
        else {
            direction = !direction;
            setY(getY() + speed);
        }
    }

    /** moves monster down, checking earlier if movement in that direction
     * is possible*/
    private void moveDown() {
        double speed = AppState.getInstance().getCurrentGame().getBotSpeedVertical();
        ArrayList<Block> blocks = AppState.getInstance().getCurrentGame().getBlocks();
        if (canMoveDown(speed, blocks))
            setY(getY() + speed);
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

