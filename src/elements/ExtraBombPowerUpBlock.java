package elements;

import config.AppState;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import uimanager.UIManager;

/** Class representing extra-bomb power-up on map */
public class ExtraBombPowerUpBlock extends Block {

    public ExtraBombPowerUpBlock(int posX, int posY, int maxX, int maxY) {
        super(posX, posY, maxX, maxY);
        setVisible(true);
        movable = true;
        active = true;
        resizeHeight(UIManager.getInstance().getGamePaneHeight(), UIManager.getInstance().getGamePaneHeight()); // initial resize, to fit default window size
        resizeWidth(UIManager.getInstance().getGamePaneWidth(), UIManager.getInstance().getGamePaneWidth()); // initial resize, to fit default window size

    }

    public void resizeStroke() {
        int stroke = (int) ((getHeight() + getWidth()) / 60);
        Image image = UIManager.getInstance().getImageManager().getExtraBomb();
        if (image != null) {
            setFill(new ImagePattern(image));
        } else {
            setStyle(String.format("-fx-fill: #66CCFF; -fx-stroke: #3399CC; -fx-stroke-width: %d;", stroke));
            System.out.println("LOG BotSlowPowerUpBlock setting default background");
        }
    }


        public void interactWithPlayer() {
        AppState appState = AppState.getInstance();
        appState.getCurrentGame().addExtraBomb();
        appState.getCurrentGame().getBlocksToBeRemoved().add(this);
    }
}
