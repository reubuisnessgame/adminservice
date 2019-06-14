package com.github.reubuisnessgame.gamebank.adminservice.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExceptionModel {
    private String timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

    public ExceptionModel(int status, String error, String message, String path) {
        timestamp = getCurrentTimeStamp();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    private String getCurrentTimeStamp() {
        return new SimpleDateFormat("yyyy-MM-ddEHH:mm:ss.SSSZ").format(new Date());
    }
}
