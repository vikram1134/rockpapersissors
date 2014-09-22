package com.red.hot.mobile.peppers.rockpapersissors;

public class CurrentSession {
	
	private String currentUser;
	private String gender;
	private int age;
	private int wins;
	private int losses;
	
	public CurrentSession(String pCurrentUser, String pGender, 
			int pAge, int pWins, int pLosses) 
	{
		currentUser = pCurrentUser;
		gender = pGender;
		age = pAge;
		wins = pWins;
		losses = pLosses;
	}
	
	public void updateWins() 
	{
		wins++;
	}
	
	public void updateLosses() 
	{
		losses++;
	}
	
	public String getCurrentUser()
	{
		return currentUser;
	}
	
	public String getGender() 
	{
		return gender;
	}
	
	public int getAge() 
	{
		return age;
	}
	
	public int getWins()
	{
		return wins;
	}
	
	public int getLosses()
	{
		return losses;
	}
	
}
