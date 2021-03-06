package com.rain.app.server.redux.logger;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MyLogger {
    
	static private FileHandler fileTxt;
    static private SimpleFormatter formatterTxt;

    static private FileHandler fileHTML;
    static private HtmlFormatter formatterHTML;

    static public void setup() throws IOException {

            // get the global logger to configure it
            Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

            // suppress the logging output to the console
            Logger rootLogger = Logger.getLogger("");
            Handler[] handlers = rootLogger.getHandlers();
            if (handlers[0] instanceof ConsoleHandler) {
                    rootLogger.removeHandler(handlers[0]);
            }

            logger.setLevel(Level.ALL);
            fileTxt = new FileHandler("log/Logging.txt");
            fileHTML = new FileHandler("log/Logging.html");

            // create a TXT formatter
            formatterTxt = new SimpleFormatter();
            fileTxt.setFormatter(formatterTxt);
            logger.addHandler(fileTxt);

            // create an HTML formatter
            formatterHTML = new HtmlFormatter();
            fileHTML.setFormatter(formatterHTML);
            logger.addHandler(fileHTML);
    }
}
