package com.spit.tph.Event;

public class CustomTextWatcherEvent {
    public CustomTextWatcherEvent(String title) {
        this.title = title;
    }

    private String title;

    public String getTitle() {
        return title;
    }
}
