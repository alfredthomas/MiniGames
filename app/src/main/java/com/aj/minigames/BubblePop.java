package com.aj.minigames;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import com.aj.minigames.Views.ContentView;
import com.aj.minigames.Views.ImprovedViewGroup;

/**
 * Created by AJ on 8/29/2017.
 */

public class BubblePop extends MiniGameBaseActivity {

    private Bubble bubble;
    int successClicks = 0;
    int winningClicks = 10;
    int width;
    int height;
    @Override
    public void setup() {
        failTime = 500000;
        width = getResources().getDisplayMetrics().widthPixels;
        height = getResources().getDisplayMetrics().heightPixels;
        bubble = new Bubble(width,height);
        contentView = new ContentView(this)
        {
            @Override
            protected void onDraw(Canvas canvas)
            {
                contentView.setBackgroundColor(bubble.reversePaint);
                canvas.drawCircle(bubble.x,bubble.y,Bubble.radius,bubble.paint);
            }
        };
        contentView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN && Helper.isPointInCircle((int)motionEvent.getX(),(int)motionEvent.getY(),bubble.x,bubble.y,Bubble.radius)){
                    successClicks++;
                    bubble.genCoords();
                    contentView.invalidate();
                    return true;
                }

                return false;

            }
        });
//        contentView.invalidate();
//        contentView.measure(View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY));
        contentView.setBackgroundColor(Color.YELLOW);
    }

    @Override
    public boolean checkSuccess() {
        if(successClicks>=winningClicks)
            return true;
        return false;
    }

    @Override
    public boolean checkFailure() {
        return false;
    }

    @Override
    public void tick() {
    }
}
class Bubble
{
    static final int radius = 100;
    int x;
    int y;
    int width;
    int height;
    Paint paint;
    int reversePaint;
    public Bubble(int width, int height)
    {
        paint = new Paint();
        this.width = width;
        this.height = height;
        genCoords();
    }
    public void genCoords()
    {
        int padding = 5;
        x = radius + padding + (int)(Math.random()*(width-radius-radius-radius));
        y = radius + padding + (int)(Math.random()*(height-radius-radius-radius));
        int r=(int)(Math.random()*255);
        int g=(int)(Math.random()*255);
        int b=(int)(Math.random()*255);
        paint.setColor(Color.rgb(r,g,b));
        reversePaint = Color.rgb(255-r,255-g,255-b);
    }
}
