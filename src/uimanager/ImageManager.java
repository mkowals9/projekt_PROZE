package uimanager;

import config.AppState;
import config.ConfigParser;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.HashMap;

/** Class responsible for loading images */
public class ImageManager {
    private HashMap<String, Image> heroes = new HashMap<>();
    private Image speedPowerUp;
    private Image botSlowPowerUp;
    private Image extraBombPowerUp;
    private Image bomb;
    private Image explosion;
    private Image verticalMonster;
    private Image horizontalMonster;
    private Image solidBlock;
    private Image weakBlock;
    private Image bonusPoints;
    private Image exitBlock;
    private Image extraBomb;

    public ImageManager() {
    loadImages();
    }

    private void loadImages() {
        try {
            speedPowerUp = new Image("file:resources/images/speedpowerup.gif");
            if (speedPowerUp.isError()) throw new Exception("error loading image");
        } catch (Exception ex) {
            System.out.println("LOG loadImages() cannot set speedpowerup image + : " + ex);
            speedPowerUp = null;
        }
        try {
            botSlowPowerUp = new Image("file:resources/images/slowdownmob.png");
            if (botSlowPowerUp.isError()) throw new Exception("error loading image");
        } catch (Exception ex) {
            System.out.println("LOG loadImages() cannot set slowdownmob image + : " + ex);
            botSlowPowerUp = null;
        }

        try {
            extraBombPowerUp = new Image("file:resources/images/powerup2.gif");
            if (extraBombPowerUp.isError()) throw new Exception("error loading image");
        } catch (Exception ex) {
            System.out.println("LOG loadImages() cannot set slowdownmob image + : " + ex);
            extraBombPowerUp = null;
        }

        try {
            bomb = new Image("file:resources/images/bomb.png");
            if (bomb.isError()) throw new Exception("error loading image");
        } catch (Exception ex) {
            System.out.println("LOG loadImages() cannot set bomb image + : " + ex);
            bomb = null;
        }

        try {
            explosion = new Image("file:resources/images/explosion.png");
            if (explosion.isError()) throw new Exception("error loading image");
        } catch (Exception ex) {
            System.out.println("LOG loadImages() cannot set explosion image + : " + ex);
            explosion = null;
        }

        try {
            verticalMonster = new Image("file:resources/images/monster1.gif");
            if (verticalMonster.isError()) throw new Exception("error loading image");
        } catch (Exception ex) {
            System.out.println("LOG loadImages() cannot set verticalMonster image + : " + ex);
            verticalMonster = null;
        }

        try {
            horizontalMonster = new Image("file:resources/images/monster2.gif");
            if (horizontalMonster.isError()) throw new Exception("error loading image");
        } catch (Exception ex) {
            System.out.println("LOG loadImages() cannot set horizontalMonster image + : " + ex);
            horizontalMonster = null;
        }

        try {
            weakBlock = new Image("file:resources/images/weakblock.jpg");
            if (weakBlock.isError()) throw new Exception("error loading image");
        } catch (Exception ex) {
            System.out.println("LOG loadImages() cannot set weakBlock image + : " + ex);
            weakBlock = null;
        }

        try {
            solidBlock = new Image("file:resources/images/solidblock.jpg");
            if (solidBlock.isError()) throw new Exception("error loading image");
        } catch (Exception ex) {
            System.out.println("LOG loadImages() cannot set solidBlock image + : " + ex);
            solidBlock = null;
        }

        try {
            bonusPoints = new Image("file:resources/images/powerup3.gif");
            if (bonusPoints.isError()) throw new Exception("error loading image");
        } catch (Exception ex) {
            System.out.println("LOG loadImages() cannot set bonusPoints image + : " + ex);
            bonusPoints = null;
        }

        try {
            exitBlock = new Image("file:resources/images/exitblock.png");
            if (exitBlock.isError()) throw new Exception("error loading image");
        } catch (Exception ex) {
            System.out.println("LOG loadImages() cannot set exitBlock image + : " + ex);
            exitBlock = null;
        }

        try {
            extraBomb = new Image("file:resources/images/powerup2.gif");
            if (extraBomb.isError()) throw new Exception("error loading image");
        } catch (Exception ex) {
            System.out.println("LOG loadImages() cannot set extraBomb image + : " + ex);
            extraBomb = null;
        }
        loadHeroIcons();
    }

    public ArrayList<String> getAvailableHeroes() {
        return new ArrayList<>(heroes.keySet());
    }

    public Image getSpeedPowerUp() {
        return speedPowerUp;
    }

    public Image getBotSlowPowerUp() {
        return botSlowPowerUp;
    }

    public Image getExtraBombPowerUp() { return extraBombPowerUp;}

    public Image getBomb() {
        return bomb;
    }

    public Image getExplosion() {
        return explosion;
    }

    public Image getVerticalMonster() {
        return verticalMonster;
    }

    public Image getHorizontalMonster() {
        return horizontalMonster;
    }

    public Image getWeakBlock() {
        return weakBlock;
    }

    public Image getSolidBlock() {
        return solidBlock;
    }

    public Image getHeroIcon() {
        return heroes.get(AppState.getInstance().getChosenHero());
    }

    public Image getBonusPoints() {
        return bonusPoints;
    }

    public Image getExitBlock() {
        return exitBlock;
    }

    public Image getExtraBomb() {
        return extraBomb;
    }

    private void loadHeroIcons() {
        HashMap<String, String> heroNameToPath = ConfigParser.getInstance().getHeroNameToPath();
        for (String name: heroNameToPath.keySet()) {
            try {
                Image heroImage = new Image(heroNameToPath.get(name));
                if (heroImage.isError()) throw new Exception("error loading image");
                else heroes.put(name, heroImage);
            } catch (Exception ex) {
                System.out.println("LOG loadImages() cannot set " + name + " image + : " + ex);
            }
        }
    }

}
