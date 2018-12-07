package com.fly.imageloader.okhttp;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;


public class ProgressResponseBody extends ResponseBody {

    private String imageUrl;
    private ResponseBody mResponseBody;
    private OnProgressListener mOnProgressListener;
    private BufferedSource mBufferedSource;

    public ProgressResponseBody(String url, ResponseBody responseBody, OnProgressListener progressListener) {
        this.imageUrl = url;
        this.mResponseBody = responseBody;
        this.mOnProgressListener = progressListener;
    }

    @Override
    public MediaType contentType() {
        return mResponseBody.contentType();
    }

    @Override
    public long contentLength() {
        return mResponseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (mBufferedSource == null) {
            mBufferedSource = Okio.buffer(source(mResponseBody.source()));
        }
        return mBufferedSource;
    }

    private Source source(Source source) {
        return new ForwardingSource(source) {
            long totalBytesRead = 0;

            @Override
            public long read(@NonNull Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                totalBytesRead += (bytesRead == -1) ? 0 : bytesRead;

                if (mOnProgressListener != null) {
                    mOnProgressListener.onProgress(imageUrl, totalBytesRead, contentLength(), (bytesRead == -1),0, null);
                }
                return bytesRead;
            }
        };
    }
}
