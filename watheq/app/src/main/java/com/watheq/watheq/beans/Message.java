package com.watheq.watheq.beans;

/*
 * Created by Mahmoud on 3/13/2017.
 */

public class Message {

    private long timestamp;
    private String body;
    private String from;
    private String to;
    private long negatedTimestamp;
    private long dayTimestamp;
    private String displayName;
    private String orderId;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public long getNegatedTimestamp() {
        return negatedTimestamp;
    }

    public void setNegatedTimestamp(long negatedTimestamp) {
        this.negatedTimestamp = negatedTimestamp;
    }

    public long getDayTimestamp() {
        return dayTimestamp;
    }

    public void setDayTimestamp(long dayTimestamp) {
        this.dayTimestamp = dayTimestamp;
    }

    public Message(long timestamp, String body, String from, String to
            , long negatedTimestamp, long dayTimestamp, String displayName, String orderId) {
        this.timestamp = timestamp;
        this.body = body;
        this.from = from;
        this.to = to;
        this.dayTimestamp = dayTimestamp;
        this.negatedTimestamp = negatedTimestamp;
        this.displayName = displayName;
        this.orderId = orderId;
    }

    public Message() {
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getTo() {
        return to;
    }

    public String getFrom() {
        return from;
    }

    public String getBody() {
        return body;
    }
}
