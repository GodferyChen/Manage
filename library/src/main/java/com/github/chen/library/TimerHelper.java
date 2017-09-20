package com.github.chen.library;

import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;

public class TimerHelper implements Serializable {

    /**
     * 用于检测连接是否超时的定时器
     */
    public Timer timeOutTimer = null;
    public long timeOut = 1000;

    /**
     * 执行的次数
     */
    public int executions = 0;
    public int executionsMax = 10;

    public TimerHelper(long timeOut, int executionsMax) {
        super();
        this.timeOut = timeOut;
        this.executionsMax = executionsMax;
    }

    /**
     * 计时器重置
     */
    public void timeOutCancel() {
        if (timeOutTimer != null) {
            timeOutTimer.cancel();
            timeOutTimer = null;
        }
    }

    /**
     * 状态重置
     */
    public void reset() {
        if (timeOutTimer != null) {
            timeOutTimer.cancel();
            timeOutTimer = null;
        }
        executions = 0;
    }

    /**
     * 判断次数是否越界
     *
     * @return
     */
    public boolean isOutOfRange() {
        return executions >= executionsMax;
    }

    /**
     * 开始计时
     */
    public void timingBegins(TimerTask timerTask) {
        timeOutCancel();
        timeOutTimer = new Timer();
        timeOutTimer.schedule(timerTask, timeOut);
    }

    /**
     * 步进，当超时的时候调用，顺便可以告诉你越界了没有
     *
     * @return
     */
    public boolean stepping() {
        timeOutCancel();
        ++executions;
        return isOutOfRange();
    }

}
