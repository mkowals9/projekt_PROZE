package game;

import config.AppState;
import config.ConfigParser;
import elements.Block;
import elements.CharacterBlock;
import elements.ExplosionBlock;
import elements.MapParser;
import javafx.beans.property.SimpleFloatProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import uimanager.UIManager;

import java.util.ArrayList;

/** Class directly responsible for gameplay */
public class Game {

    private final Pane scorePane;
    private final Pane gamePane;
    private final int lvl;
    private int playerHP;
    private int playerSpeed;
    private int botSpeed;
    private int freeBombCount = 1;
    private float timePlay;
    private ArrayList<Block> blocks = new ArrayList<>();
    private final ArrayList<Block> blocks_without_player = new ArrayList<>();
    private final ArrayList<Block> active_blocks = new ArrayList<>();
    private final ArrayList<Block> blocksToRemove = new ArrayList<>();
    private final ArrayList<ExplosionBlock> explosion_blocks= new ArrayList<>();
    private CharacterBlock player = null;
    public ArrayList<String> input = new ArrayList<>();
    private SimpleFloatProperty timePlayProperty=new SimpleFloatProperty();
    private GameTimer timer;

    public Game(Pane gamePane, Pane scorePane, int lvl)  {
        this.gamePane = gamePane;
        this.scorePane = scorePane;
        this.lvl = lvl;
        gamePane.getChildren().removeAll(gamePane.getChildren());
        if (!setMap())
            return;
        AppState appState = AppState.getInstance();

        setSpeedsAndHp();
        setProperties();
        setPlayer();
        setListeners();
        gamePane.requestFocus();
        timer = new GameTimer(input, this);
        timer.start();
    }

    public GameTimer getTimer() {
        return timer;
    }

    /** returns block representing player */
    public CharacterBlock getPlayer() {
        return player;
    }

    /** returns list of active blocks */
    public ArrayList<Block> getActive_blocks() {
        return active_blocks;
    }

    /** returns list of inactive (static, without actions) blocks */
    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    /** returns list of blocks representing explosions*/
    public ArrayList<ExplosionBlock> getExplosion_blocks() {
        return explosion_blocks;
    }

    /** returns list of blocks which should be removed from map because of explosion*/
    public ArrayList<Block> getBlocksToBeRemoved() {
        return blocksToRemove;
    }

    public SimpleFloatProperty timePlayPropertyProperty() {
        return timePlayProperty;
    }

    /** returns scaled bot speed depending on game window size*
     * game mode contains a number which represents how much % of map height/width bot travels in 1 sec.
     * bot's Block is drawn if 60fps, so in order to scale that speed I'm dividing mentioned number by 60
     * and converting percentage to a fraction by dividing by 100 (60*100 = 6000)
     */
    public double getBotSpeedHorizontal() {
        return botSpeed * UIManager.getInstance().getGamePaneWidth()/ 6000.0;
    }

    /** returns scaled bot speed depending on game window size*
     * game mode contains a number which represents how much % of map height/width bot travels in 1 sec.
     * bot's Block is drawn if 60fps, so in order to scale that speed I'm dividing mentioned number by 60
     * and converting percentage to a fraction by dividing by 100 (60*100 = 6000)
     */
    public double getBotSpeedVertical() {
        return botSpeed * UIManager.getInstance().getGamePaneHeight() / 6000.0;
    }
    /** returns scaled players speed depending on game window size */
    public double getPlayerSpeedHorizontal() {
        return playerSpeed * UIManager.getInstance().getGamePaneWidth()/ 6000.0;
    }

    /** returns scaled players speed depending on game window size */
    public double getPlayerSpeedVertical() {
        return playerSpeed * UIManager.getInstance().getGamePaneHeight() / 6000.0;
    }

    public Pane getGamePane() {
        return gamePane;
    }

    public int getPlayerHP() {
        return playerHP;
    }

    public void setPlayerHP(int playerHP) {
        this.playerHP = playerHP;
        AppState.getInstance().hPPropertyProperty().setValue(playerHP);
    }

    public void setPlayerSpeed(int playerSpeed) {
        this.playerSpeed = playerSpeed;
    }

    public void setBotSpeed(int botSpeed) {
        this.botSpeed = botSpeed;
    }

    /** adds new block to GamePane*/
    public void addToGamePane(Block e) {
        gamePane.getChildren().add(e);
    }

    /** adds to list of explosion blocks and GamePane new explosion block*/
    public void addExplosion(ExplosionBlock explosion) {
        gamePane.getChildren().add(explosion);
        explosion_blocks.add(explosion);
    }

    public void finishGame() {
        timer.stop();
        clearGamePane();
        AppState.getInstance().getCurrentScore().setValue(AppState.getInstance().getCurrentScore().getValue() + calculateScore());
        AppState.getInstance().setCurrentLvl(AppState.getInstance().getCurrentLvl() + 1);
        UIManager.getInstance().setLvlFinishedScreen();
    }

