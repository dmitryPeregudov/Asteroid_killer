package com108104271860286811966hlru.google.httpsplus.asteroid_killer;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

public class MySurface extends SurfaceView implements SurfaceHolder.Callback {
    GameThread gameThread;
    static String TAG="MyLog";
    Canvas canvas;
    Context context;

    public MySurface(Context context) {
        super(context);
        this.context=context;
        getHolder().addCallback(this);


    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        gameThread=new GameThread(getHolder(),getResources(),context);
        gameThread.start();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    boolean retry=true;
    while (retry){
        try {gameThread.join();
            retry = false;
        }catch (InterruptedException e){};
    }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        if (event.getAction()==MotionEvent.ACTION_DOWN){
            if (!gameThread.game){
                if (gameThread.thread.isAlive()){}
                else {
                gameThread=null;
            gameThread=new GameThread(getHolder(),getResources(),context);
                for (int i=0;i<=gameThread.asteroids.size();i++){
                    gameThread.asteroids.remove(i);
                }

            gameThread.start(); }}


           else{ Bullet bullet=new Bullet( (int) event.getX(),(int) event.getY(),getResources());
         gameThread.bullets.add(bullet);
         gameThread.playFire();
         Log.d(TAG,"touch "+event.getX()+" Y "+event.getY());}

        }return true;
    }


    void pause(){
        if (gameThread==null){
            gameThread=new GameThread(getHolder(),getResources(),context);
        }
        gameThread.game=false;

    }

    void destroy(){
        if (gameThread==null){

            gameThread=new GameThread(getHolder(),getResources(),context);
        }
        gameThread.game=false;
        if (gameThread.thread!=null){
        gameThread.thread.interrupt();}

    }
}
