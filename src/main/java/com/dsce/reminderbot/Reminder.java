package com.dsce.reminderbot;

import java.util.Date;

public class Reminder {
    private String message;
    private Date dateTime;

    public Reminder(String message, Date dateTime) {
        this.message = message;
        this.dateTime = dateTime;
    }

    public String getMessage() {
        return message;
    }

    public Date getDateTime() {
        return dateTime;
    }
}
