package elements;

import javafx.scene.shape.Rectangle;
import uimanager.UIManager;

import java.util.ArrayList;

/** Base class for drawing resizable rectangles on map */
public abstract class Block extends Rectangle {

    protected final int posX;               //pozycja w oknie x, czyli w ktorej kolumnie sie tworzy
    protected final int posY;               //pozycja w oknie y, czyli w ktorym rzedzie sie tworzy
    protected final int maxX;               //max wymiary okna x, czyli po polsku ile blokow jest w x
    protected final int maxY;               //max wymiary okna y, czyli po polsku ile blokow jest w y
    protected boolean active = false;
    protected boolean movable = false;
    private boolean firstResizeH = true;
    private boolean firstResizeW = true;

    public Block(int posX, int posY, int maxX, int maxY) {
        this.posX = posX;
        this.posY = posY;
        this.maxX = maxX;
        this.maxY = maxY;
        resizeHeight(UIManager.getInstance().getGamePaneHeight(), UIManager.getInstance().getGamePaneHeight());// initial resize, to fit default window size
        resizeWidth(UIManager.getInstance().getGamePaneWidth(), UIManager.getInstance().getGamePaneHeight());// initial resize, to fit default window size
    }


    /**
     * resizes itself horizontally based on provided stage width
     */
    public void resizeWidth(double w, double oldWidth) {
        if (!movable)
            resizeWidthNotMovable(w);
        else
            resizeWidthMovable(w, oldWidth);
    }

    /**
     * resizes itself horizontally based on provided stage width
     */
    public void resizeWidthNotMovable(double w) {
        setX(posX * (w / maxX));
        setWidth(w / maxX);
        resizeStroke();
    }

    /**
     * resizes itself horizontally based on provided stage width and old width
     */
    public void resizeWidthMovable(double w, double oldWidth) {
        if (firstResizeW) {
            setX((posX + 0.25) * (w / maxX));
            firstResizeW = false;
        } else
            setX((getX() / oldWidth) * w);
        setWidth(w / (2 * maxX));
        resizeStroke();
    }

    /**
     * resizes itself vertically based on provided stage height
     */
    public void resizeHeight(double h, double oldHeight) {
        if (!movable)
            resizeHeightNotMovable(h, oldHeight);
        else
            resizeHeightMovable(h, oldHeight);
    }

    /**
     * resizes itself vertically based on provided stage width and old width
     */
    public void resizeHeightNotMovable(double h, double oldHeight) {
        setY(posY * (h / maxY));
        setHeight(h / maxY);
        resizeStroke();
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMaxY() {
        return maxY;
    }

    public void respawn(){
        setX((posX + 0.1) * (UIManager.getInstance().getGamePaneWidth() / maxX));
        setY((posY + 0.1) * (uimanager.UIManager.getInstance().getGamePaneHeight() / maxY));
    }

    /**
     * returns current number of map's row in which now is block
     */
    public int getCurrentPosX(){
        return (int) Math.floor(getX() / (UIManager.getInstance().getGamePaneWidth() / maxX));
    }

    /**
     * returns current number of map's column in which now is block
     */
    public int getCurrentPosY(){
        return (int) Math.floor(getY() / (uimanager.UIManager.getInstance().getGamePaneHeight() / maxY));
    }

    /**
     * resizes itself vertically based on provided stage height and old Stage height
     */
    public void resizeHeightMovable(double h, double oldHeight) {
        if (firstResizeH) {
            setY((posY + 0.25) * (h / maxY));
            firstResizeH = false;
        } else
            setY((getY() / oldHeight) * h);
        setHeight(h / (2 * maxY));
        resizeStroke();
    }

    /**
     * resizes stroke in order to resize border while resizing block and set styling
     */
    public abstract void resizeStroke();

    /** checks if certain block does any action or not*/
    public boolean isActive() {
        return active;
    }

    /**
     * responsible for Block-specific actions like moving or exploding
     * Each active block "Knows" what to do, so the GameTimer won't have to
     * Gets called in GameTimer 60 times per sec.
     */
    public void handle() {
    }

    /**
     * interprets for certain block its interaction with player
     */
    public void interactWithPlayer() {}

    /**
     * interprets for certain block its interaction with explosion, defines if block should
     * be destroyed or not
     */
    public void interactWithExplosion() {}

    /**
     * checks if movement to the right side is possible
     */
    public boolean canMoveRight(double dx, ArrayList<Block> blocks) {
        BogusBlock futureSelf = new BogusBlock(posX, posY, maxX, maxY);
        futureSelf.setHeight(getHeight());
        futureSelf.setWidth(getWidth());
        futureSelf.setX(getX()  + dx);
        futureSelf.setY(getY());
        return !block_intersects(futureSelf, blocks);
    }

    /**
     * checks if movement to the left side is possible
     */
    public boolean canMoveLeft(double dx, ArrayList<Block> blocks) {
        BogusBlock futureSelf = new BogusBlock(posX, posY, maxX, maxY);
        futureSelf.setHeight(getHeight());
        futureSelf.setWidth(getWidth());
        futureSelf.setX(getX()  - dx);
        futureSelf.setY(getY());
        return !block_intersects(futureSelf, blocks);
    }

    /**
     * checks if movement up  is possible
     */
    public boolean canMoveUp(double dy, ArrayList<Block> blocks) {
        BogusBlock futureSelf = new BogusBlock(posX, posY, maxX, maxY);
        futureSelf.setHeight(getHeight());
        futureSelf.setWidth(getWidth());
        futureSelf.setX(getX());
        futureSelf.setY(getY()  - dy);
        return !block_intersects(futureSelf, blocks);
    }

    /**
     * checks if movement down  is possible
     */
    public boolean canMoveDown(double dy, ArrayList<Block> blocks) {
        Block futureSelf = new BogusBlock(posX, posY, maxX, maxY);
        futureSelf.setHeight(getHeight());
        futureSelf.setWidth(getWidth());
        futureSelf.setX(getX());
        futureSelf.setY(getY()  + dy);
        return !block_intersects(futureSelf, blocks);
    }

    /**
     * check if current block and other block has the same position
     */
    public boolean positionsMatch(Block other){
        return (other.getCurrentPosX() == getCurrentPosX() &&
                other.getCurrentPosY() == getCurrentPosY());
    }

    /**
     * check if passed SolidBlock (futureSelf) intersects with ANY of the passed blocks
     */
    public boolean block_intersects(Block futureSelf, ArrayList<Block> blocks) {
        for (Block block : blocks) {
            if (futureSelf.intersects(block.getBoundsInParent()))
                return true;
        }
        return false;
    }

    /**
     * check if passed Block (futureSelf) intersects with ANY of the passed blocks
     */
    public boolean intersects(Block futureSelf, ArrayList<ExplosionBlock> blocks) {
        for (Block block : blocks) {
            if (futureSelf.intersects(block.getBoundsInParent()))
                return true;
        }
        return false;
    }

}
