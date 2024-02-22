package com.spit.tph.Event;

public class CurrentAssetsCountEvent {
    private int assetsCount;

    public CurrentAssetsCountEvent(int assetsCount) {
        this.assetsCount = assetsCount;
    }

    public int getAssetsCount() {
        return assetsCount;
    }
}
