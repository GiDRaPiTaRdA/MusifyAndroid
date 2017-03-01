package com.example.maxim.musify.helpers;

import android.os.AsyncTask;
import android.util.Log;

import java.util.concurrent.TimeUnit;

/**
 * Created by ${Maxim} on ${07.01.2017}.
 */

public class AsyncTimer {

    public interface TimerTickListener {
        void OnTimerTickUpdate();
    }

    private final static String ASYNC_TIMER_LOG = "ASYNC_TIMER_LOG";

    private int UPPER_FREQUENCY_BOUND = 600000;
    private int LOWER_FREQUENCY_BOUND = 1;
    private int frequency= 1000;

    private TimerTickListener listener;
    private MyTimer timer;

    boolean isRunning = false;

    public AsyncTimer(){
        Initialize(1000);
    }
    public AsyncTimer(int frequency){
        Initialize(frequency);
    }

    private void Initialize(int frequency){
        setFrequency(frequency);
    }

    public void Start() {
        if (!isRunning) {
            timer = new MyTimer();
            timer.execute();
            isRunning = true;
        }
    }

    public void Pause() {
        if (timer != null && !timer.isCancelled()) {
            timer.cancel(true);
            isRunning = false;
        }

    }

    public void setTimerTickListener(TimerTickListener listener) {
        this.listener = listener;
    }

    public int getFrequency() {
        return frequency;
    }
    public void setFrequency(int frequency) {
        if(frequency<=UPPER_FREQUENCY_BOUND&&
                frequency>=LOWER_FREQUENCY_BOUND)
            this.frequency = frequency;
        else
            Log.e(ASYNC_TIMER_LOG,"Impossible to set frequency with current value: "+frequency+"\nFrequency has to belond the region 1-");
    }

    private class MyTimer extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... params) {
            while (!isCancelled())
                try {
                    TimeUnit.MILLISECONDS.sleep(frequency);
                    publishProgress();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            if(listener!=null)
                listener.OnTimerTickUpdate();
        }
    }

}
