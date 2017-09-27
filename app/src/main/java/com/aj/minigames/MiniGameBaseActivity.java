package com.aj.minigames;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.aj.minigames.Views.ContentView;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by AJ on 8/29/2017.
 */

abstract class MiniGameBaseActivity extends Activity{

    public boolean success = false;
    public boolean fail = false;
    public int failTime = 10000;
    public ContentView contentView;
    public ViewGroup.LayoutParams matchParentParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    public void onSuccess()
    {
        setContentView(R.layout.win_screen);
    }
    public void onFail()
    {
        setContentView(R.layout.lose_screen);
    }
    public abstract boolean checkSuccess();
    public abstract boolean checkFailure();

    public abstract void setup();

    public MiniGameBaseActivity()
    {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        contentView = (LinearLayout)getLayoutInflater().inflate(R.layout.content_view,null);
//        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        setup();
        setContentView(contentView);

//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                tick();
//                if(checkSuccess())
//                {
//                    success = true;
//                    onSuccess();
//                }
//                if(checkFailure())
//                {
//                    fail = true;
//                    onFail();
//                }
//            }
//        };
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                boolean stop = false;
                Handler uiHandler = new Handler(Looper.getMainLooper());
                long startTime = System.currentTimeMillis();
                while (System.currentTimeMillis() - startTime < failTime && !stop) {
                    if (checkSuccess()) {
                        uiHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                success = true;
                                onSuccess();
                            }
                        });
                        stop = true;
                    }
                    if (checkFailure()) {
                        uiHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                fail = true;
                                onFail();
                            }
                        });
                        stop = true;
                    }
                    uiHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            tick();
                        }
                    });

                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(!success)
                {
                    uiHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            onFail();
                        }
                    });
                }
            }
        };
        Thread gameThread = new Thread(runnable);
        gameThread.start();
    }

    public abstract void tick();

}
