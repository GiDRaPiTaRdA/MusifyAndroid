package com.example.maxim.musify.helpers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.util.Log;

/**
 * Created by ${Maxim} on ${07.01.2017}.
 */

public class BroadcastReceiverHelper extends BroadcastReceiver {

    private final static String BROADCAST_RECEIVER_HELPER_TAG = "BROADCAST_RECEIVER_TAG";

    private BroadcastReceiverListener listener;
    Context context;

    public BroadcastReceiverHelper(Context context){
        this.context = context;
        IntentFilter filter =  new IntentFilter();
        filter.addAction(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
        this.context.registerReceiver(this,filter);
    }


    public interface BroadcastReceiverListener{
         void onReceive(Context context, Intent intent);
    }

    public void setBroadcastReceiverListener(BroadcastReceiverListener listener){
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(listener!=null)
            listener.onReceive(context,intent);
        else
            Log.d(BROADCAST_RECEIVER_HELPER_TAG,"No Listener found!!");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        context.unregisterReceiver(this);
    }
}
