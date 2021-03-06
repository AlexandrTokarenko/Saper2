package com.example.saper.model.items;

import java.util.Timer;
import java.util.TimerTask;
import java.util.function.LongConsumer;

//класс отвечающий за время. не знаю, как он работает. нашел в интернете
public class GameTimer {

    private Timer timer;
    private long elapsedSeconds;
    private LongConsumer listener;

    public void start() {
        timer = new Timer();
        final TimerTask task = new TimerTask() {
            @Override
            public void run() {
                elapsedSeconds++;
                listener.accept(elapsedSeconds);
            }
        };

        elapsedSeconds = 0;
        listener.accept(elapsedSeconds);
        timer.scheduleAtFixedRate(task, 1000, 1000);
    }

    public void reset() {
        timer.cancel();
        timer = null;
        elapsedSeconds = 0;
    }

    public void setTimeListener(LongConsumer listener) {
        this.listener = listener;
    }
}
