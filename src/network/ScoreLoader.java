package network;

import config.AppState;
import config.Score;

import java.util.ArrayList;

/** Class responsible for loading scores from server */
public class ScoreLoader {
    private BomberClient bomberClient;

    public ScoreLoader(BomberClient bomberClient) {
        this.bomberClient = bomberClient;
    }

    /** load a list of scores from server and save them locally */
    public void load() {
        String response = bomberClient.sendRequest("get/score/list");
        ArrayList<Score> scores = new ArrayList<>();
        for (String id: response.split(",")) {
            String score = bomberClient.sendRequest(String.format("get/score/%s", id));
            scores.add(new Score(score));
        }
        AppState.getInstance().setBestScores(scores);
        AppState.getInstance().saveScores();
    }

    /** send new score to be stored on server */
    public void sendScore(Score score) {
        String resp = bomberClient.sendRequest(String.format("post/score/%s", score.toLine()));
    }

}
