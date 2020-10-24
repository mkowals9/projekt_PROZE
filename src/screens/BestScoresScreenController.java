package screens;

import config.AppState;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import uimanager.UIManager;

import java.util.ArrayList;

/** Class responsible for BestScoresScreen logic */
public class BestScoresScreenController {

    public Button ReturnBtn;
    public VBox vBoxScores;
    public ArrayList<Button> buttons= new ArrayList<>();

    public void initialize() {
        buttons.addAll(AppState.getInstance().getScoreButtons());
        for (Button b: buttons){
            b.setStyle("-fx-text-fill: white");
        }
        vBoxScores.setAlignment(Pos.CENTER);
        vBoxScores.getChildren().addAll(1,buttons);
        }


    public void ReturnBtnOnClick() {
        UIManager.getInstance().setMainScreen();
    }
}

