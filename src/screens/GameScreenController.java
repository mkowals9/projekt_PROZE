package screens;

import config.AppState;
import game.Game;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import uimanager.UIManager;

/** Class responsible for GameScreen logic */
public class GameScreenController {

    public Pane gamePane;
    public Pane scorePane;
    public Button startButton;
    public Button backButton;
    private static Game game;
    public  Label hPValueLabel, difficultyValueLabel, timeValueLabel,levelValueLabel, pointsValueLabel;


    public void backButtonOnClick() {
        AppState appState = AppState.getInstance();
        UIManager.getInstance().setMainScreen();
        appState.stopTimer();
        appState.setCurrentGame(null);
        resetingLabels();
        appState.clearScore();
    }

    public static Game getGame() {
        return game;
    }

    /** Creates new instance of Game */
    public void startButtonOnClick() {
        game = new Game(gamePane, scorePane, AppState.getInstance().getCurrentLvl());
        AppState.getInstance().setCurrentGame(game);
        startButton.setVisible(false);
        bindingLabels();
    }

    /** Binds game's parametrs to labels */
    public void bindingLabels(){
        difficultyValueLabel.textProperty().bind(AppState.getInstance().difficultyPropertyProperty());
        levelValueLabel.textProperty().bind((AppState.getInstance().lvlPropertyProperty()).asString());
        pointsValueLabel.textProperty().bind(AppState.getInstance().pointsPropertyProperty().asString());
        timeValueLabel.textProperty().bind((AppState.getInstance().getCurrentGame().timePlayPropertyProperty()).asString());
        hPValueLabel.textProperty().bind((AppState.getInstance().hPPropertyProperty()).asString());
    }

    /** Resets labels*/
    public void resetingLabels(){
        pointsValueLabel.textProperty().unbind();
        timeValueLabel.textProperty().unbind();
        hPValueLabel.textProperty().unbind();

        pointsValueLabel.textProperty().set("0");
        timeValueLabel.textProperty().set("0");
        hPValueLabel.textProperty().set("0");
    }

}
