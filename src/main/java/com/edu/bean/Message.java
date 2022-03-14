package com.edu.bean;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Message {
    private Integer messageId;

    private Integer userId;

    private String content;

    private Integer replyUserId;
    @JsonFormat(locale = "yyyy-MM-dd")
    private Date time;

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getReplyUserId() {
        return replyUserId;
    }

    public void setReplyUserId(Integer replyUserId) {
        this.replyUserId = replyUserId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}