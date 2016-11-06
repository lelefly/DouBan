package com.buaa.douban.util;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.util.LruCache;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.buaa.douban.MyApplication;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

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

    private Type mType = Type.LIFO;

    private Thread mPoolThread;

    private Handler mPoolThreadHandler;

    private Handler mUIHandler;

    private Semaphore mSemaphore = new Semaphore(0);

    public enum Type
    {
        FIFO , LIFO
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
                        mThreadPool.execute(getTask());

                    }
                };
                mSemaphore.release();
                Looper.loop();
            }
        };

        mPoolThread.start();

        int maxMemory = ((ActivityManager)MyApplication.getContext().getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
        int cacheMemory = maxMemory/8;

        mLruCache = new LruCache<String,Bitmap>(cacheMemory){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes()*value.getHeight();
            }
        };

        mThreadPool = Executors.newFixedThreadPool(threadCount);
        mTaskQueue = new LinkedList<Runnable>();
        mType = type;
    }

    private Runnable getTask() {
        if(mType == Type.FIFO){
            return mTaskQueue.removeFirst();
        }else{
            return mTaskQueue.removeLast();
        }
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

    public void loadImage(final String path, final ImageView imageView){
        imageView.setTag(path);

        if(mUIHandler == null){
            mUIHandler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    ImageBeanHolder holder = (ImageBeanHolder)msg.obj;
                    Bitmap bm = holder.bitmap;
                    ImageView imageView = holder.imageView;
                    String path = holder.path;
                    if(imageView.getTag().toString().equals(path)){
                        imageView.setImageBitmap(bm);
                    }
                }
            };
        }
        Bitmap bm = getBitmapFromLruCache(path);
        if(bm !=null){
            refreshBitmap(path, imageView, bm);
        }else{
            addTask(new Runnable(){
                @Override
                public void run() {
                   ImageSize imageSize = getImageViewSize(imageView);
                    Bitmap bm = decodeBitmap(path,imageSize.width,imageSize.height);
                    addBitmapToCache(path,bm);
                    refreshBitmap(path,imageView,bm);
                }
            });
        }
    }

    private void refreshBitmap(String path, ImageView imageView, Bitmap bm) {
        Message message = Message.obtain();
        ImageBeanHolder holder= new ImageBeanHolder();
        holder.bitmap = bm;
        holder.path = path;
        holder.imageView = imageView;
        message.obj = holder;
        mUIHandler.sendMessage(message);
    }

    private void addBitmapToCache(String path, Bitmap bm) {
        if(getBitmapFromLruCache(path) == null){
            if(bm!=null){
                mLruCache.put(path,bm);
            }
        }
    }

    private Bitmap decodeBitmap(String path,int width, int height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path,options);

        options.inSampleSize = caculateInSampleSize(options,width,height);

        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(path,options);

        return bitmap;
    }

    private int caculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int width = options.outWidth;
        int height = options.outHeight;

        int inSampleSize = 1;

        if(width>reqWidth || height>reqHeight){
            int widthRadio = Math.round(width*1.0f/reqWidth);
            int heightRadio = Math.round(height*1.0f/reqHeight);

            inSampleSize = Math.max(widthRadio,heightRadio);

        }

        return inSampleSize;

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private ImageSize getImageViewSize(ImageView imageView) {
        ImageSize imageSize = new ImageSize();

        DisplayMetrics displayMetrics = imageView.getContext().getResources().getDisplayMetrics();

        ViewGroup.LayoutParams lp = imageView.getLayoutParams();

        int width = imageView.getWidth();
        if(width<= 0){
            width = lp.width;
        }

        if(width <= 0){
            width = imageView.getMaxWidth();
        }

        if(width<=0){
            width = displayMetrics.widthPixels;
        }

        int height = imageView.getWidth();
        if(height<= 0){
            height = lp.height;
        }

        if(height <= 0){
            height = imageView.getMaxHeight();
        }

        if(height<=0){
            height = displayMetrics.heightPixels;
        }

        imageSize.width = width;
        imageSize.height = height;

        return imageSize;
    }

    private synchronized void addTask(Runnable runnable) {
        mTaskQueue.add(runnable);
        try {
            if(mPoolThreadHandler == null)
            mSemaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mPoolThreadHandler.sendEmptyMessage(0x110);
    }

    private Bitmap getBitmapFromLruCache(String path) {
        return mLruCache.get(path);
    }

    private class ImageSize{
        int width;
        int height;
    }

    private class ImageBeanHolder{
        Bitmap bitmap;
        ImageView imageView;
        String path;
    }
}
