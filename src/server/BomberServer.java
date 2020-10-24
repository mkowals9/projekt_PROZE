package server;

import java.io.IOException;
import java.net.ServerSocket;

/** Class responsible communication with multiple clients */
public class BomberServer {
    private ServerSocket serverSocket;

    /** responsible for creating new thread to comunicate witch each new client */
    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            while (true)
                new ClientHandler(serverSocket.accept()).start();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            stop();
        }
    }

    public void stop() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
