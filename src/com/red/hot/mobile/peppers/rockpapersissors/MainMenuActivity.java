package com.red.hot.mobile.peppers.rockpapersissors;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainMenuActivity extends Activity implements OnClickListener{
	private Button Instructions;
	private Button Newgame;
	private Button MyScore;
	private String username;
	DBAdapter dbAdapter;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		dbAdapter = new DBAdapter(this);
		dbAdapter.open();
		initview();
		initlisteners();
	}

	private void initlisteners() {
	Instructions.setOnClickListener(this);
	Newgame.setOnClickListener(this);
	MyScore.setOnClickListener(this);
	}

	private void initview() {
		
		Instructions=(Button)findViewById(R.id.instructions);
		Newgame=(Button)findViewById(R.id.newgame);
		MyScore=(Button)findViewById(R.id.myscore);
		username = LoginActivity.getVariable();


	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.instructions)
		{
			Intent intent=new Intent(MainMenuActivity.this,InstructionActivity.class);
			startActivity(intent);
			
		}
		if(v.getId()==R.id.newgame)
		{
			Intent intent1=new Intent(MainMenuActivity.this,GameActivity.class);
			startActivity(intent1);
			
		}
		if(v.getId()==R.id.myscore)
		{
			Toast.makeText(this, "wins: "+dbAdapter.getWins(username)+" loss: "+dbAdapter.getLoss(username),Toast.LENGTH_LONG).show();

		}

		
	}
	

}
