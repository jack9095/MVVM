package com.example.fly.mvvm.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.fly.mvvm.R;


public class ViewUtils {

    public static View CreateTagView(Context context, String tagContent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user_tag, null);
        TextView tvTag = view.findViewById(R.id.tv_tag);
        tvTag.setText(tagContent);
        return view;
    }

}
