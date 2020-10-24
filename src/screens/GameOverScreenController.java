package screens;

import config.AppState;
import config.Score;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import uimanager.UIManager;

/** Class responsible for GameOverScreen logic */
public class GameOverScreenController {
    public Label scoreLabel;

    public void initialize() {
        AppState appState = AppState.getInstance();
        Score score = appState.getCurrentScore();
        scoreLabel.setText(String.format("Your score is %s", score.getValue()));
        if (appState.isServerConnected())
            appState.getBomberClient().scoreLoader.sendScore(score);
        appState.addScore(score);
        appState.saveScores();
        AppState.getInstance().clearScore();

    }
    public void scoresButtonOnClick(ActionEvent actionEvent) {
        UIManager.getInstance().setBestScoresScreen();
    }

    public void backButtonOnClick(ActionEvent actionEvent) {
        UIManager.getInstance().setMainScreen();
    }
}
