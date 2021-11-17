package com.example.model.myexception;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Ataev Ismayll
 * on class create logger for write error
 * логер должен создоватся в проекте 1 раз
 */

public class MyException extends Exception {

    private static final Logger logger = LogManager.getLogger(MyException.class);

    public MyException() {
        super();
    }

    public MyException(String msg) {
        super();
        logger.log(Level.ERROR, msg);
    }

    public MyException(Exception exception) {
        super();
        logger.log(Level.ERROR, exception.getMessage());
    }

    public MyException(String msg, Exception exception) {
        super();
        logger.log(Level.ERROR, msg + exception.getMessage());
    }
}