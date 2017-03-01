package com.example.maxim.musify.helpers;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Maxim on 05.01.2017.
 */

public class Notifications {
    public static void ShowToast(Context context,String message){
        Toast toast =  Toast.makeText(context,message,Toast.LENGTH_LONG);
        toast.show();
    }
}