    /** removes blocks destroyed in explosions from lists of blocks and GamePane,
     * and in the end clear list of explosion blocks*/
    public void removeDeletedBlocks() {
        scorePane.managedProperty().set(false);
        gamePane.getChildren().removeAll(blocksToRemove);
        blocks.removeAll(blocksToRemove);
        active_blocks.removeAll(blocksToRemove);
        blocks_without_player.removeAll(blocksToRemove);
        explosion_blocks.removeAll(blocksToRemove);
        blocksToRemove.clear();
    }

    public boolean canPlaceBomb() {
        return freeBombCount > 0;
    }

    public void bombPlaced() {
        freeBombCount -= 1;
    }

    public void bombExploded() {
        freeBombCount += 1;
    }

    public void addExtraBomb() {
        freeBombCount += 1;
    }

    public void stopTimer() {
        if (timer != null)
            timer.stop();
    }

    private int calculateScore(){
        int pointsForMap = ConfigParser.getInstance().getPointsForMap(lvl);
        int gameModeDivider = AppState.getInstance().getGameMode().getScoreDivider();
        int time = (int) timer.getTimePlayInSeconds();
        int lostHP = 1;
        int score = pointsForMap - (time * gameModeDivider * lostHP);

        return Math.max(score, 0);
    }

    /** loads list of blocks, sets them on the screen and saves lists of active/inactive blocks */
    private boolean setMap() {
        if (AppState.getInstance().isServerConnected())
            try {
                blocks = AppState.getInstance().getBomberClient().mapLoader.loadMap(lvl);
            } catch (Exception exception) {
                UIManager.getInstance().setConnectionBrokenScreen();
                return false;
            }
        else
            blocks = MapParser.parseTxt(ConfigParser.getInstance().getMapTxt(lvl));
        gamePane.getChildren().addAll(blocks);
        for (Block block: blocks) {
            if (block.isActive())
                active_blocks.add(block);
            if (!(block instanceof CharacterBlock))
                blocks_without_player.add(block);
        }
        blocks.removeAll(active_blocks);
        return true;
    }

    private void setListeners(){
        UIManager.getInstance().setStageWidthChangeListener((observableValue, oldWidth, newWidth) -> {
            reziseHorizontally((Double) newWidth, (Double) oldWidth);
            moveScore((Double) newWidth);
        });

        UIManager.getInstance().setStageHeightChangeListener((observableValue, oldHeight, new_height) ->{
            reziseVertically((Double) new_height, (Double) oldHeight);
        });

        gamePane.addEventFilter(KeyEvent.KEY_PRESSED,
                event-> {String code = event.getCode().toString().toUpperCase();
                    if (code.equalsIgnoreCase("P")) {
                        timer.pause();
                    }
                    if (!input.contains(code))
                        input.add(code);
                });

        gamePane.addEventFilter(KeyEvent.KEY_RELEASED,
                event-> {String code = event.getCode().toString().toUpperCase();
                    input.remove(code);
                });
    }

    /** Sets properties responsible of in-game display of game parameters */
    private void setProperties() {
        AppState appState = AppState.getInstance();
        appState.difficultyPropertyProperty().setValue(appState.getGameMode().getName());
        appState.lvlPropertyProperty().setValue(appState.getCurrentLvl());
        appState.hPPropertyProperty().setValue(appState.getGameMode().getHp());
        appState.pointsPropertyProperty().setValue(appState.getCurrentScore().getValue());
        timePlayPropertyProperty().setValue(timePlay);
    }

    private void setSpeedsAndHp() {
        AppState appState = AppState.getInstance();
        playerSpeed = appState.getGameMode().getSpeed();
        botSpeed = appState.getGameMode().getBotSpeed();
        playerHP = appState.getGameMode().getHp();
    }

    /** set player from list of active blocks */
    private void setPlayer() {
        for (Block block: active_blocks) {
            if (block instanceof CharacterBlock)
                player = (CharacterBlock) block;
        }
    }

    /** resize map horizontally */
    private void reziseHorizontally(Double newWidth, Double oldWidth) {
        for (Block b: blocks)
            b.resizeWidth(newWidth - 200, oldWidth - 200);
        for (Block b: active_blocks)
            b.resizeWidth(newWidth - 200, oldWidth - 200);
        for (Block b: explosion_blocks)
            b.resizeWidth(newWidth - 200, oldWidth - 200);
    }

    /** resize map vertically */
    private void reziseVertically(Double newHeight, Double oldHeight) {
        // -40 for window's top bar.
        for (Block b: blocks) {
            b.resizeHeight(newHeight - 40, oldHeight - 40);
        }
        for (Block b: active_blocks) {
            b.resizeHeight(newHeight - 40, oldHeight - 40);
        }
        for (Block b: explosion_blocks) {
            b.resizeHeight(newHeight - 40, oldHeight - 40);
        }
    }

    /** After resizing gamePane, moves scorePane pane so it won't overlap with gamePane */
    private void moveScore(Double distance){
        scorePane.setLayoutX(distance - 200);
    }

    private void clearGamePane() {
        gamePane.getChildren().removeAll(gamePane.getChildren());
        for (Node node: scorePane.getChildren()) {
            if (node instanceof Button)
                node.setVisible(true);
        }
    }
}

