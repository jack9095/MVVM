package com.example.fly.loadstate;

import android.app.Application;

import com.example.fly.loadstate.view.ErrorState;
import com.example.fly.loadstate.view.LoadingState;
import com.fly.stateview.core.LoadState;

/**
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        new LoadState.Builder()
                .register(new ErrorState())
                .register(new LoadingState())
                .setDefaultStateView(LoadingState.class)
                .build();
    }
}
