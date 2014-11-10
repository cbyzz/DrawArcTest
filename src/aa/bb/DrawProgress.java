package aa.bb;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class DrawProgress extends Activity {
	
	ProgressBar studi_ProgressBar;
	
	TextView txt_progress;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.main);
		 LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			LinearLayout layBody = (LinearLayout) inflater.inflate(
					R.layout.main, null);
			LinearLayout infoview = (LinearLayout) layBody
					.findViewById(R.id.lay_arc);
			SampleView sampleView = new SampleView(this);
			infoview.addView(sampleView);
			setContentView(layBody);
			sampleView.setBg(0);
			Path path = new Path();
			Canvas canvas = new Canvas();
			path.addCircle(200, 200, 50, Path.Direction.CW);
			canvas.clipPath(path);

	}
}
