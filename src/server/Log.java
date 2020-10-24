package server;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static java.lang.Thread.currentThread;

/** Class responsible for simple logging system */
public class Log {
    public static Logger logger = Logger.getLogger(Log.class.getName());

    public static void initialize() {
        FileHandler fh;
        try {
            fh = new FileHandler("server.log");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void log(String log) {
        logger.info(String.format("TID %4s| %s", currentThread().getId(), log));
    }
}
