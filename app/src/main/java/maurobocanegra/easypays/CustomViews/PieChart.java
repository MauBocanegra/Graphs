package maurobocanegra.easypays.CustomViews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import maurobocanegra.easypays.R;

/**
 * Created by mbocanegra on 20/04/16.
 */
public class PieChart extends View {
    /*
    * public LineGraph(Context context, AttributeSet attrs) {
        super(context, attrs);
        thisView=this;
    }*/
    int padding=40;
    View thisView;

    public  PieChart(Context context, AttributeSet attrs){
        super(context, attrs);
        thisView = this;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas){
        Paint paints = new Paint(Paint.ANTI_ALIAS_FLAG);
        paints.setStyle(Paint.Style.STROKE);
        paints.setColor(ContextCompat.getColor(getContext(), R.color.Cyan500));
        paints.setStrokeWidth(20);
        paints.setShadowLayer(5, 0, 10, 0x80000000);
        paints.setStrokeCap(Paint.Cap.ROUND);
        paints.setStrokeJoin(Paint.Join.ROUND);
        paints.setStyle(Paint.Style.STROKE);

        RectF oval = new RectF();
        Log.d("debug", "width=" + getWidth());
        oval.set(0 + padding, 0 + padding, getWidth() - padding, getWidth() - padding);

        Path guidePath = new Path();
        guidePath.arcTo(oval, 0, 90, false);

        Path paintPath = new Path();
        float[] pos = new float[]{0.0f,0.0f};
        PathMeasure guidePathMeasure;

        paintPath.moveTo(getWidth() / 2, getHeight() / 2);
        guidePathMeasure = new PathMeasure(guidePath,false);
        guidePathMeasure.getPosTan(0,pos,null);
        paintPath.lineTo(pos[0],pos[1]);

        paintPath.moveTo(getWidth() / 2, getHeight() / 2);
        guidePathMeasure = new PathMeasure(guidePath,false);
        guidePathMeasure.getPosTan(guidePathMeasure.getLength(),pos,null);
        paintPath.lineTo(pos[0],pos[1]);
        //getPosTan
        //path.lineTo();
        //path.arcTo(oval, 0, 90);

        canvas.drawPath(paintPath, paints);
        canvas.drawPath(guidePath, paints);
    }
}
