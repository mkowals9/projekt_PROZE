package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static server.GetHandler.handleGet;
import static server.Log.log;
import static server.PostHandler.handlePost;

/** Responsible for handling connecting clients */
class ClientHandler extends Thread {
    private final Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private RequestSplitter splitter;
    public ResponseObj responseObj;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
        try
        {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            splitter = new RequestSplitter();

        } catch (IOException ex) {
            // no handling should be necessary
        }
    }

    public void run() {
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            takeRequest();
            in.close();
            out.close();
            clientSocket.close();

        } catch (IOException e) {
        }
    }

    /** Responsible for receiving requests from client */
    private void takeRequest() {
        String inputLine = "";
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            while ((inputLine = in.readLine()) != null) {
                newResponse(inputLine);
                handleRequest(inputLine);
                respond();
            }
        } catch (IOException e) {
            log("Connection reset!");
        }
    }

    /** Responsible for sending information to client */
    private void respond() {
        log(String.format("RESPONSE: %40s  | REQ:  %40s", responseObj.getValue(), responseObj.getRequest()));
        out.println(responseObj.getValue());
    }

    private void handleRequest(String request) {
        RequestObj requestObj = splitter.validateRequest(request);
        if (requestObj == null)
            return;

        if (requestObj.method.equalsIgnoreCase("get")) handleGet(requestObj, responseObj);
        else if (requestObj.method.equalsIgnoreCase("post")) handlePost(requestObj, responseObj);
        else responseObj.setValue("Error: category not in [get, post]");
    }

    /** Prepare new server.ResponseObj */
    private void newResponse(String request){
        responseObj = new ResponseObj(request);
        splitter.setResponseObj(responseObj);
    }
}
