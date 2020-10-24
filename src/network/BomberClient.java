package network;

import config.AppState;
import config.ConfigParser;
import uimanager.UIManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/** Class responsible for communication with server */
public class BomberClient {
    BufferedReader in;
    PrintWriter out;
    Socket clientSocket;
    public MapLoader mapLoader;
    public ScoreLoader scoreLoader;
    public GameModeLoader gameModeLoader;

    public BomberClient() throws Exception {
        try {
            clientSocket = new Socket("127.0.0.1", 5555);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException exception) {
            throw new Exception("Server unreachable");
        }
        AppState.getInstance().setBomberClient(this);
        mapLoader = new MapLoader(this);
        scoreLoader = new ScoreLoader(this);
        gameModeLoader = new GameModeLoader(this);
    }

    /** Method responsible for loading scores, lvl-s and game-modes from server */
    public void synchronize() {
        scoreLoader.load();
        ConfigParser.getInstance().setAvailableLevels(mapLoader.loadMapLvls());
        ConfigParser.getInstance().setGameModes(gameModeLoader.load());
    }

    /** Method responsible for sending request to server and getting response */
    public String sendRequest(String request) {
        String response = "";
        try {
            out.println(request);
            response = in.readLine();
            System.out.println(String.format("REQUEST: %30s | RESPONSE %30s", request, response));
        } catch (IOException ex)  {
            // no handling should be necessary
        }
        if (response == null) {
            UIManager.getInstance().setConnectionBrokenScreen();
        }

        return response;
    }

    public void close() {
        try {
            clientSocket.close();
        } catch (IOException exception) {
            System.out.println(" BomberClient.close() error");
        }
    }

}
