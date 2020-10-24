package server;

import config.AppState;
import config.Score;

/** Responsible for handling POST requests */
public class PostHandler {

    public static void handlePost(RequestObj requestObj, ResponseObj responseObj) {
        switch (requestObj.category) {
            case "gamemode": {
                gameModePost(requestObj, responseObj);
                break;
            }
            case "score": {
                scorePost(requestObj, responseObj);
                break;
            }
            default: {
                responseObj.setValue("get/wrong argument");
                break;
            }
        }
    }

    private static void gameModePost(RequestObj requestObj, ResponseObj responseObj) {
        responseObj.setValue("gameModeGet");
    }

    private static void scorePost(RequestObj requestObj, ResponseObj responseObj) {
        try {
            AppState.getInstance().addScore(new Score(requestObj.parameter));
            responseObj.setValue("200/0K");
            System.out.println(AppState.getInstance().getBestScores().size());
        } catch (Exception ex) {
            responseObj.setValue("400/Cannot_make_score");
        }
    }
}
