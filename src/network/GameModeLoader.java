package network;

import config.GameMode;

import java.util.ArrayList;

/** Class responsible for loading game-modes from server */
public class GameModeLoader{

    private BomberClient bomberClient;

    public GameModeLoader(BomberClient bomberClient){
        this.bomberClient=bomberClient;
    }

    public ArrayList<GameMode> load(){
        String response = bomberClient.sendRequest("get/gamemode/all");
        ArrayList<GameMode> gameModes = new ArrayList<>();
        if(!response.startsWith("200"))
        {
            //TO DO handle with bad response
        }
        else{
            response = response.replace("200/", "");
            for (String id: response.split(",")) {
                String parameters = bomberClient.sendRequest(String.format("get/gamemode/%s", id));
                parameters=parameters.replace("200/","");
                String [] parametersArray=parameters.split(",");
                gameModes.add(new GameMode(Integer.parseInt(parametersArray[1]),Integer.parseInt(parametersArray[3]),
                        Integer.parseInt(parametersArray[2]),Integer.parseInt(parametersArray[4]),parametersArray[0]));
            }
        }
        return gameModes;
        }

}
