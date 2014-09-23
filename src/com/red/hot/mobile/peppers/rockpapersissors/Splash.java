package com.red.hot.mobile.peppers.rockpapersissors;

import android.app.Activity;
import android.content.Intent;
import android.graphics.AvoidXfermode;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;

public class Splash extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		Runnable mRunnable;
		Handler mHandler = new Handler();
		mRunnable = new Runnable() {
			@Override
			public void run() {
				Intent intent=new Intent(Splash.this,LoginActivity.class);
				startActivity(intent);
				finish();
				
			}
		};
		mHandler.postDelayed(mRunnable,
				2 * 1000);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
}
