package com108104271860286811966hlru.google.httpsplus.asteroid_killer;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.SurfaceHolder;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class GameThread extends Thread implements MediaPlayer.OnCompletionListener {
    static float MAX_X, MAX_Y, unitX, unitY;
    SurfaceHolder surfaceHolder;
    Asteroid asteroid;
    Bitmap fon;
    boolean game = true;
    Thread thread;
    Bitmap station;
    int i = 0;
    int curentTime = 0;
    int interval = 30;
    Resources resources;
    Context context;
    int tempo = 0;
static String TAG="MyLog";
    MediaPlayer bang, fireSound;

    ArrayList<Explosion> explosions = new ArrayList<>();
    ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    ArrayList<Asteroid> asteroids = new ArrayList<Asteroid>();


    public GameThread(SurfaceHolder surfaceHolder, Resources resources, Context context) {
        //TODO  охранение данных, темп отрисовки пули и астероидов
        this.surfaceHolder = surfaceHolder;
        this.resources = resources;
        unitY = MAX_Y / 5;
        unitX = MAX_X / 7;
        asteroid = new Asteroid(resources);
        asteroids.add(asteroid);
        this.context = context;
        Bitmap bitmap = BitmapFactory.decodeResource(resources, R.drawable.fon);
        fon = Bitmap.createScaledBitmap(bitmap, (int) MAX_X, (int) MAX_Y, false);
        bitmap = BitmapFactory.decodeResource(resources, R.drawable.station);
        station = Bitmap.createScaledBitmap(bitmap, (int) MAX_X / 2, (int) MAX_Y / 2, false);

    }

    @Override
    public void run() {
        Canvas canvas;
        while (game) {

            addAsteroid();
            delAsteroid();
            delBullet();
            checkExplosion();
            addTempo();
            collision();
            canvas = surfaceHolder.lockCanvas();
            dethCheck();
            canvas.drawBitmap(fon, 0, 0, null);
            canvas.drawBitmap(station, 0 + MAX_X / 4, MAX_Y / 6 * 5, null);

            for (Explosion exp: explosions){
               exp.draw(canvas);
            }
            for (Bullet bull:bullets){
                bull.draw(canvas);
            }
            for (Asteroid ast : asteroids) {
                ast.draw(canvas);
            }
            //TODO Вынести в отдельный метод
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
            }


            surfaceHolder.unlockCanvasAndPost(canvas);
        }


    }


    public void addAsteroid() {
        if (curentTime >= interval) {
            asteroid = new Asteroid(resources);
            asteroids.add(asteroid);
            curentTime = 0;
        } else curentTime++;
    }

    public void delAsteroid() {
        for (int i = 0; i <= asteroids.size() - 1; i++) {
            if (asteroids.get(i).Y > MAX_Y + 100) {
                asteroids.remove(i);
            }
        }
    }

    public void delBullet() {
        for (int i = 0; i <= bullets.size() - 1; i++) {
            if (bullets.get(i).Y < -100) {
                bullets.remove(i);
            }
        }
    }

    void collision() {

        try {


            for (int i = 0; i <= asteroids.size() - 1; i++) {
                for (int j = 0; j <= bullets.size() - 1; j++) {
                    if (
                            ((asteroids.get(i).X <= bullets.get(j).X) & (bullets.get(j).X <= asteroids.get(i).X + asteroids.get(i).picture.getWidth())
                                    || ((bullets.get(j).X + bullets.get(j).within <= asteroids.get(i).X + asteroids.get(i).picture.getWidth()) && (asteroids.get(i).X + asteroids.get(i).picture.getWidth() <= bullets.get(j).X)
                            ))
                                    && ((asteroids.get(i).Y <= bullets.get(j).Y) & (bullets.get(j).Y <= asteroids.get(i).Y + asteroids.get(i).picture.getHeight())
                                    || (bullets.get(j).Y + bullets.get(j).heigh <= asteroids.get(i).Y + asteroids.get(i).picture.getHeight()) && (asteroids.get(i).Y + asteroids.get(i).picture.getHeight() <= bullets.get(j).Y
                            ))
                            )


                    {
                        int cord[] = new int[4];

                        cord[0] = asteroids.get(i).X;
                        cord[1] = asteroids.get(i).Y;
                        cord[2] = asteroids.get(i).picture.getWidth() / 2;
                        cord[3] = asteroids.get(i).picture.getHeight() / 2;
                        Explosion explosion = new Explosion(resources, cord,2,2);
                        explosions.add(explosion);
                        playBang();
                        asteroids.remove(i);
                        bullets.remove(j);
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    void checkExplosion() {
        for (int i = 0; i <= explosions.size() - 1; i++) {
            if (!explosions.get(i).check) {
                explosions.remove(i);

            }
        }

    }

    void addTempo() {
        if (interval == 10) {
        }
        if (tempo == 50 && interval > 10) {
            tempo = 0;
            interval--;
        } else tempo++;

    }

   void playBang(){
        bang=MediaPlayer.create(context,R.raw.explosion);
        bang.start();
        bang.setOnCompletionListener(this);
    }

    void playFire(){
        fireSound=MediaPlayer.create(context,R.raw.fire);
        fireSound.start();
        fireSound.setOnCompletionListener(this);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (bang!=null&&!bang.isPlaying()) {
            bang.release();bang=null;
        }
        if (fireSound!=null&&!fireSound.isPlaying()) {
            fireSound.release();fireSound=null;
        }
    }

 void dethCheck(){

        for (int i=0;i<asteroids.size();i++){
            if (asteroids.get(i).X+asteroids.get(i).picture.getWidth()/2>=0 + MAX_X / 4 && asteroids.get(i).Y+asteroids.get(i).picture.getHeight()/2>=MAX_Y / 6 * 5 &&
                     asteroids.get(i).X+asteroids.get(i).picture.getWidth()/2<= MAX_X-(MAX_X / 4)
                    ){game=false;playBang();



            thread=new Thread(new Runnable() {
                @Override
                 public void run() {
                    Paint paint=new Paint();
                    paint.setColor(Color.RED);
                    paint.setTextSize(MAX_X/12);

                    int cord[] = new int[4];

                    cord[0] =  0+(int)MAX_X/6;
                    cord[1] = 2*(int)( MAX_Y / 4);
                    cord[2] =0;
                    cord[3] = 0;
                    Explosion deth=new Explosion(resources,cord,5,5);
                    while (deth.i<=23){
                        Canvas canvas1=surfaceHolder.lockCanvas();
                        canvas1.drawBitmap(fon, 0, 0, null);
                        canvas1.drawText("YOU DIED TAP TO RESTART",0.0f,MAX_Y/3,paint);
                        game=false;
                        deth.draw(canvas1);
                       try {
                           TimeUnit.MILLISECONDS.sleep(100);
                       }catch (InterruptedException e){}
                       surfaceHolder.unlockCanvasAndPost(canvas1);
                    }
                }
            });thread.start();}
        }
}



}
