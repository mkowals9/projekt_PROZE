package elements;

import config.AppState;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import uimanager.UIManager;

/** Class representing players speed power-up on map */
public class SpeedPowerUpBlock extends Block {

    public SpeedPowerUpBlock(int posX, int posY, int maxX, int maxY) {
        super(posX, posY, maxX, maxY);
        setVisible(true);
        active = true;
        movable = true;
        resizeHeight(UIManager.getInstance().getGamePaneHeight(), UIManager.getInstance().getGamePaneHeight()); // initial resize, to fit default window size
        resizeWidth(UIManager.getInstance().getGamePaneWidth(), UIManager.getInstance().getGamePaneWidth()); // initial resize, to fit default window size

    }

    public void resizeStroke() {
        int stroke = (int) ((getHeight() + getWidth()) / 20);
        Image image = UIManager.getInstance().getImageManager().getSpeedPowerUp();
        if (image != null) {
            setFill(new ImagePattern(image));
        }
        else {
            setStyle(String.format("-fx-fill: #6DF698; -fx-stroke: #3399CC; -fx-stroke-width: %d;", stroke));
            System.out.println("LOG SpeedPowerUpBlock setting default background");
        }

    }

    public void interactWithPlayer() {
        AppState appState = AppState.getInstance();
        appState.getCurrentGame().setPlayerSpeed(appState.getGameMode().getSpeed() * 2);
        appState.getCurrentGame().getBlocksToBeRemoved().add(this);
    }
}
