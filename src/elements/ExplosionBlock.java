package elements;

import config.AppState;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import uimanager.UIManager;

/** Class representing explosion on map */
public class ExplosionBlock extends Block{
    private int explosionTime=60;

    public ExplosionBlock(int posX, int posY, int maxX, int maxY){
        super(posX,posY,maxX,maxY);
        movable = false;
        active=true;
        setVisible(true);
    }

    public void resizeStroke() {
        int stroke = (int) ((getHeight() + getWidth()) / 60);
        Image image = UIManager.getInstance().getImageManager().getExplosion();
        if (image != null) {
            setFill(new ImagePattern(image));
        }
        else {
            setStyle(String.format("-fx-fill: white; -fx-stroke: #FFC300 ; -fx-stroke-width: %d;", stroke));
            System.out.println("LOG ExplosionBlock setting default background");
        }
    }

    public void handle(){
        if (explosionTime == 60) {
            for (Node block: AppState.getInstance().getCurrentGame().getGamePane().getChildren()){
                if (block.intersects(this.getBoundsInLocal())){
                    if (block instanceof SolidBlock)
                        AppState.getInstance().getCurrentGame().getBlocksToBeRemoved().add(this);
                    ((Block) block).interactWithExplosion();
                }
            }
            explosionTime-=1;
        } else if(explosionTime!=0) {
            explosionTime-=1;
        } else {
            AppState.getInstance().getCurrentGame().getBlocksToBeRemoved().add(this);
        }
        for (Block block: AppState.getInstance().getCurrentGame().getActive_blocks()){
            if (positionsMatch(block))
               block.interactWithExplosion();
        }
    }

    public void resizeWidthNotMovable(double w) {
        setX((posX + 0.1) * (w / maxX));
        setWidth(w / (maxX * 1.5));
        resizeStroke();
    }

    public void resizeHeightNotMovable(double h, double oldHeight) {
        setY((posY + 0.1)* (h / maxY));
        setHeight(h / (maxY * 1.5));
        resizeStroke();
    }
}