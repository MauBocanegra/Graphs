package maurobocanegra.easypays.CustomViews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;

import maurobocanegra.easypays.R;
import maurobocanegra.easypays.Utils.Dynamics;

/**
 * Created by mbocanegra on 19/04/16.
 */
public class LineGraph extends View {

    int padding=40;                     //es el padding interno de la grafica
    int maxY;                           //valor maximo en el array pej {1,2,-8-,3,4,5,6}
    int maxX;                           // cantidad de valores en el array
    int[][] lineGraphs;
    int gridSpaceVert;
    int gridSpaceHor;
    float contPathLen=0.0f;
    View thisView;
    boolean shadowsOn=true;

    int[] paintIds;
    Paint[] paints;
    Path[] paintPaths;
    Path[] guidePaths;
    PathMeasure[] pathMeasures;
    float longestPathMeasureLength;
    boolean hasInit=false;
    public LineGraph(Context context, AttributeSet attrs) {
        super(context, attrs);
        thisView=this;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(w+h>0){

            //paintpaths ahora tiene 2 lugares
            paintPaths = new Path[lineGraphs.length];
            for(int i=0; i< lineGraphs.length; i++){
                paintPaths[i]=new Path();
                paintPaths[i].moveTo(0 + padding, getHeight() - padding);
            }

            paints = new Paint[lineGraphs.length];
            for(int i=0; i< lineGraphs.length; i++){
                paints[i] = new Paint(Paint.ANTI_ALIAS_FLAG);
                paints[i].setStyle(Paint.Style.STROKE);
                paints[i].setColor(ContextCompat.getColor(getContext(), paintIds[i]));
                paints[i].setStrokeWidth(20);
                if(shadowsOn)
                    paints[i].setShadowLayer(5, 0, 10, 0x80000000);
                paints[i].setStrokeCap(Paint.Cap.ROUND);
                paints[i].setStrokeJoin(Paint.Join.ROUND);
                paints[i].setStyle(Paint.Style.STROKE);
            }

            viewHandler.post(updateView);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint gridPaint = new Paint();
        gridPaint.setStyle(Paint.Style.STROKE);
        gridPaint.setColor(0xFFEEEEEE);
        gridPaint.setStrokeWidth(2);

        gridSpaceVert=((getHeight()-(padding*2))/maxY);
        gridSpaceHor=((getWidth()-(padding*2))/maxX);

        //Draw Grid
        canvas.drawLine(0 + padding, 0 + padding, 0 + padding, getHeight() - padding, gridPaint);
        canvas.drawLine(0 + padding, getHeight() - padding, getWidth() - padding, getHeight() - padding, gridPaint);

        //DrawYguidelines
        for(int i=0; i<maxY; i++)
            canvas.drawLine(0+padding, getHeight()-padding-(gridSpaceVert*i), getWidth()-padding, getHeight()-padding-(gridSpaceVert*i), gridPaint);

        //DrawXGuideLines
        for(int i=0; i<maxX; i++)
            canvas.drawLine(getWidth()-padding-(gridSpaceHor*i),0+padding,getWidth()-padding-(gridSpaceHor*i),getHeight()-padding, gridPaint);

        if(hasInit){
            for(int i=0; i<paintPaths.length; i++){
                canvas.drawPath(paintPaths[i], paints[i]);
            }
        }

    }

    //Hanlder e hilo que se encargan de animar el path
    Handler viewHandler = new Handler();
    Runnable updateView = new Runnable(){
        @Override
        public void run(){
            thisView.invalidate();
            updateState();
            //se vuelve a llamar mientras no se dibuje todo el path
            if(contPathLen<longestPathMeasureLength){
                viewHandler.postDelayed(updateView, 15l);
            }
        }
    };

    private void init(){

        guidePaths = new Path[lineGraphs.length];
        //movemos el guidepath al primer valor pasado en los arreglos de cada lineGraph
        for(int i=0; i<guidePaths.length; i++){
            guidePaths[i]=new Path();
            guidePaths[i].moveTo(0 + padding, getYPos(lineGraphs[i][0]));

        }

        paintPaths = new Path[lineGraphs.length];
        //iniciamos a iterar desde el segundo valor en los lineGraphs ya que ya se movio al primero anteriormente
        for(int i=0; i<paintPaths.length; i++){
            paintPaths[i]=new Path();
            paintPaths[i].moveTo(0 + padding, getYPos(lineGraphs[i][0]));
        }

        int pos=1;

        for(int i=0; i<guidePaths.length; i++){
            pos=1;
            for(int j=1; j<lineGraphs[i].length; j++) {
                guidePaths[i].lineTo(getXPos(pos), getYPos(lineGraphs[i][j]));
                //Log.d("debug","guidePath["+i+"]("+pos+","+lineGraphs[i][j]+")=["+getXPos(pos)+","+getYPos(lineGraphs[i][j])+"]");
                pos++;
            }
        }

        pathMeasures = new PathMeasure[lineGraphs.length];
        for(int i=0; i<pathMeasures.length; i++){
            pathMeasures[i] = new PathMeasure(guidePaths[i], false);
        }

        longestPathMeasureLength = getMaxPathMeasureLength(pathMeasures);

        hasInit=true;
    }

    private void updateState(){

        if(contPathLen==0.0f){
            init();
        }

        contPathLen+=10.0f;

        float coor[] = {0f,0f};
        for(int i=0; i<pathMeasures.length; i++){
            pathMeasures[i].getPosTan(contPathLen,coor,null);
            paintPaths[i].lineTo(coor[0],coor[1]);
        }
    }

    private float getYPos(int val){
        return getHeight()-padding-(gridSpaceVert*val);
    }
    private float getXPos(int val) {
        return getWidth()-padding-(gridSpaceHor*(maxX-val));
    }

    private int getMaxVal(int [][] arr){
        int max = -999999;
        for(int[] subArr : arr){
            for(int i : subArr){
                if(i>=max)
                    max=i;
            }
        }
        return max;
    }

    private int getMaxItems(int [][] arr){
        int max = -999999;
        for(int[] subArr : arr){
            if(subArr.length>max){
                max=subArr.length;
            }
        }
        return max;
    }

    private float getMaxPathMeasureLength(PathMeasure[] paths){
        float max = -9999.0f;
        for(int i=0; i<paths.length; i++){
            if(paths[i].getLength()>=max){max=paths[i].getLength();}
        }
        return max;
    }

    // -------------------------

    public void setData(int[][] lineGraphsData){
        lineGraphs = lineGraphsData;
        maxY=getMaxVal(lineGraphs);
        maxX=getMaxItems(lineGraphs)-1;
    }

    public void setColors(int[] colorsData){
        paintIds = colorsData;
    }

    public void setShadows(boolean shadowsBoolean){
        shadowsOn=shadowsBoolean;
    }
}






