package com.red.hot.mobile.peppers.rockpapersissors;

import java.util.ArrayList;
import java.util.Locale;










import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends Activity implements OnClickListener{
	private ImageView micro ;
	private ImageView rock ;
	private ImageView paper ;
	private ImageView sissors ;
	private String username;
	DBAdapter dbAdapter;
	private TextView wins;
	private TextView losses;
	
	 private final int REQ_CODE_SPEECH_INPUT = 100;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_page);
		dbAdapter = new DBAdapter(this);
		dbAdapter.open();
		initview();
		valuetoview();
		initlistener();

	}
	private void valuetoview() {
		int win=dbAdapter.getWins(username);
		int loss=dbAdapter.getLoss(username);
		wins.setText(""+win);
		losses.setText(""+loss);
	}
	private void initlistener() {
	micro.setOnClickListener(this);	
	rock.setOnClickListener(this);	
	paper.setOnClickListener(this);	
	sissors.setOnClickListener(this);	
	
	}
	private void initview() {
	micro=(ImageView)findViewById(R.id.microphone);
	rock=(ImageView)findViewById(R.id.rock);
	paper=(ImageView)findViewById(R.id.paper);
	sissors=(ImageView)findViewById(R.id.sissors);
	username = LoginActivity.getVariable();
	wins=(TextView)findViewById(R.id.wins);
	losses=(TextView)findViewById(R.id.losses);
	}
	private void StartSpeach() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.say_something));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

	  /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
 
        switch (requestCode) {
        case REQ_CODE_SPEECH_INPUT: {
            if (resultCode == RESULT_OK && null != data) {
 
                ArrayList<String> result = data
                        .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
             
                	
                	if(result.get(0).trim().equals("rock"))
                	{
//                		Toast.makeText(this, result.get(0),Toast.LENGTH_LONG).show();
                		computeresult(0);
                	}
                	else if(result.get(0).trim().equals("paper"))
                	{
//                		Toast.makeText(this, result.get(0),Toast.LENGTH_LONG).show();
                		computeresult(1);
                	}
                	else if(result.get(0).trim().equals("scissors"))
                	{
//                		Toast.makeText(this, result.get(0),Toast.LENGTH_LONG).show();
                		computeresult(2);
                	}else
                	{
//                		Toast.makeText(this, result.get(0),Toast.LENGTH_LONG).show();
                		showErrorMessage();
                	}
                
            }
            break;
        }
 
        }
    }
	
	
	private void showErrorMessage() {
		new AlertDialog.Builder(this)
	    .setTitle("error")
	    .setMessage("please choose rock or paper or scissors!!")
	    .setPositiveButton("let me play again!", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            dialog.dismiss();
	        }
	     })
	    .setIcon(android.R.drawable.ic_dialog_alert)
	     .show();
	}
	private void computeresult(int userChoice) {
		GameEngine gameengine= new GameEngine();
		int computerchoice= gameengine.computeSelection();
		int result= gameengine.compareResults(userChoice,computerchoice);
		ShowDialog(result, userChoice, computerchoice);
	
		
		if(result==0)
		{
//			Toast.makeText(this, "Game tied",Toast.LENGTH_LONG).show();
		
		}
		else if(result==1)
		{
//			Toast.makeText(this, "you win",Toast.LENGTH_LONG).show();
			dbAdapter.winsUpdate(username);
			wins.setText(""+dbAdapter.getWins(username));
		}
		else
		{
//			Toast.makeText(this, "you lose",Toast.LENGTH_LONG).show();
			dbAdapter.lossUpdate(username);
			losses.setText(""+dbAdapter.getLoss(username));
		}
	}
	public void ShowDialog(int result, int userchoice , int computerchoice )
	{
		new AlertDialog.Builder(this)
	    .setTitle(getResultString(result))
	    .setMessage(getResultString(result)+ " you chose "+getChoiceString(userchoice)+
	    		" and computer chose "+getChoiceString(computerchoice))
	    .setPositiveButton("let me play again!", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            dialog.dismiss();
	        }
	     })
	    .setIcon(android.R.drawable.ic_dialog_alert)
	     .show();
	}
	private String getChoiceString(int choice) {
		if(choice==0)
		{
		return "rock";	
		}else if(choice ==1)
		{
			return "paper";
		}else
		{
			return "scissors";
		}
	}
	private String getResultString(int result) {
		if(result==0)
		{
			return "game tied!!";
		}
		else if(result==1)
		{
			return "you win!!";
			
		}
		else
		{
			return "you lose!!";
		}
		
	}
	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.microphone)
		{
			StartSpeach();
		}
		else if(v.getId()==R.id.rock)
		{
			computeresult(0);


		}else if(v.getId()==R.id.paper)
		{
			computeresult(1);
		}else if(v.getId()==R.id.sissors)
		{
			computeresult(2);
		}
	}

}
