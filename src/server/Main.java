package server;

import config.ConfigParser;

import static server.Log.log;

public class Main {

    public static void main(String[] args) {
        Log.initialize();
        log("Sever started!");

        ConfigParser.setConfigPath("serverResources/config.xml");
        ConfigParser.getInstance();

        BomberServer server = new BomberServer();
        server.start(5555);
    }
}


