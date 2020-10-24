package server;

/** Class representing server's response to client */
public class ResponseObj {
    private String value;
    private String request;

    public ResponseObj(String request) {
        this.request = request;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRequest() {
        return request;
    }
}
