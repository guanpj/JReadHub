package com.jeez.guanpj.jreadhub.event;

public class FabClickEvent {
    private int currentItemIndex;

    public FabClickEvent(int currentItemIndex) {
        this.currentItemIndex = currentItemIndex;
    }

    public int getCurrentItemIndex() {
        return currentItemIndex;
    }

    public void setCurrentItemIndex(int currentItemIndex) {
        this.currentItemIndex = currentItemIndex;
    }
}
