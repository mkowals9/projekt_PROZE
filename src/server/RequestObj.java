package server;

import static server.Log.log;

/** Class representing client's request */
public class RequestObj {
    public String method;
    public String category;
    public String parameter;

    public RequestObj(String method, String category, String parameter) {
        log(String.format("Method: %s  |  cat: %s  |   param: %s", method, category, parameter));
        this.method = method;
        this.category = category;
        this.parameter = parameter;
    }
}
