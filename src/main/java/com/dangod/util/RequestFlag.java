package com.dangod.util;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Yxm on 2017/12/20.
 */
public class RequestFlag{
    private final Timer timer = new Timer();
    private boolean flag = true;

    public void start(int seconds) {
        timer.schedule(new TimerTask() {
            public void run() {
                flag = true;
//                timer.cancel();
            }
        }, seconds* 1000);
    }

    public boolean getFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
