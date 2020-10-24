package screens;

import config.AppState;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import uimanager.UIManager;

/** Class responsible for ConnectionBrokenScreen logic */
public class ConnectionBrokenScreenController {
    public Label msgLabel;

    public void backButtonOnClick(ActionEvent actionEvent) {
        UIManager.getInstance().setMainScreen();
    }

    public void initialize() {
        AppState appState = AppState.getInstance();
        msgLabel.setText("There has been an error while connecting to server./nPlease check if server is online and try connecting again.");
        appState.setServerConnected(false);
    }
}
