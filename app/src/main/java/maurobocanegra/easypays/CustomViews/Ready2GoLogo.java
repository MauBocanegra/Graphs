package maurobocanegra.easypays.CustomViews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import maurobocanegra.easypays.R;

/**
 * Created by mbocanegra on 05/05/16.
 */
public class Ready2GoLogo extends View{

    View thisView;
    boolean hasInit=false;

    boolean whiteCircleFinished=false;
    boolean stopPainting=false;

    int padding=40;

    int whiteCircleCounter=0;

    Paint whiteCirclePaint;
    Paint otherPaint;
    Paint trianglePaint;
    Paint midTrianglePaint;
    Paint linePaint;
    Paint whiteTrianglePaint;
    Path trianglePath;
    Path linePath;
    Path midTriangle;
    Path lineGuideWhiteTriangle;
    Path lineWhiteTriangle;
    float middlePointLineR[] = {0f,0f};
    float midCirclePoint[] = {0f,0f};
    Path midCirclePath;
    float whiteTrianglePoints[][];

    public Ready2GoLogo(Context context, AttributeSet attrs){
        super(context, attrs);
        thisView=this;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh){
        super.onSizeChanged(w, h, oldw, oldh);

        padding=((getWidth()/3)-(getWidth()/5));
        Log.d("Padding","padd="+padding);

        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        Log.d("debug", "threadStarted");
        viewHandler.postDelayed(updateView,500);
    }

    Handler viewHandler = new Handler();
    Runnable updateView = new Runnable(){
        @Override
        public void run(){
            thisView.invalidate();
            updateState();
            if(!stopPainting) {
                viewHandler.postDelayed(updateView, 5l);
            }else{
                Log.d("debug", "threadStopped");
            }
        }
    };

    @Override
    protected void onDraw(Canvas canvas){
        if(hasInit){
            canvas.drawCircle(getWidth()/2, getHeight()/2, ((getWidth()-padding*7/3)/2)+1, linePaint);
            canvas.drawCircle(getWidth()/2, getHeight()/2, ((getWidth()-padding*7/3)/2), whiteCirclePaint);
            //canvas.drawCircle(getWidth()/2-pcpx(12), getHeight()/2-pcpx(23),5,trianglePaint);
            //canvas.drawCircle(getWidth()/2+pcpx(12), getHeight()/2+pcpx(23),5,trianglePaint);
            //canvas.drawCircle(getWidth()/2-pcpx(11), getHeight()/2+pcpx(20),5,trianglePaint);
            //canvas.drawCircle(getWidth()/2-pcpx(4), getHeight()/2+pcpx(5),5,trianglePaint);
            canvas.drawPath(trianglePath,trianglePaint);
            canvas.drawPath(linePath, linePaint);
            canvas.drawPath(midTriangle, trianglePaint);
            canvas.drawPath(midCirclePath,trianglePaint);
            canvas.drawPath(lineWhiteTriangle,whiteTrianglePaint);
            //canvas.drawArc

            //canvas.drawPath(linePath,trianglePaint);
            //canvas.drawCircle(getWidth()/2, getHeight()/2, whiteCircleCounter, whiteCirclePaint);

            /*
            if(whiteCircleCounter >= ((getWidth()-padding*7/3)/2) ){
                whiteCircleFinished=true;
            }
            */

            whiteCircleFinished=true;
        }
    }

