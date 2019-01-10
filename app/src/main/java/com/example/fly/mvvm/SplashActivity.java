package com.example.fly.mvvm;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.fly.mvvm.dialog.CustomDialog;
import com.example.fly.mvvm.dialog.ProgressDialog;
import com.example.fly.mvvm.utils.SystemUtil;
import com.example.fly.mvvm_library.base.BaseActivity;

public class SplashActivity extends BaseActivity {

    private CustomDialog.Builder builder;
    private CustomDialog mDialog;
    private ProgressDialog mProgressDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        builder = new CustomDialog.Builder(this);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.show();

//        showSingleButtonDialog("这是单选对话框的内容！", null, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mDialog.dismiss();
//                //这里写自定义处理XXX
//            }
//        });

//        showTwoButtonDialog("这是双选对话框的内容！", null, null, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mDialog.dismiss();
//                //这里写自定义处理XXX
//            }
//        }, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mDialog.dismiss();
//                //这里写自定义处理XXX
//            }
//        });
    }

    public void onClick(View view){

    }


    private void showSingleButtonDialog(String alertText, String btnText, View.OnClickListener onClickListener) {
        mDialog = builder.setMessage(alertText)
                .setSingleButton(btnText, onClickListener)
                .createSingleButtonDialog();
        mDialog.show();
    }

    private void showTwoButtonDialog(String alertText, String confirmText, String cancelText, View.OnClickListener conFirmListener, View.OnClickListener cancelListener) {
        mDialog = builder.setMessage(alertText)
                .setPositiveButton(confirmText, conFirmListener)
                .setNegativeButton(cancelText, cancelListener)
                .createTwoButtonDialog();
        mDialog.show();
    }

    private void showSystemParameter() {
        String TAG = "系统参数：";
        Log.e(TAG, "手机厂商：" + SystemUtil.getDeviceBrand());
        Log.e(TAG, "手机型号：" + SystemUtil.getSystemModel());
        Log.e(TAG, "手机当前系统语言：" + SystemUtil.getSystemLanguage());
        Log.e(TAG, "Android系统版本号：" + SystemUtil.getSystemVersion());
        Log.e(TAG, "手机IMEI：" + SystemUtil.getIMEI(getApplicationContext()));
    }
}