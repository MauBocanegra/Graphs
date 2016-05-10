package maurobocanegra.easypays.CustomViews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import maurobocanegra.easypays.R;

/**
 * Created by mbocanegra on 05/05/16.
 */
public class SVLineGraph extends SurfaceView implements  SurfaceHolder.Callback{

    private DrawThread drawThread;
    int w1; int h1;

    public SVLineGraph(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public SVLineGraph(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SVLineGraph(Context context) {
        super(context);

        drawThread = new DrawThread(getHolder(), this);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d("debug","Started!");
        w1=10; h1=10;
        drawThread.startrun(true);
        drawThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        try {
            drawThread.startrun(false);
            drawThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void drawCanvas(Canvas canvas) {
        /*
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setStyle(Paint.Style.STROKE);
        p.setColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        p.setStrokeWidth(20);
        p.setShadowLayer(5, 0, 10, 0x80000000);
        p.setStrokeCap(Paint.Cap.ROUND);
        p.setStrokeJoin(Paint.Join.ROUND);
        p.setStyle(Paint.Style.STROKE);
        canvas.drawLine(10,10,w1,h1,p);
        */
        canvas.drawRGB(254,254,254);
    }

    public class DrawThread extends Thread{

        private SurfaceHolder surfaceHolder;
        private SVLineGraph svLineGraph;
        private boolean running=false;

        public DrawThread(SurfaceHolder h, SVLineGraph svline){
            surfaceHolder=h;
            svLineGraph=svline;
        }

        public void startrun(boolean r){
            running=r;
        }

        @SuppressLint("WrongCall")
        @Override
        public void run(){
            super.run();
            Canvas canvas;
            while(running){
                canvas=null;
                try{
                    canvas = surfaceHolder.lockCanvas(null);
                    synchronized (surfaceHolder) {
                        w1++;
                        Log.d("debug","trying to draw");
                        svLineGraph.drawCanvas(canvas);
                    }
                }finally {
                    if(canvas!=null){
                        Log.d("debug","posted");
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
    }
}
