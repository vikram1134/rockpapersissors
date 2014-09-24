package com.red.hot.mobile.peppers.rockpapersissors;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ScoreActivity extends Activity{
	private TextView uname;
	private TextView wins;
	private TextView losses;
	String username;
	DBAdapter dbAdapter;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_score);
		dbAdapter = new DBAdapter(this);
		dbAdapter.open();
		
		initview();
		valuetoview();
	}

	private void valuetoview() {
			username = LoginActivity.getVariable();
			uname.setText(username);
			wins.setText(""+dbAdapter.getWins(username));
			losses.setText(""+dbAdapter.getLoss(username));

}

	private void initview() {
		uname=(TextView)findViewById(R.id.uname);
		wins=(TextView)findViewById(R.id.wins);
		losses=(TextView)findViewById(R.id.losses);
		
		
	}
}
