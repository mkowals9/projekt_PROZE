package screens;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import uimanager.UIManager;

/** Class responsible for HelpScreen logic */
public class HelpScreenController{
    public Button ReturnBtn;
    public ImageView keyboard;

    public void ReturnBtnOnClick() {
        UIManager.getInstance().setMainScreen();}
    }

