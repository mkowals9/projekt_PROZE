package elements;


import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import uimanager.UIManager;

/** Class representing indestructible element on map */
public class SolidBlock extends Block {

    public SolidBlock(int posX, int posY, int maxX, int maxY) {
        super(posX, posY, maxX, maxY);
        setVisible(true);
    }

    public void resizeStroke() {
        int stroke = (int) ((getHeight() + getWidth()) / 60);
        Image image = UIManager.getInstance().getImageManager().getSolidBlock();
        if (image != null) {
            setFill(new ImagePattern(image));
            setStyle(String.format("-fx-stroke: black; -fx-stroke-width: %d;", stroke));
        }
        else {
            setStyle(String.format("\"-fx-fill: transparent; -fx-stroke: black; -fx-stroke-width: %d;\"", stroke));
            System.out.println("LOG WeakBlock setting default background");
        }
    }
}
