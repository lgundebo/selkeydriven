package com.drc.wtf.object_repository;

import java.util.HashMap;
import java.util.List;






import java.util.Random;

import com.drc.wtf.core.RandomData;
import com.drc.wtf.test_management.Action;

import DRC.AutomationFramework.Excel.WorkBook;

public class ObjectRepository implements Cloneable
{
	private HashMap<String, Element> elementCache = new HashMap<String, Element>();

	private RandomData  randomData = new RandomData();
	

	public ObjectRepository(String excelFilePath, String workSheetName)
	{
		WorkBook file = new WorkBook(excelFilePath);
		
		file.SetActiveSheet(workSheetName);
		
		//Reads over the first row and adds element to hashmap
		while(file.activeSheet.moveToNextRow())
		{
			List<String> row = file.activeSheet.getCurrentRow();
			
			if(row.size() >= 5 )
			{
				Element element = new Element(row.get(0),row.get(1), row.get(2), row.get(3), row.get(4));
				
				if(elementCache.containsKey(row.get(1)))
				{
					System.out.println("Duplicate entry in the object repository.  Ommitting: " +row.get(1));
					System.out.println("Values: "+ element.toString());
					
				}
				else
				{
				elementCache.put(row.get(1).toUpperCase(), element);
				}
			}
			else if(row.size() >= 1)
			{
				System.out.print("Did not add row: "+ (file.activeSheet.getRowIndex() +1) +". The length of the row was less then 5: length = "+ row.size() );
			}
			
		}
		
		file.close();
		
		
	}
	
	
	//Used to clone the object repository
	public ObjectRepository(ObjectRepository cloneThis)
	{
		this.elementCache = new HashMap<String, Element>(cloneThis.elementCache);
		this.randomData = new RandomData();
	}
	
	
	public Element get(String key)
	{
		
		Element requestedElement = elementCache.get(key.toUpperCase());
		
		return requestedElement;
		
	}
	
	
	
	//logic for Dynamic variables
	//this logic needs to be commented and possible reworked
	public void updateDynamicValues(Action action)
	{
	
 		   Element element = randomData.updateDynamicRepositoryValues(action);
	    		
	   		elementCache.put("DYNAMIC".toUpperCase(), element);	
					
	    	   
		
	}
	
	
	public Action updateActionRandomData(Action action)
	{
		return this.randomData.updateActionRandom(action);
		
	}
	
	
	
	
}