    private void init(){

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.width=getWidth();
        params.height=getWidth();

        //-----


        // **********  PAINTS ********** //

        whiteCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        whiteCirclePaint.setStyle(Paint.Style.STROKE);
        whiteCirclePaint.setColor(ContextCompat.getColor(getContext(), R.color.WhiteMaterial));
        whiteCirclePaint.setShadowLayer(padding / 2, padding / 2, padding / 2, 0x30000000);
        whiteCirclePaint.setStrokeWidth(7);
        whiteCirclePaint.setStyle(Paint.Style.FILL);

        trianglePaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        trianglePaint.setStyle(Paint.Style.FILL);
        trianglePaint.setColor(ContextCompat.getColor(getContext(), R.color.Red500));
        trianglePaint.setShadowLayer(padding / 4, padding / 4, padding / 4, 0x30000000);
        trianglePaint.setStrokeWidth(7);

        midTrianglePaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        midTrianglePaint.setStyle(Paint.Style.FILL);
        midTrianglePaint.setColor(ContextCompat.getColor(getContext(), R.color.Red500));
        midTrianglePaint.setStrokeWidth(7);

        linePaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeCap(Paint.Cap.ROUND);
        linePaint.setColor(ContextCompat.getColor(getContext(), R.color.Red500));
        linePaint.setShadowLayer(padding / 4, padding / 4, padding / 4, 0x30000000);
        linePaint.setStrokeWidth(getWidth()/45);

        otherPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        otherPaint.setStyle(Paint.Style.STROKE);
        otherPaint.setStrokeCap(Paint.Cap.ROUND);
        otherPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        otherPaint.setShadowLayer(padding / 4, padding / 4, padding / 4, 0x30000000);
        otherPaint.setStrokeWidth(5);

        whiteTrianglePaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        whiteTrianglePaint.setStyle(Paint.Style.STROKE);
        whiteTrianglePaint.setColor(ContextCompat.getColor(getContext(), R.color.WhiteMaterial));
        whiteTrianglePaint.setStrokeWidth(getWidth()/45);

        // **********  PATHS ********** //

        trianglePath = new Path();
        trianglePath.moveTo(getWidth() / 2 - pcpx(15), getHeight() / 2 - pcpx(8));
        trianglePath.lineTo(getWidth() / 2 - pcpx(15), getHeight() / 2 + pcpx(25));
        trianglePath.lineTo(getWidth() / 2 - pcpx(6), getHeight() / 2 + pcpx(7));
        trianglePath.close();

        linePath = new Path();
        linePath.moveTo(getWidth() / 2 - pcpx(14), getHeight() / 2 - pcpx(23));
        linePath.lineTo(getWidth()/2+pcpx(14), getHeight() / 2 + pcpx(24));

        midTriangle = new Path();
        PathMeasure measureLineR = new PathMeasure(linePath, false);
        float halfMeasureR = measureLineR.getLength() / 2;
        measureLineR.getPosTan(halfMeasureR + (measureLineR.getLength() / 20), middlePointLineR, null);
        midTriangle.moveTo(middlePointLineR[0], middlePointLineR[1]);
        midTriangle.lineTo(middlePointLineR[0], getHeight() / 2 - pcpx(24));
        midTriangle.lineTo(getWidth() / 2 - pcpx(14), getHeight() / 2 - pcpx(24));

        lineGuideWhiteTriangle = new Path();
        lineGuideWhiteTriangle.moveTo((getWidth() / 2 - pcpx(14)) + (getWidth() / 40), getHeight() / 2 - pcpx(23));
        lineGuideWhiteTriangle.lineTo(middlePointLineR[0] + (getWidth() / 40), middlePointLineR[1]);
        PathMeasure measureGuideLineWhiteTriangle = new PathMeasure(lineGuideWhiteTriangle,false);
        float thirdPartGuileLineWhiteTriangle = measureGuideLineWhiteTriangle.getLength()/3;
        whiteTrianglePoints = new float[3][2];
        measureGuideLineWhiteTriangle.getPosTan(thirdPartGuileLineWhiteTriangle,whiteTrianglePoints[0],null);
        measureGuideLineWhiteTriangle.getPosTan(thirdPartGuileLineWhiteTriangle*2,whiteTrianglePoints[1],null);
        whiteTrianglePoints[2][1]=whiteTrianglePoints[0][1];
        whiteTrianglePoints[2][0]=(int)Math.sqrt(Math.pow(whiteTrianglePoints[0][0]-whiteTrianglePoints[1][0],2));
        whiteTrianglePoints[2][0]*=2;
        whiteTrianglePoints[2][0]+=whiteTrianglePoints[0][0];
        lineWhiteTriangle = new Path();
        lineWhiteTriangle.moveTo(whiteTrianglePoints[0][0],whiteTrianglePoints[0][1]);
        lineWhiteTriangle.lineTo(whiteTrianglePoints[1][0],whiteTrianglePoints[1][1]);
        lineWhiteTriangle.lineTo(whiteTrianglePoints[2][0],whiteTrianglePoints[2][1]);
        lineWhiteTriangle.close();
        //lineWhiteTriangle.lineTo(whiteTrianglePoints[0][0],whiteTrianglePoints[0][1]);

        Path lineMidCircle = new Path();
        lineMidCircle.moveTo(middlePointLineR[0], middlePointLineR[1]);
        lineMidCircle.lineTo(middlePointLineR[0], getHeight() / 2 - pcpx(24));
        PathMeasure measureLineMidCircle = new PathMeasure(lineMidCircle, false);
        measureLineMidCircle.getPosTan(measureLineMidCircle.getLength() / 2, midCirclePoint, null);
        RectF oval = new RectF();
        oval.set(
                midCirclePoint[0]-measureLineMidCircle.getLength() / 2,
                midCirclePoint[1]-measureLineMidCircle.getLength() / 2,
                midCirclePoint[0]+measureLineMidCircle.getLength() / 2,
                midCirclePoint[1]+measureLineMidCircle.getLength() / 2
        );
        midCirclePath = new Path();
        midCirclePath.arcTo(oval,-90,180);
        //oval.set(getWidth()-midCirclePoint[0]);
        //-----
        hasInit=true;
    }

    private void updateState(){
        if(!hasInit) {
            init();
            return;
        }

        if(hasInit) {
            whiteCircleCounter += 5;
        }

        stopPainting = whiteCircleFinished;
    }

    private int pcpx(int per){
        return (int)(getWidth()*per)/100;
    }
}
