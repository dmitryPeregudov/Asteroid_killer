package com108104271860286811966hlru.google.httpsplus.asteroid_killer;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

import java.util.Random;

public class Asteroid {
    int X,Y=-100,startX,startY,speedX,speedY;
    final int ID=R.drawable.asteroid;
    Bitmap picture;
    Random random=new Random();

    static String TAG="MyLog";
    Asteroid(Resources resources){

        startX=(int)(random.nextInt((int)GameThread.MAX_X));
        speedX=20+random.nextInt((int) GameThread.MAX_X/50);
        speedY=20+random.nextInt((int) GameThread.MAX_Y/50);
        X=startX;
      Bitmap pictureOrigin=BitmapFactory.decodeResource(resources,R.drawable.asteroid);
        picture=Bitmap.createScaledBitmap(pictureOrigin,(int)GameThread.unitX,(int)GameThread.unitY,false);

    }
    public void draw(Canvas canvas){


        Y+=speedY;
        canvas.drawBitmap(picture,X,Y,null);
    }

}
