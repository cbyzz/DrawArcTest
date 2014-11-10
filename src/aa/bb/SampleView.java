package aa.bb;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.view.View;

public class SampleView extends View {
    private Paint mPaints;
    private Paint mFramePaint;
    private boolean[] mUseCenters;
    private RectF[] mOvals;
    private RectF mBigOval;
    private float mStart;
    private float mSweep;
    private int mBigIndex;
    private int mBg = 0;
    
    private static final float SWEEP_INC = 1;
    private static final float START_INC = 0;
    
    public void setBg(int i) {
    	mBg = i;
    }
    
    public SampleView(Context context) {
        super(context);
        
        mPaints = new Paint();
        mUseCenters = new boolean[4];
        mOvals = new RectF[4];

        mPaints = new Paint(mPaints);
        mPaints.setStyle(Paint.Style.STROKE);
        mPaints.setStrokeWidth(80);
        mPaints.setColor(Color.rgb(255, 220, 21));
        mPaints.setAntiAlias(true);
        mPaints.setStrokeCap(Paint.Cap.ROUND);
//        mBigOval = new RectF(15, 15, 155, 150);
        mBigOval = new RectF(100, 150, 620,660);
    }
    
    private static Shader getDrawableShader(Context ctx, int resId) {
	    Bitmap bmp = BitmapFactory.decodeResource(ctx.getResources(), resId);
	    return new BitmapShader(bmp, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
	}
    
    private void drawArcs(Canvas canvas, RectF oval, boolean useCenter,
                          Paint paint) {
     // Outside of onDraw(), preferably on a background thread if possible
//	    	Shader shader = getDrawableShader(getContext(), R.drawable.bg_blue);
//	    	// In onDraw()
//	    	paint.setShader(shader);
//	        canvas.drawArc(oval, 135, mSweep, useCenter, paint);
    		Shader shader2 = getDrawableShader(getContext(), R.drawable.bg_gray);
	    	// In onDraw()
	    	paint.setShader(shader2);
	        canvas.drawArc(oval, 135, mSweep, useCenter, paint);
    }
    
    
    @Override protected void onDraw(Canvas canvas) {
    	canvas.drawColor(Color.alpha(Color.GRAY));
        drawArcs(canvas, mBigOval, mUseCenters[2],
                 mPaints);
        
        
        mSweep += SWEEP_INC;
        if (mSweep > 270) {
            mSweep -= 270;
            mStart += START_INC;
            if (mStart >= 270) {
                mStart -= 270;
            }
            mBigIndex = (mBigIndex) % mOvals.length;
        }
        if ( mSweep != 270) {
        	invalidate();
        }
    }

	public static int getSweepInc() {
		return Float.floatToIntBits(SWEEP_INC);
	}
}
