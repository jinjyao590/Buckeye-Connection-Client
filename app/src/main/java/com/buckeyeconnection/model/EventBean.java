package com.buckeyeconnection.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dingyinglu on 11/14/16.
 */
public class EventBean {

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    @SerializedName("event")
    private String eventName;

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    private String place;

    public String getEventContent() {
        return eventContent;
    }

    public void setEventContent(String eventContent) {
        this.eventContent = eventContent;
    }

    @SerializedName("econtent")
    private String eventContent;

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    @SerializedName("club")
    private String club;
}
