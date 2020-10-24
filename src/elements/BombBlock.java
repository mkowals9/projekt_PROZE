package elements;

import config.AppState;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import uimanager.UIManager;

/** Class representing bomb on map */
public class BombBlock extends Block{

    private int ticksToExplode;

    public BombBlock(int posX, int posY, int maxX, int maxY){
        super(posX,posY,maxX,maxY);
        ticksToExplode=180;
        movable = false;
        active=true;
        setVisible(true);
        }

    public void resizeStroke() {
        int stroke = (int) ((getHeight() + getWidth()) / 60);
        Image image = UIManager.getInstance().getImageManager().getBomb();
        if (image != null) {
            setFill(new ImagePattern(image));
        }
        else {
            setStyle(String.format("-fx-fill: red; -fx-stroke: #7B241C ; -fx-stroke-width: %d;", stroke));
            System.out.println("LOG BombBlock setting default background");
        }
    }

    public void handle(){
        if(ticksToExplode!=0){
            ticksToExplode=ticksToExplode-1;}
        else{
            AppState.getInstance().getCurrentGame().getBlocksToBeRemoved().add(this);
            explode();
        }
    }

    /**
     * creates blocks representing explosions
     */
    public void explode(){
        ExplosionBlock center = new ExplosionBlock(this.posX,this.posY,this.maxX,this.maxY);
        ExplosionBlock blockRight = new ExplosionBlock(this.posX-1,this.posY,this.maxX,this.maxY);
        ExplosionBlock blockLeft = new ExplosionBlock(this.posX+1,this.posY,this.maxX,this.maxY);
        ExplosionBlock blockDown = new ExplosionBlock(this.posX,this.posY+1,this.maxX,this.maxY);
        ExplosionBlock blockUp = new ExplosionBlock(this.posX,this.posY-1,this.maxX,this.maxY);
        AppState.getInstance().getCurrentGame().addExplosion(center);
        AppState.getInstance().getCurrentGame().addExplosion(blockDown);
        AppState.getInstance().getCurrentGame().addExplosion(blockUp);
        AppState.getInstance().getCurrentGame().addExplosion(blockRight);
        AppState.getInstance().getCurrentGame().addExplosion(blockLeft);
        AppState.getInstance().getCurrentGame().bombExploded();
    }
}

