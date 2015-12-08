package com.drc.wtf.core;

public class CodeTimer 
{
	private long startTime;
	private long endTime; 

	public void start()
	{
		startTime = System.currentTimeMillis();
	}
	
	public void stop()
	{
		endTime = System.currentTimeMillis();
	}
	
	
	public int getTime()
	{
		int time = (int) ((endTime -startTime)/1000);
		return time;
	}

	public int getRunningTime()
	{
		int time = (int) (( System.currentTimeMillis() -startTime)/1000);
		return time;
	}
	
	
	public int getTimeMillis()
	{
		int time = (int) (endTime -startTime);
		return time;
	}
	
}

