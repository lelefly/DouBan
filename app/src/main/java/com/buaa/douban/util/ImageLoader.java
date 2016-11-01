package com.buaa.douban.util;

import android.app.ActivityManager;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.LruCache;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;

import static android.R.attr.key;

/**
 * Created by liuxiaole on 2016/10/31.
 */

public class ImageLoader {
    private static ImageLoader mInstance;

    private LruCache<String,Bitmap> mLruCache;

    //线程池
    private ExecutorService mThreadPool;

    private static final int DEFAULT_THREAD_COUNT = 1;

    private LinkedList<Runnable> mTaskQueue;

    private Thread mPoolThread;

    private Handler mPoolThreadHandler;

    private Handler mUIHandler;

    public enum Type
    {
        FIFI  , LIFO
    }


    private ImageLoader(int threadCount,Type type){
        init(threadCount,type);
    }

    private void init(int threadCount, Type type) {
        mPoolThread = new Thread(){
            @Override
            public void run() {
                Looper.prepare();
                mPoolThreadHandler = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {

                    }
                };
                Looper.loop();
            }
        };

        mPoolThread.start();

        long maxMemory = ActivityManager.get
        long cacheMemory = maxMemory/8;
    }

    public static ImageLoader getInstance(){
        if(mInstance == null){
            synchronized (ImageLoader.class)
            {
                if(mInstance==null){
                    mInstance = new ImageLoader(DEFAULT_THREAD_COUNT,Type.LIFO);
                }
            }
        }
        return mInstance;
    }
}
