package uimanager;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/** Singleton class responsible for switching between scenes */
public class UIManager {

    public static UIManager instance;
    private static Scene currentScene;
    private static ImageManager imageManager;
    public Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("PROZE20L");
    }

    /** Returns singleton instance of UIManager */
    public static UIManager getInstance() {
        if (instance == null) {
            instance = new UIManager() {};
            imageManager = new ImageManager();
        }
        return instance;
    }

    public static void initUIManager(Stage stage) {
        getInstance().setPrimaryStage(stage);
    }

    public double getGamePaneWidth() {
        // - 200 for scorePane
        Double width = primaryStage.getWidth() - 200;
        return width > 0 ? width: 0;
    }

    public double getGamePaneHeight() {
        // - 40 for top window bar
        Double height = primaryStage.getHeight() - 40;
        return height > 0 ? height: 0;
    }

    public void setHelpScreen() {
        try {
            VBox help = FXMLLoader.load(new File("src/screens/helpScreen.fxml").toURI().toURL());
            currentScene.setRoot(help);
        } catch (IOException ex) {
            System.out.println("LOG UIManager.setConnectionBrokenScreen() : " + ex);
            setMainScreen();
        }
        
    }

    public void setConnectionBrokenScreen() {
        try {
            AnchorPane welcome = FXMLLoader.load(new File("src/screens/connectionBrokenScreen.fxml").toURI().toURL());
            currentScene.setRoot(welcome);
        } catch (IOException ex) {
            System.out.println("LOG UIManager.setConnectionBrokenScreen() : " + ex);
            setMainScreen();
        }
    }

    public void setGameOverScreen() {
        try {
            GridPane gameOver = FXMLLoader.load(new File("src/screens/gameOverScreen.fxml").toURI().toURL());
            currentScene.setRoot(gameOver);
        } catch (IOException ex) {
            System.out.println("LOG UIManager.setGameOverScene() : " + ex);
            setMainScreen();
        }
    }

    public void setBestScoresScreen() {
        try {
            VBox scores = FXMLLoader.load(new File("src/screens/bestScoresScreen.fxml").toURI().toURL());
            currentScene.setRoot(scores);
        } catch (IOException ex) {
            System.out.println("LOG UIManager.setBestScoresScene() : " + ex);
            setMainScreen();
        }
    }

    public void setMainScreen() {
        try {
            VBox scores = FXMLLoader.load(new File("src/screens/mainScreen.fxml").toURI().toURL());
            currentScene.setRoot(scores);
        } catch (IOException ex) {
            System.out.println("LOG UIManager.setBestScoresScene() : " + ex);
            setMainScreen();
        }
    }

    public void setGameScreen() {
        try {
            GridPane game = FXMLLoader.load(new File("src/screens/gameScreen.fxml").toURI().toURL());
            for (Node node: game.getChildren())
                node.setVisible(true);
            currentScene.setRoot(game);
        } catch (IOException ex) {
            System.out.println("LOG UIManager.setBestScoresScene() : " + ex);
            setMainScreen();
        }
    }

    public void setLvlFinishedScreen() {
        try {
            VBox lvlFinished = FXMLLoader.load(new File("src/screens/lvlFinishedScreen.fxml").toURI().toURL());
            for (Node node: lvlFinished.getChildren())
                node.setVisible(true);
            currentScene.setRoot(lvlFinished);
        } catch (IOException ex) {
            System.out.println("LOG UIManager.setLvlFinishedScreen() : " + ex);
            setMainScreen();
        }
    }

    public void setGameAdjustmentsScreen() {
        try {
            VBox gameAdj = FXMLLoader.load(new File("src/screens/gameAdjustmentsScreen.fxml").toURI().toURL());
            currentScene.setRoot(gameAdj);
        } catch (IOException ex) {
            System.out.println("LOG UIManager.setGameAdjustmentsScene() : " + ex);
            setMainScreen();
        }
    }

    public void setSettingsScreen() {
        try {
            GridPane settings = FXMLLoader.load(new File("src/screens/settingsScreen.fxml").toURI().toURL());
            for (Node node: settings.getChildren())
                node.setVisible(true);
            currentScene.setRoot(settings);
        } catch (IOException ex) {
            System.out.println("LOG UIManager.setSettingsScene() : " + ex);
            setMainScreen();
        }
    }

    public void setWelcomeScreen() {
        try {
            AnchorPane welcome = FXMLLoader.load(new File("src/screens/welcomeScreen.fxml").toURI().toURL());
            for (Node node: welcome.getChildren())
                node.setVisible(true);
            Scene scene = new Scene(welcome);
            primaryStage.setScene(scene);
            currentScene=scene;
            instance.primaryStage.show();  // Placed here so there wont be ugly rescaling before loading intended screen
        } catch (IOException ex) {
            System.out.println("LOG UIManager.setWelcomeScene() : " + ex);
            setMainScreen();
        }
    }

    public ImageManager getImageManager() {
        return imageManager;
    }

    /** Add given ChangeListener to primaryStage.widthProperty*/
    public void setStageWidthChangeListener(ChangeListener<Number> listener) {
        primaryStage.widthProperty().addListener(listener);
    }

    /** Add given ChangeListener to primaryStage.heightProperty*/
    public void setStageHeightChangeListener(ChangeListener<Number> listener) {
        primaryStage.heightProperty().addListener(listener);
    }

    private UIManager() {
        if (instance != null) {
            throw new IllegalStateException("Singleton already constructed");
        }
    }
}

