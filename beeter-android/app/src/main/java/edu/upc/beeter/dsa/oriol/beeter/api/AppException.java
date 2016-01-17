package edu.upc.beeter.dsa.oriol.beeter.api;

/**
 * Created by Oriol on 07/05/2015.
 */
public class AppException extends Exception {
    public AppException() {
        super();
    }

    public AppException(String detailMessage) {
        super(detailMessage);
    }
}