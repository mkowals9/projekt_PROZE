package elements;

import config.AppState;
import game.Game;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import uimanager.UIManager;

import java.util.ArrayList;

/** Class representing player on map */
public class CharacterBlock extends Block{

    private boolean canMove = true;

    public CharacterBlock(int posX, int posY, int maxX, int maxY) {
        super(posX,posY,maxX,maxY);
        movable = true;
        active=true;
        setVisible(true);
        double width = Math.max(UIManager.getInstance().primaryStage.getWidth() - 200, 0);
        double height = Math.max(UIManager.getInstance().primaryStage.getHeight(), 0);
        resizeHeight(height, height);// initial resize
        resizeWidth(width, width);// initial resize
    }

    /**
     * controls player's movement depending on pressed key
     */
    public void move(String key, ArrayList<Block> blocks) {
        double dx = AppState.getInstance().getCurrentGame().getPlayerSpeedHorizontal();
        double dy = AppState.getInstance().getCurrentGame().getPlayerSpeedVertical();
        if (canMove) {
            switch (key) {
                case "W": {
                    if (canMoveUp(dy, blocks))
                        setY(getY() - dy);
                    break;
                }
                case "S": {
                    if (canMoveDown(dy, blocks))
                        setY(getY() + dy);
                    break;
                }
                case "A": {
                    if (canMoveLeft(dx, blocks))
                        setX(getX() - dx);
                    break;
                }
                case "D": {
                    if (canMoveRight(dx, blocks))
                        setX(getX() + dx);
                    break;
                }
                case "SPACE": {
                    canMove = false;
                }
            }
        } else if (key.equals("SPACE"))
            canMove = true;
    }

    public void resizeStroke() {
        int stroke = (int) ((getHeight() + getWidth()) / 20);
        Image image = UIManager.getInstance().getImageManager().getHeroIcon();
        if (image != null) {
            setFill(new ImagePattern(image));
        } else {
            setStyle(String.format("-fx-fill: pink; -fx-stroke: magenta; -fx-stroke-width: %d;", stroke));
            System.out.println("LOG CharacterBlock setting default background");
        }
    }

    /**
     * set parametrs to certain values representing death's state
     */
    public void die(){
        AppState appState = AppState.getInstance();
        appState.getCurrentGame().setPlayerHP(0);
        appState.getCurrentGame().getTimer().stop();
        appState.gameOver();
    }

    /**
     * place bomb on map in current player's position
     */
    public void placeBomb(){
        BombBlock bomb=new BombBlock(getCurrentPosX(), getCurrentPosY(), getMaxX(), getMaxY());
        bomb.resizeStroke();
        Game currentGame = AppState.getInstance().getCurrentGame();
        currentGame.addToGamePane(bomb);
        currentGame.getActive_blocks().add(bomb);
        currentGame.bombPlaced();
    }

    public void interactWithExplosion() {loseHp();}


    public void interactWithMonster() {loseHp();
    }

    private void loseHp() {
        AppState appState = AppState.getInstance();
        int playerHP = appState.getCurrentGame().getPlayerHP();
        if (playerHP > 1)
        {   appState.getCurrentGame().setPlayerHP(playerHP - 1);
            respawn();}
        else
            die();
    }

}


