package screens;

import config.AppState;
import config.ConfigParser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import uimanager.UIManager;

/** Class responsible for LvlFinishedScreen logic */
public class LvlFinishedScreenController {
    @FXML
    public Button ReturnBtn;
    @FXML
    public Label msgLabel;
    @FXML
    public Button ContinueBtn;
    private boolean goToScore = false;

    public void initialize() {
        AppState appState = AppState.getInstance();
        ContinueBtn.setText("Continue");
        int lvl = appState.getCurrentLvl();
        if (!ConfigParser.getInstance().getLvlToMapPath().containsKey(String.valueOf(lvl)) && !appState.isServerConnected()) {
            msgLabel.setText("You finished the game, congratulations! \n(Connect to server for more levels)");
            goToScore = true;
        }
        else if (!ConfigParser.getInstance().isLvlAvailable(appState.getCurrentLvl())) {
            msgLabel.setText("You finished the game, congratulations!");
            goToScore = true;
        }
        else
            msgLabel.setText(String.format("You finished lvl %d", (AppState.getInstance().getCurrentLvl() - 1)));
    }

    public void ContinueBtnOnClick(ActionEvent actionEvent) {
        if (goToScore)
            UIManager.getInstance().setGameOverScreen();
        else
            UIManager.getInstance().setGameScreen();
    }

    public void ReturnBtnOnClick() {
        UIManager.getInstance().setGameOverScreen();
    }
}
