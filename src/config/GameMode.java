package config;

/** Class responsible for game parameters dependent on difficulty level */
public class GameMode {

    private final int speed;
    private final int botSpeed;
    private int hp;
    private final int scoreDivider;
    private final String name;

    public GameMode(int speed, int botSpeed, int hp, int scoreDivider, String name) {
        this.speed = speed;
        this.botSpeed = botSpeed;
        this.hp = hp;
        this.scoreDivider = scoreDivider;
        this.name = name;
    }

    public void setHp(int hp) {
        this.hp = hp;
        AppState.getInstance().hPPropertyProperty().setValue(hp);
    }

    public int getBotSpeed() {
        return botSpeed;
    }

    public int getSpeed() {
        return speed;
    }

    public int getHp() {
        return hp;
    }

    public String getName() {
        return name;
    }

    public int getScoreDivider() {
        return scoreDivider;
    }

}
