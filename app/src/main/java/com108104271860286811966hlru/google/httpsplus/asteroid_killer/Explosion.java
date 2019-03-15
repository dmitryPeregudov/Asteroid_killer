package com108104271860286811966hlru.google.httpsplus.asteroid_killer;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

public class Explosion {
    int cordX=0,cordY=0,cordXX=0,cordYY=0;
    Bitmap explosion[]=new Bitmap[25];
    int i=0;
    boolean check=true;
    Explosion(Resources resources,int[] cord,int delitelX,int delitelY){
        Bitmap bitmap=BitmapFactory.decodeResource(resources,R.drawable.exp);
        Bitmap bitmap1[]=new Bitmap[25];
        int X=0;
        int Y=0;
        int with=( bitmap.getWidth()/5);
        int heig=(bitmap.getHeight()/5);
        int count=0;
        for (int i=0;i<25;i++) {
            if (count == 5) {
                count = 0;
                Y += heig;
                X = 0;
            }
            bitmap1[i] = Bitmap.createBitmap(bitmap, X, Y, with, heig);
            explosion[i] = Bitmap.createScaledBitmap(bitmap1[i], (int) GameThread.unitX * delitelX, (int) GameThread.unitY * delitelY, true);
            X = X + with;
            ++count;
        }
        cordX=cord[0];
        cordY=cord[1];
        cordXX=cord[2];
        cordYY=cord[3];
        }
        void draw(Canvas canvas){
            if (i==25){check=false;i=0;}
            if (check) {
                canvas.drawBitmap(explosion[i], cordX - cordXX, cordY - cordYY, null);
                i++;
            }
        }

}
