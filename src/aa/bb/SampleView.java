package aa.bb;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.util.TypedValue;
import android.view.View;

public class SampleView extends View {
    private Paint mPaints;
    private Paint mPaints2;;
    
    
    private float mSweep = 0.1f;
    private float limit = 10;
    
    private int mBg = 0;
//    Shader shader2;
    
    Bitmap bit;
    Bitmap bit2;
    Bitmap milestone;
    
    //-- --//
    private float ratio = 2.f; 
    private float stroke = 46.67f; // dp

    private float start_degree = 135 + 6;
    private float end_degree = 270 - 12;
    
    private static float  SWEEP_INC = 5;
    private static final float START_INC = 0;
    
    public void setBg(int i) {
    	mBg = i;
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
        this.setMeasuredDimension(parentWidth, parentHeight);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    
    public SampleView(Context context) {
        super(context);
               
        mPaints = new Paint();
        mPaints.setTextSize(120);
        
        Resources res = getResources();
        BitmapDrawable bd = (BitmapDrawable)res.getDrawable(R.drawable.gold);
		 bit = bd.getBitmap();
        
        //-- --//
        mPaints2 = new Paint(mPaints);
        mPaints2.setStyle(Paint.Style.STROKE);
        mPaints2.setStrokeWidth(140);
        mPaints2.setStrokeCap(Paint.Cap.ROUND);
        mPaints2.setAntiAlias(true);
       
       //-- --// 
        {
    		Bitmap bitmap = bd.getBitmap();
    		ratio = (float) bitmap.getHeight() / (float) bitmap.getWidth();
	
    		mPaints2.setStrokeWidth(stroke);
    		
    		{
    			res = getResources();
    			bd = (BitmapDrawable)res.getDrawable(R.drawable.milestones);
    			milestone = bd.getBitmap();
    		}
    		
    		
        }
        
    }
    
    private static Shader getDrawableShader(Context ctx, int resId) {
	    Bitmap bmp = BitmapFactory.decodeResource(ctx.getResources(), resId);
	    return new BitmapShader(bmp, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
	}
    
    private void drawArcs(Canvas canvas, RectF oval, boolean useCenter,
                          Paint paint) {
//    	Shader shader = getDrawableShader(getContext(), R.drawable.bg_blue);
    	   Shader shader = new BitmapShader(bit2, TileMode.CLAMP, TileMode.CLAMP);
	    	// In onDraw()
	    	paint.setShader(shader);
	        canvas.drawArc(oval, start_degree, mSweep, useCenter, paint);
    }
    
    
    @Override 
    protected void onDraw(Canvas canvas) {
    		
    	RectF bounds = new RectF(canvas.getClipBounds());    	
    	
    	float real_w = bounds.right - bounds.left;
    	
    	stroke = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 46.67f,  getResources().getDisplayMetrics()); // dp -> px
		stroke *= ratio * real_w / canvas.getWidth(); 

		bit2 = Bitmap.createScaledBitmap(bit, (int) real_w, (int)( real_w * ratio) , true);

		Resources res = getResources();
		BitmapDrawable bd = (BitmapDrawable)res.getDrawable(R.drawable.track);
		Bitmap bitmap = bd.getBitmap();
		
		bounds.bottom = real_w * ratio + bounds.top; 
		
		canvas.drawBitmap(bitmap, new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), bounds, mPaints);
        
		bit = Bitmap.createScaledBitmap(bit, 
				(int)( canvas.getWidth()), 
				(int) (canvas.getWidth() * ratio), true);
		
		bounds = new RectF(canvas.getClipBounds());
		bounds.bottom = real_w * ratio + bounds.top;
		
		bounds.left += (float) stroke  / 2.f;
		bounds.top += (float) stroke / 2.f;
		bounds.right -= (float) stroke / 2.f;
		bounds.bottom += (float) stroke;
		
		
		canvas.drawText(""+mSweep, bounds.centerX(), bounds.centerY(), mPaints);
		
		mPaints2.setStrokeWidth(stroke);	
		
		if (mSweep < end_degree) {
			mSweep += SWEEP_INC;
		} 
		
		if (mSweep > end_degree) {
			mSweep = end_degree;
		}		

        drawArcs(canvas, bounds, false, mPaints2);
		
		bounds = new RectF(canvas.getClipBounds());
		bounds.bottom = real_w * ratio + bounds.top;
		canvas.drawBitmap(milestone, new Rect(0, 0, milestone.getWidth(), milestone.getHeight()), bounds, mPaints);
        
		invalidate();
    }

	public static int getSweepInc() {
		return Float.floatToIntBits(SWEEP_INC);
	}
}
