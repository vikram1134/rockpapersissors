package com.red.hot.mobile.peppers.rockpapersissors;

import android.widget.Toast;

public class GameEngine {
	public int computeSelection()
	{
	int computerSelection=(int)(Math.random() * 3);
	
	
	
	return computerSelection;
	}
	public int compareResults(int userChoice, int computerSelection)
	{
		int winner;
		if(userChoice==computerSelection)
	{
		winner=0;
	}
	else if((userChoice==0&&computerSelection==2)||
			(userChoice==2&&computerSelection==1)||
			(userChoice==1&&computerSelection==0))
	{
		winner=1;
	}
	else
	{
		winner =2;
	}
		
		return winner;
	}

}
