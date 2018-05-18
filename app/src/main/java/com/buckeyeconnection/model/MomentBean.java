package com.buckeyeconnection.model;

/**
 * Created by dingyinglu on 10/29/16.
 */
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class MomentBean {
    @SerializedName("content")
    private String content;

    @SerializedName("username")
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
