package com.example.maxim.musify.bindings;

import android.content.Context;
import android.databinding.BaseObservable;

import com.example.maxim.musify.models.MainModel;

/**
 * Created by Maxim on 04.01.2017.
 */

public class BindingManager extends BaseObservable {

    private MainModel mainModel;

    public BindingManager(Context context) {
        mainModel =  new MainModel(context);
    }

    public MainModel getMainModel() {
        return mainModel;
    }
}
