package screens;

import config.AppState;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.text.TextAlignment;
import network.BomberClient;
import uimanager.UIManager;


/** Class responsible for MainScreen logic */
public class MainScreenController {

    public Button newGameBtn;
    public Button BSBtn;
    public Button SettingsBtn;
    public Button helpBtn;
    public Button netBtn;

    public void newGameBtnOnClick() {
        UIManager.getInstance().setGameAdjustmentsScreen();
    }

    public void helpBtnOnClick() {
        UIManager.getInstance().setHelpScreen();
    }

    public void BSBtnOnClick() {UIManager.getInstance().setBestScoresScreen();};

    public void settingsBtnOnClick() {
        UIManager.getInstance().setSettingsScreen();
    }

    /** Button actions responsible for connecting to server */
    public void netBtnOnClick(ActionEvent actionEvent) {
        AppState appState = AppState.getInstance();
        if (!appState.isServerConnected()) {
            try {
                BomberClient client = new BomberClient();
                appState.setServerConnected(true);
                setNetBtnStyle();
                client.synchronize();
            }
            catch (Exception exception) {
                appState.setServerConnected(false);
                netBtn.setText("Server unreachable!\n(make sure that server is online!)");
                netBtn.setStyle("-fx-background-color: darkred");
            }
        }
        else {
            appState.setServerConnected(false);
            appState.getBomberClient().close();
            setNetBtnStyle();
        }
    }

    public void initialize() {
        netBtn.setTextAlignment(TextAlignment.CENTER);
        setNetBtnStyle();
    }

    private void setNetBtnStyle() {
        if (AppState.getInstance().isServerConnected()) {
            netBtn.setText("Server connected!");
            netBtn.setStyle("-fx-background-color: darkseagreen");
        } else {
            netBtn.setText("Connect to server.");
            netBtn.setStyle("-fx-background-color: indianred");
        }

    }
}
