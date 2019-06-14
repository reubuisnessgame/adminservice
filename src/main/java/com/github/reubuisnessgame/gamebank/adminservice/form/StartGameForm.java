package com.github.reubuisnessgame.gamebank.adminservice.form;

public class StartGameForm {
    private boolean isStated;

    private long time;

    public StartGameForm(boolean isStated, long time) {
        this.isStated = isStated;
        this.time = time;
    }

    public boolean isStated() {
        return isStated;
    }

    public void setStated(boolean stated) {
        isStated = stated;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
