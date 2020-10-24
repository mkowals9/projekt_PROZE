package screens;

import config.AppState;
import config.ConfigParser;
import config.Score;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import uimanager.UIManager;

/** Responsible for WelcomeScreen logic */
public class WelcomeScreen extends Application {
    public TextField username;
    public Button saveButton;
    @FXML
    public ComboBox<String> heroPicker;

    @Override
    public void start(Stage primaryStage) {
        ConfigParser.setConfigPath("resources/config.xml");
        ConfigParser.getInstance().load();
        UIManager.initUIManager(primaryStage);
        UIManager.getInstance().setWelcomeScreen();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public void onSaveClicked(MouseEvent mouseEvent) {
        String name = username.getText();
        name = name.replace(" ", "_");
        if (name == null || name.length() == 0)
            saveButton.setText("Please write your name");
        else {
            AppState.getInstance().setUserName(name);
            AppState.getInstance().setCurrentScore(new Score(name, 0));
            UIManager.getInstance().setMainScreen();
        }
    }

    public void initialize() {
        ObservableList<String> heroes = FXCollections.observableArrayList();
        heroes.addAll(UIManager.getInstance().getImageManager().getAvailableHeroes());
        heroPicker.setItems(heroes);
        heroPicker.setStyle("-fx-text-fill: white");
        heroPicker.getSelectionModel().selectFirst();
        heroPicker.onActionProperty();
    }

    public void comboAction(ActionEvent actionEvent) {
        AppState.getInstance().setChosenHero(heroPicker.getValue());
    }
}
