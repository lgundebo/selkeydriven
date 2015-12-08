package com.drc.wtf.test_management;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.drc.wtf.test_management.Action;

public class Log 
{

	private String LogfileName;
	private String logHistory ="";
	private String exceptionHistory ="";
	private Action actionToLogFor;
	
	
	public Log(String logFilePath)
	{
		LogfileName = logFilePath;
		
		File file = new File(logFilePath);
		file.getParentFile().mkdirs();
		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logHistory+= "****************Test Case Execution Starting****************" + System.getProperty("line.separator");
		
	}
	
	protected void setAction(Action action)
	{
		actionToLogFor = action;
	}
	
	
	public void stepFailed(Exception e)
	{
		String stepError = "Step # " + actionToLogFor.testStep + "--> Test failed for action: " + actionToLogFor.actionName +
				System.getProperty("line.separator") +
				" on field: " + actionToLogFor.fieldName + " with value of: " + actionToLogFor.fieldValue; 
		String breakLine = "********************Step Failure********************" +System.getProperty("line.separator");
		logHistory += breakLine;
		
		exceptionHistory += System.getProperty("line.separator");
		exceptionHistory += breakLine;
		exceptionHistory += "--------------Exception details for:-------------------"+System.getProperty("line.separator");
			
		exceptionHistory += stepError +System.getProperty("line.separator") +System.getProperty("line.separator");
		if(e.getCause() != null)
		{
		exceptionHistory += e.getCause().getMessage()  +System.getProperty("line.separator");		
		}
		exceptionHistory += e.getMessage()  +System.getProperty("line.separator");
		
		
		writeLogAndConsole(stepError);
		
		
		 e.printStackTrace();		
		
	}
	
	public void TestFailed(Exception e, String message)
	{
		String testError = "Test Case Failed: "+
	System.getProperty("line.separator") +
				message; 
		String breakLine = "********************Test Failure********************" +System.getProperty("line.separator");
		logHistory += breakLine;
		
		exceptionHistory += System.getProperty("line.separator");
		exceptionHistory += breakLine;
		exceptionHistory += "--------------Exception details for:-------------------"+System.getProperty("line.separator");
			
		exceptionHistory += testError +System.getProperty("line.separator") +System.getProperty("line.separator");
		if(e.getCause() != null)
		{
		exceptionHistory += e.getCause().getMessage()  +System.getProperty("line.separator");		
		}
		exceptionHistory += e.getMessage()  +System.getProperty("line.separator");
		
		
		writeLogAndConsole(testError);
		
		
		 e.printStackTrace();		
		
	}
	
	public void stepFailed()
	{
		String stepError = "Step # " + actionToLogFor.testStep + "--> Test failed for action: " + actionToLogFor.actionName; 
		String breakLine = "********************Step Failure********************" +System.getProperty("line.separator");
		logHistory += breakLine;
		
				
		
		writeLogAndConsole(stepError);
		
	}
	
	public void writeLogAndConsole(String sText){
 	   
		logHistory += sText + System.getProperty("line.separator");
		
		try {
			  writeLogs(LogfileName, sText);
			  System.out.println(sText);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 }
	
	
	public String LogHistory()
	{
		return logHistory;
	}
	
	public String ExceptionHistory()
	{
		return exceptionHistory;
	}
	
	
	
	
	
	  private void writeLogs(String logFileLocation, String sText) throws IOException {
          TimeZone tz = TimeZone.getTimeZone("EST"); // or PST, MID, etc ...
          Date now = new Date();
          DateFormat df = new SimpleDateFormat ("yyyy.mm.dd hh:mm:ss ");
          df.setTimeZone(tz);
          String currentTime = df.format(now);
         
          FileWriter aWriter = new FileWriter(logFileLocation, true);
          aWriter.write(sText +" -->>Time: " +currentTime +" "+ "\r\n");
          aWriter.flush();
          aWriter.close();
   }
      
	
	
}
