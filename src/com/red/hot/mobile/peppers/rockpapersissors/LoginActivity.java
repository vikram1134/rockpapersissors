package com.red.hot.mobile.peppers.rockpapersissors;

import java.util.Calendar;

import org.joda.time.LocalDate;
import org.joda.time.Years;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener, OnFocusChangeListener{
	private EditText uname;
	private EditText age;
	private RadioGroup radioGroup;
	private Button login;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initviews();
		initListeners();
	}

	private void initListeners() {
		// TODO Auto-generated method stub
		login.setOnClickListener(this);
		age.setOnClickListener(this);
		age.setOnFocusChangeListener(this);
		
	}

	private void initviews() {
		uname=(EditText)findViewById(R.id.username);
		age=(EditText)findViewById(R.id.age);
		radioGroup=(RadioGroup)findViewById(R.id.gender_group);
		login=(Button)findViewById(R.id.login);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.login)
		{
		String name=uname.getText().toString();
		String ageString=age.getText().toString();
		checkInput(name,ageString);
		Intent intent=new Intent(LoginActivity.this,MainMenuActivity.class);
		startActivity(intent);
		finish();
		}else
		{showDialog();
		}
	
	}

	private void checkInput(String name,String ageString) {
		try {
			int years=Integer.parseInt(ageString);
			if(years<10)
			{
				Toast.makeText(this,"error", Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
		}
		
	}

	private void showDialog()
	{
		final Calendar c = Calendar.getInstance();
		int mYear = c.get(Calendar.YEAR);
		int mMonth = c.get(Calendar.MONTH);
		int mDay = c.get(Calendar.DAY_OF_MONTH);
		 
		DatePickerDialog dpd = new DatePickerDialog(this,
		        new DatePickerDialog.OnDateSetListener() {
		 
		            @Override
		            public void onDateSet(DatePicker view, int year,
		                    int monthOfYear, int dayOfMonth) {
		                LocalDate bithdayDate=new LocalDate(year, monthOfYear, dayOfMonth);
		            	
						LocalDate now = new LocalDate();
						Years ageInyears = Years.yearsBetween(
								bithdayDate, now);
						age.setText(""+ageInyears.getYears());
		            }
		        }, mYear, mMonth, mDay);
		dpd.show();
	}
	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		// TODO Auto-generated method stub
		if(hasFocus)
		{
			showDialog();
		}
		
	}

}
