package elements;

import config.AppState;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import uimanager.UIManager;

/** Class representing horizontal monster on map */
public class ExitBlock extends Block {

    public ExitBlock(int posX, int posY, int maxX, int maxY) {
        super(posX, posY, maxX, maxY);
        setVisible(true);
        active = true;
    }

    public void resizeStroke() {
        int stroke = (int) ((getHeight() + getWidth()) / 60);
        Image image = UIManager.getInstance().getImageManager().getExitBlock();
        if (image != null) {
            setFill(new ImagePattern(image));
        }
        else {
            setStyle(String.format("\"-fx-fill: green; -fx-stroke: darkgreen; -fx-stroke-width: %d;\"", stroke));
            System.out.println("LOG ExitBlock setting default background");
        }
    }

    public void interactWithPlayer() {
        System.out.println("Exit");
        AppState.getInstance().getCurrentGame().finishGame();
    }
}
