package com.edu.bean;

public class FenLei {
    private int fenId;
    private String content;

    public int getFenId() {
        return fenId;
    }

    public void setFenId(int fenId) {
        this.fenId = fenId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "FenLei{" + "fenId=" + fenId + ", content='" + content + '\'' + '}';
    }
}
