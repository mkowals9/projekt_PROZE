package screens;


import config.AppState;
import config.ConfigParser;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import uimanager.UIManager;

import java.util.ArrayList;
import java.util.Set;

/** Controller responsible for selection of level and game mode */
public class GameAdjustmentsScreenController {
    public Button ReturnBtn;
    public VBox vBoxAdj;

    public void setButtons() {
        ArrayList<Integer> avaiableLvls = new ArrayList<>();
        if (AppState.getInstance().isServerConnected())
            avaiableLvls = AppState.getInstance().getBomberClient().mapLoader.loadMapLvls();
        else {
            Set<String> mapKeys  = ConfigParser.getInstance().getLvlToMapPath().keySet();
            for (String test: mapKeys)
                avaiableLvls.add(Integer.valueOf(test));
        }
        ArrayList<Button> buttons = new ArrayList<>();
        avaiableLvls.sort(Integer::compare);

        for (int lvl: avaiableLvls) {
            Button btn = new Button(String.format("Lvl %s", lvl));
            btn.setStyle("-fx-text-fill: white");
            btn.setOnMouseClicked(e -> {
                AppState.getInstance().setCurrentLvl(lvl);
                setGameScene();
            });
            buttons.add(btn);
        }
        this.vBoxAdj.getChildren().addAll(0,buttons);
    }

    private static void setGameScene() {
        UIManager.getInstance().setGameScreen();
    }


    public void ReturnBtnOnClick() {
        UIManager.getInstance().setMainScreen();
    }

    public void initialize() {
        setButtons();
    }

}
