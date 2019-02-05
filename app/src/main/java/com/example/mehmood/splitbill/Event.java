package com.example.mehmood.splitbill;

public class Event {
    private String mText1;
    private String mText2;

    public Event(String Text1, String Text2) {
        this.mText1 = Text1;
        this.mText2 = Text2;
    }

    public String getText1() {
        return mText1;
    }

    public String getText2() {
        return mText2;
    }
}

