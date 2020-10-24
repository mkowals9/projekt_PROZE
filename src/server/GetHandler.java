package server;

import config.AppState;
import config.ConfigParser;
import config.Score;
import config.GameMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/** Responsible for handling GET requests */
public class GetHandler {

    public static void handleGet(RequestObj requestObj, ResponseObj responseObj) {
        switch (requestObj.category) {
            case "gamemode": {
                gameModeGet(requestObj, responseObj);
                break;
            }
            case "score": {
                scoreGet(requestObj, responseObj);
                break;
            }
            case "map": {
                mapGet(requestObj, responseObj);
                break;
            }
            default: {
                responseObj.setValue("get/wrong argument");
                break;
            }
        }
    }

    /** Responsible for handling GET requests concerning game-modes */
    private static void gameModeGet(RequestObj requestObj, ResponseObj responseObj) {
        responseObj.setValue("gameModeGet");
        StringBuilder resp = new StringBuilder();
        if(requestObj.parameter.equals("all")){
            for(String key: ConfigParser.getInstance().getGameModes().keySet()){
                resp.append(key).append(",");
            }
            resp.deleteCharAt(resp.length() - 1);
            responseObj.setValue("200/"+resp.toString());
        }
        else{
            if(ConfigParser.getInstance().getGameModes().containsKey(requestObj.parameter)){
                GameMode chosenGameMode = ConfigParser.getInstance().getGameModes().get(requestObj.parameter);
                resp.append(chosenGameMode.getName()).append(",");
                resp.append(String.valueOf(chosenGameMode.getSpeed())).append(",");
                resp.append(String.valueOf(chosenGameMode.getHp())).append(",");
                resp.append(String.valueOf(chosenGameMode.getBotSpeed())).append(",");
                resp.append(String.valueOf(chosenGameMode.getScoreDivider()));
                responseObj.setValue("200/"+resp.toString());
            }
            else{
                responseObj.setValue("400, get/wrong argument");
            }
        }
    }

    /** Responsible for handling GET requests concerning maps */
    private static void mapGet(RequestObj requestObj, ResponseObj responseObj) {
        ConfigParser configParser = ConfigParser.getInstance();
        Set<String> mapIds = configParser.getLvlToMapPath().keySet();
        if (requestObj.parameter.equalsIgnoreCase("list")) {
            String mapIdList = String.join(",", ConfigParser.getInstance().getLvlToMapPath().keySet());
            responseObj.setValue("200/" + mapIdList);
            return;
        } else if (mapIds.contains(requestObj.parameter)) {
            ArrayList<String> map = configParser.getMapTxt(Integer.parseInt(requestObj.parameter));
            responseObj.setValue("200/" + String.join("|", map));
            return;
        }
        responseObj.setValue("mapGet");
    }

    /** Responsible for handling GET requests concerning scores */
    private static void scoreGet(RequestObj requestObj, ResponseObj responseObj) {
        HashMap<String, String> scores = new HashMap<>();
        int scoreId = 1;
        for (Score score: AppState.getInstance().getBestScores())
            scores.put(String.valueOf(scoreId++), score.toLine());

        if (requestObj.parameter.equalsIgnoreCase("list")) {
            StringBuilder resp = new StringBuilder();
            for (String key: scores.keySet())
                resp.append(key).append(",");
            responseObj.setValue(resp.toString());
        }
        else responseObj.setValue(
                scores.getOrDefault(
                        requestObj.parameter, "scoreGet | WRONG parameter"
                )
        );
    }
}
