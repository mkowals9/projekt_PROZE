package config;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.paint.Paint;

import java.io.*;
import java.util.ArrayList;

/** Class representing users score */
public class Score {
    private final String userName;
    private int value;

    public void setValue(int value) {
        this.value = value;
        AppState.getInstance().pointsPropertyProperty().setValue(value);
    }

    public Score(String userName, int value) {
        this.userName = userName;
        this.value = value;
    }

    public Score(String line) {
        this.value = Integer.parseInt(line.split(",")[0]);
        this.userName = line.split(",")[1];
    }

    public String toLine() {
        return String.format("%s,%s", value, userName);
    }

    public Button toButton() {
        Button button = new Button(String.format("%s - %s", userName, value));
        button.setAlignment(Pos.CENTER);
        button.setPadding(new Insets(10, 10, 10, 10));
        button.setTextFill(Paint.valueOf("blue"));
        return button;
    }

    /**
     * Returns list of BestScores loaded from file
     */
    public static ArrayList<Score> getScores() {
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(ConfigParser.getInstance().getScoresPath());
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            System.out.println("LOG ConfigParser.getScores() : " + ex);
        }
        assert stream != null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String strLine;
        ArrayList<String> lines = new ArrayList<>();
        try {
            while ((strLine = reader.readLine()) != null) {
                String lastWord = strLine.substring(strLine.lastIndexOf(" ") + 1);
                lines.add(lastWord);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("LOG ConfigParser.getScores() : " + ex);
        }
        try {
            reader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("LOG ConfigParser.getScores() : " + ex);
        }
        ArrayList<Score> scores = new ArrayList<>();
        for (String scoreLine : lines) {
            scores.add(new Score(scoreLine));
        }
        return scores;

    }

    /**
     * Saves sorted list of BestScores to correct file (path from config)
     */
    public static void saveScores() {
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(ConfigParser.getInstance().getScoresPath());
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            System.out.println("LOG ConfigParser.saveScores() : " + ex);
        }
        assert stream != null;
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stream));
        ArrayList<Score> scores = AppState.getInstance().getBestScores();
        scores.sort((score, t1) -> Integer.compare(t1.getValue(), score.value));
        try {
            for (Score score : scores) {
                writer.write(score.toLine());
                writer.newLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("LOG ConfigParser.saveScores() : " + ex);
        }
        try {
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("LOG ConfigParser.saveScores() : " + ex);
        }
    }

    public int getValue() {
        return value;
    }
}