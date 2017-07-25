package com.lymilestone.postcodemrd.modle.event;

/**
 * Created by CodeManLY on 2017/7/24 0024.
 */

public class DrawerEvent {

    private boolean isOpen;

    public DrawerEvent(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}
