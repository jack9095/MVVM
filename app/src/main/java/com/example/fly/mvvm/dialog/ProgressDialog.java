package com.example.fly.mvvm.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.fly.mvvm.R;

public class ProgressDialog {

    private Context context;
    private Dialog dialog;
    private Display display;
    private RelativeLayout mRoot;

    public ProgressDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        builder();
    }

    public ProgressDialog builder() {
        View view = LayoutInflater.from(context).inflate(
                R.layout.progress_layout, null);

        mRoot = (RelativeLayout) view.findViewById(R.id.lLayout_bg);

        dialog = new Dialog(context, R.style.Dialog);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);

        // 调整透明度的
        WindowManager.LayoutParams lp1=dialog.getWindow().getAttributes();
        lp1.dimAmount=0.4f;  // 调整透明度的
        dialog.getWindow().setAttributes(lp1);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);


        mRoot.setLayoutParams(new FrameLayout.LayoutParams((int) (display
                .getWidth() * 0.85), RelativeLayout.LayoutParams.WRAP_CONTENT));

        return this;
    }

    public void show() {
        dialog.show();
    }

}