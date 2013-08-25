package com.macaroon.piztor;

import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.app.Activity;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.SystemClock;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.widget.ImageView;


public class Main extends PiztorAct {

	ActMgr actMgr;

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message m) {
			if (m.what != 0) {
				Location l = (Location) m.obj;
				if (l == null)
					System.out.println("fuck!!!");
				ReqUpdate r = new ReqUpdate(UserInfo.token, l.getLatitude(),
						l.getLongitude(), System.currentTimeMillis(), 1000);
				AppMgr.transam.send(r);
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		id = "Main";
		super.onCreate(savedInstanceState);
		AppMgr.tracker.setHandler(handler);
		EmptyStatus[] r = new EmptyStatus[1];
		r[0] = new EmptyStatus();
		actMgr = new ActMgr(this, r[0], r);
		setContentView(R.layout.activity_main);
		ImageView view = (ImageView) findViewById(R.id.main_mapview);
		view.setOnTouchListener(new MultiTouchListener());
	}

	@Override
	protected void onStart() {
		super.onStart();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}