package com108104271860286811966hlru.google.httpsplus.asteroid_killer;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;


public class Bullet {

    int X,Y,toX,toY,startsX,startsY;
 int DX,DY,i=0,within,heigh;
    Bitmap [] bitmap=new Bitmap[7];
    Bullet( int x, int y, Resources resources){

        startsX= (int) GameThread.MAX_X/2;
        startsY=(int) GameThread.MAX_Y+10;
        X=startsX;
        Y=startsY;
        toX=x;
        toY=y;

        DX=(toX-X)/20;
        DY=(toY-Y)/20;
        Bitmap pictureOrigin=BitmapFactory.decodeResource(resources,R.drawable.first);
        bitmap[0]=Bitmap.createScaledBitmap(pictureOrigin,(int)GameThread.unitX,(int)GameThread.unitY,false);
        pictureOrigin=BitmapFactory.decodeResource(resources,R.drawable.second);
        bitmap[1]=Bitmap.createScaledBitmap(pictureOrigin,(int)GameThread.unitX,(int)GameThread.unitY,false);
        pictureOrigin=BitmapFactory.decodeResource(resources,R.drawable.third);
        bitmap[2]=Bitmap.createScaledBitmap(pictureOrigin,(int)GameThread.unitX,(int)GameThread.unitY,false);
        pictureOrigin=BitmapFactory.decodeResource(resources,R.drawable.fou);
        bitmap[3]=Bitmap.createScaledBitmap(pictureOrigin,(int)GameThread.unitX,(int)GameThread.unitY,false);
        pictureOrigin=BitmapFactory.decodeResource(resources,R.drawable.five);
        bitmap[4]=Bitmap.createScaledBitmap(pictureOrigin,(int)GameThread.unitX,(int)GameThread.unitY,false);
        pictureOrigin=BitmapFactory.decodeResource(resources,R.drawable.six);
        bitmap[5]=Bitmap.createScaledBitmap(pictureOrigin,(int)GameThread.unitX,(int)GameThread.unitY,false);
        pictureOrigin=BitmapFactory.decodeResource(resources,R.drawable.seven);
        bitmap[6]=Bitmap.createScaledBitmap(pictureOrigin,(int)GameThread.unitX,(int)GameThread.unitY,false);
        within=bitmap[0].getWidth();
        heigh=bitmap[0].getHeight();
    }


    public void draw(Canvas canvas){
        if (i>6){i=0;}
        canvas.drawBitmap(bitmap[i],X-(bitmap[i].getWidth()/2),Y-(bitmap[i].getHeight()/2),null);
        X+=DX;
        Y+=DY;
        i++;
    }
}
