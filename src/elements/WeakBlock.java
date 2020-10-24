package elements;

import config.AppState;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import uimanager.UIManager;

/** Class representing destructible element on map */
public class WeakBlock extends Block {

    public WeakBlock(int posX, int posY, int maxX, int maxY) {
        super(posX, posY, maxX, maxY);
        setVisible(true);
    }

    public void resizeStroke() {
        int stroke = (int) ((getHeight() + getWidth()) / 60);
        Image image = UIManager.getInstance().getImageManager().getWeakBlock();
        if (image != null) {
            setFill(new ImagePattern(image));
            setStyle(String.format("-fx-stroke: #452D18; -fx-stroke-width: %d;", stroke));
        }
        else {
            setStyle(String.format("-fx-fill: sandybrown; -fx-stroke: #452D18; -fx-stroke-width: %d;", stroke));
        }
    }

    public void interactWithExplosion() {
        AppState.getInstance().getCurrentGame().getBlocksToBeRemoved().add(this);
    }

}
