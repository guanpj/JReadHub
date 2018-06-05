package com.jeez.guanpj.jreadhub.event;

public class SetDrawerStatusEvent {
    private int status;

    public SetDrawerStatusEvent(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
