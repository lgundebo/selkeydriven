package DRC.AutomationFramework.Excel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class WorkBook 
{
	public List<WorkSheet> workSheets = new ArrayList<WorkSheet>();
	private WritableWorkbook workBook;
	public WorkSheet activeSheet;
	
	public WorkBook(String excelFile)
	{
		
		setupWorkBook(excelFile, excelFile);
        
	}
	
	public WorkBook(String templateExcelFile, String newExcelFile)
	{
		
		setupWorkBook(templateExcelFile, newExcelFile);
		
	}
	
	public void save()
	{
		try {
			workBook.write();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void close()
	{
		try {
			
			workBook.write();
			workBook.close();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
        
        public Boolean SetActiveSheet(String name)
        {
       
       Boolean sheetFound = false;
        	for(WorkSheet temp : workSheets)
        	{
        		if(name.equals(temp.name))
        		{
        		activeSheet = temp;
        		sheetFound =true;
        			break;
        		}
        	
        	}
        	
        	return sheetFound;
        }
        
    
        private void setupWorkBook(String currentExcelFile, String newExcelFile)
        {
        	File inputWorkbook = new File(currentExcelFile);
        	File newWorkBook = new File(newExcelFile);
        	
            workBook =null;	
            try {
            	
            	//Use jxl workbook to create a writeable workbook
            	Workbook temp = Workbook.getWorkbook(inputWorkbook);
    			workBook = Workbook.createWorkbook(newWorkBook, temp); // . getWorkbook(inputWorkbook);
    			temp.close();
    			
    		} catch (BiffException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    			System.out.println("Failed to open: " + currentExcelFile);
    			
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    			System.out.println("Failed to open: " + currentExcelFile);
    		
    		}
          
            
            int sheetCount = workBook.getNumberOfSheets();
            String[] sheetNames = workBook.getSheetNames();
            WritableSheet[] sheets = workBook.getSheets();
            
            for(int i =0; i < sheetCount; i++)
            {
            	workSheets.add(new WorkSheet(sheetNames[i], sheets[i]));
            	
            }
        	
        }
		
	
	
}

