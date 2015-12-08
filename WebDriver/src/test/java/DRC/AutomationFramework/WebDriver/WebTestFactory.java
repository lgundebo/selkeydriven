package DRC.AutomationFramework.WebDriver;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

//import jxl.read.biff.BiffException;

import org.testng.annotations.Factory;

//import DRC.AutomationFramework.Excel.WorkBook;

import DRC.AutomationFramework.WebDriver.DriverEnumerators.*;

public class WebTestFactory 
{
	/*
	 @Factory 
	  public Object[] createInstances() throws Exception 
	 {
		 /*
		 Object[] result  = new Object[0]; 
		 
		 String filePath = "\\\\sqafs01\\qadoc\\DRCAutomation\\DebugConfig.xls";
		 WorkBook configWorkBook =null;
		
		 try{
			configWorkBook = new WorkBook(filePath);
		
		 configWorkBook.SetActiveSheet("TargetComputers");

		 List<String> row;// = configWorkBook.activeSheet.getCurrentRow();
			
			
			int count =0;
			while(configWorkBook.activeSheet.moveToNextRow() && configWorkBook.activeSheet.getCurrentRow().size() > 0)
			 {
				System.out.println(configWorkBook.activeSheet.getRowIndex());
				row = configWorkBook.activeSheet.getCurrentRow();
				 if(!row.get(1).equals("NONE"))
				 {
				 
				 DriverSetup a = new DriverSetup();
				   a.browser = Browser.valueOf(row.get(2).toUpperCase());
				   a.hubURL = HubURL.LOCALHOST;
				   a.platform = Platform.valueOf(row.get(1).toUpperCase());
				   a.targetBits = BrowserTargetBits.BIT_32;
				   a.version = Version.NOTSPECIFIED;
				   
				   result = ResizeArray(result);
				   result[count] = new ParrallelTest(a);
				 count++;
				 }
				 
				 
				
			 }
				 
			 }
		   finally
		   {
		configWorkBook.close();
		   }
		    return result;
		    
	  }
*/
	 
	 public Object[] ResizeArray(Object[] source) {  
		    int size = source.length;  
		    Object[] destination = new Object[size+1];
		    System.arraycopy( source, 0, destination, 0, size);  
		    
		    return destination;
		}  

}
