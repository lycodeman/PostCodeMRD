package com.lymilestone.postcodemrd.modle.event;

/**
 * Created by CodeManLY on 2017/7/25 0025.
 */

public class ToolBarAlphaEvent {
    private int alpha;

    public ToolBarAlphaEvent(int alpha) {
        this.alpha = alpha;
    }

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }
}
