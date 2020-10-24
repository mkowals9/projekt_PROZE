package server;

import java.util.ArrayList;
import java.util.Arrays;

import static server.Log.log;

/** Responsible for splitting requests into parts, and first basic validation of request */
public class RequestSplitter {
    private ResponseObj responseObj;

    public RequestObj validateRequest(String request) {

        log("validate   " + request);
        ArrayList<String> lista = new ArrayList<>(Arrays.asList(request.split("/")));

        if (lista.size() != 3) {
            responseObj.setValue("ERROR, validateRequest() size != 3");
            return null;
        }

        log("validate split   " + lista);
        return new RequestObj(lista.get(0), lista.get(1), lista.get(2));
    }

    public void setResponseObj(ResponseObj responseObj) {
        this.responseObj = responseObj;
    }
}
