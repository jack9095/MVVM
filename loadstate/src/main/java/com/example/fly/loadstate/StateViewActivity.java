package com.example.fly.loadstate;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.fly.loadstate.view.ErrorState;
import com.example.fly.loadstate.view.LoadingState;
import com.fly.stateview.core.LoadManager;
import com.fly.stateview.stateview.BaseStateControl;

/**
 */
public class StateViewActivity extends AppCompatActivity {
    LoadManager loadManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_view);
        loadManager = new LoadManager.Builder()
                .setViewParams(this)
                .setListener(new BaseStateControl.OnRefreshListener() {
                    @Override
                    public void onRefresh(View v) {
                        onStateRefresh();
                    }
                })
                .build();
        loadManager.showStateView(ErrorState.class);

    }

    private void onStateRefresh() {
        loadManager.showStateView(LoadingState.class);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadManager.showSuccess();
            }
        }, 3000);
    }
}
