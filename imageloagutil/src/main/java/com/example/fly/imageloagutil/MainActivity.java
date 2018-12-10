package com.example.fly.imageloagutil;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.GlideException;
import com.fly.imageloader.invocation.ImageLoaderManager;
import com.fly.imageloader.okhttp.OnProgressListener;
import com.fly.imageloader.tranform.BlurBitmapTranformation;
import com.fly.imageloader.tranform.GlideCircleTransformation;
import com.fly.imageloader.tranform.RoundBitmapTranformation;

/**
 * https://muyangmin.github.io/glide-docs-cn/doc/getting-started.html
 */
public class MainActivity extends AppCompatActivity {
    private ImageView mImageView_1;
    private ImageView mImageView_2;
    private ImageView mImageView_3;
    private ImageView mImageView_4;
    private ImageView mImageView_5;
    private ImageView mImageView_6;
    private ImageView mImageView_7;
    private ImageView mImageView_8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        mImageView_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this,ProgressActivity.class));
            }
        });
        ImageLoaderManager.getInstance().setFileName("fly");
        ImageLoaderManager.getInstance().setSize(300);
        String url="http://img5.imgtn.bdimg.com/it/u=3532743473,184108530&fm=200&gp=0.jpg";
        ImageLoaderManager.getInstance().displayCircleImage(this, url, mImageView_1, R.mipmap.ic_launcher_round);
        ImageLoaderManager.getInstance().displayRoundImage(this, url, mImageView_2, R.mipmap.ic_launcher_round, 40);

        ImageLoaderManager.getInstance().displayImageNetUrlGif(this, "https://gss0.baidu.com/-vo3dSag_xI4khGko9WTAnF6hhy/zhidao/pic/item/80cb39dbb6fd5266ad7a5e30a318972bd4073644.gif", mImageView_3,null);

        ImageLoaderManager.getInstance().displayImageInResource(this, R.mipmap.ic_launcher, mImageView_4);
        //本地图片，模糊处理
        ImageLoaderManager.getInstance().displayImageInResource(this, R.mipmap.ic_launcher, mImageView_5, new BlurBitmapTranformation( 200));
        //本地图片，裁圆角处理
        ImageLoaderManager.getInstance().displayImageInResource(this, R.mipmap.ic_launcher, mImageView_6, new GlideCircleTransformation());
        //四周圆角处理
        ImageLoaderManager.getInstance().displayImageInResource(this, R.mipmap.ic_launcher, mImageView_7, new RoundBitmapTranformation( 40));


        ImageLoaderManager.getInstance().disPlayImageProgressByOnProgressListener(this, "http://img.zcool.cn/community/0142135541fe180000019ae9b8cf86.jpg@1280w_1l_2o_100sh.png", mImageView_8, R.mipmap.ic_launcher, R.mipmap.ic_launcher, new OnProgressListener() {

            @Override
            public void onProgress(String imageUrl, long bytesRead, long totalBytes, boolean isDone,GlideException exception) {
                Log.e("MainActivity","fly bytesRead="+bytesRead);
                Log.e("MainActivity","fly totalBytes="+totalBytes);
//                System.out.println("fly percent="+percent);
//                mProgress7.setText("我在主线程，进度是多少==+bytesRead"+bytesRead+"totalBytes="+totalBytes);

                if (isDone){
//                    mProgress7.setText("我在主线程，进度是多少==100%");
                }
            }
        });
    }

    private void findView() {
        mImageView_1 = findViewById(R.id.image_view_1);
        mImageView_2 = findViewById(R.id.image_view_2);
        mImageView_3 = findViewById(R.id.image_view_3);
        mImageView_4 = findViewById(R.id.image_view_4);
        mImageView_5 = findViewById(R.id.image_view_5);
        mImageView_6 = findViewById(R.id.image_view_6);
        mImageView_7 = findViewById(R.id.image_view_7);
        mImageView_8 = findViewById(R.id.image_view_8);
    }
}
