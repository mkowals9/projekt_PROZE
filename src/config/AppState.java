package config;

import game.Game;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;
import network.BomberClient;
import uimanager.UIManager;

import java.util.ArrayList;
import java.util.stream.Collectors;

/** Class responsible for storing current session data like username, score, lvl etc */
public class AppState {

    private static AppState instance;
    private int currentLvl;
    private SimpleLongProperty pointsProperty = new SimpleLongProperty();
    private SimpleStringProperty difficultyProperty=new SimpleStringProperty();
    private SimpleIntegerProperty lvlProperty= new SimpleIntegerProperty();
    private SimpleIntegerProperty hPProperty = new SimpleIntegerProperty();
    volatile private ArrayList<Score> bestScores;
    private Score currentScore;
    private String userName = "User";
    private Game currentGame;
    private GameMode gameMode;
    private BomberClient bomberClient;
    private boolean serverConnected = false;
    private String chosenHero;

    public GameMode getGameMode() {
        if (gameMode != null)
            return gameMode;
        return ConfigParser.getInstance().getGameMode("easy");
    }

    public SimpleLongProperty pointsPropertyProperty() {
        return pointsProperty;
    }

    public SimpleStringProperty difficultyPropertyProperty() {
        return difficultyProperty;
    }

    public SimpleIntegerProperty lvlPropertyProperty() {
        return lvlProperty;
    }

    public SimpleIntegerProperty hPPropertyProperty() {
        return hPProperty;
    }

    public String getUserName() {
        return userName;
    }

    public ArrayList<Score> getBestScores() {
        return bestScores;
    }

    public void setBestScores(ArrayList<Score> scores) {
        this.bestScores = scores;
    }

    public int getCurrentLvl() {
        return currentLvl;
    }

    public void setCurrentLvl(int currentLvl) {
        this.currentLvl = currentLvl;
    }

    public Score getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(Score currentScore) {
        this.currentScore = currentScore;
        pointsProperty.setValue(currentScore.getValue());
    }


    public static AppState getInstance() {
        if (instance == null) {
            instance = new AppState() {};
            instance.bestScores = Score.getScores();
        }
        return instance;
    }

    public void stopTimer() {
        if (currentGame != null)
            currentGame.stopTimer();
    }

    public ArrayList<Button> getScoreButtons() {
        ArrayList<Button> buttons = new ArrayList<>();
        ArrayList<Score> fiveBestScores = (ArrayList<Score>) bestScores.stream().limit(5).collect(Collectors.toList());
        fiveBestScores.sort((score, t1) -> Integer.compare(t1.getValue(), score.getValue()));
        for (Score score: fiveBestScores) {
            buttons.add(score.toButton());
        }
        return buttons;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
        difficultyProperty.setValue(gameMode.getName());
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public void addScore(Score newScore) {
        bestScores.add(newScore);
        saveScores();
    }

    public void saveScores(){
        Score.saveScores();
    }

    public void gameOver() {
        UIManager.getInstance().setGameOverScreen();
    }

    public BomberClient getBomberClient() {
        return bomberClient;
    }

    public void setBomberClient(BomberClient bomberClient) {
        this.bomberClient = bomberClient;
    }

    public boolean isServerConnected() {
        return serverConnected;
    }

    public void setServerConnected(boolean serverConnected) {
        this.serverConnected = serverConnected;
    }

    public void clearScore() {
        setCurrentScore(new Score(getUserName(), 0));
    }

    public void bonusPoints() {
        currentScore.setValue(currentScore.getValue() + currentLvl * 500);
    }

    public Game getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(Game currentGame) {
        this.currentGame = currentGame;
    }

    public String getChosenHero() {
        if (chosenHero == null)
            if (UIManager.getInstance().getImageManager().getAvailableHeroes().size() != 0)
                return UIManager.getInstance().getImageManager().getAvailableHeroes().get(0);
        return chosenHero;
    }

    public void setChosenHero(String chosenHero) {
        this.chosenHero = chosenHero;
    }

    private AppState() {
        if (instance != null) {
            throw new IllegalStateException("Singleton already constructed");
        }
    }

}
