package DRC.AutomationFramework.Excel;

import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;


public class WorkSheet 
{
	
	public String name;
	private WritableSheet worksheet;
	private int currentRow =0;
	private int totalRows;
	
	
	public WorkSheet(String wsName , WritableSheet sheet)
	{
		
		name = wsName;
		worksheet = sheet;
		totalRows = sheet.getRows();
	
	}

	public int getRowIndex()
	{
		
		return currentRow;
	}
	
	public Boolean moveToNextRow()
	{
		Boolean moreRows = false;
		if(currentRow < totalRows )
		{
		currentRow++;
		moreRows = true;
		}
		
		
		return moreRows;
	}
	
	public List<String> getCurrentRow()
	{
		
		return getRowByIndex(currentRow);
		
	}
	
	
	public List<String> getRowByIndex(int index)
	{
		
List<String> cellValues = null;
		
		
		cellValues= new ArrayList<String>(); 
		Cell[] cells = worksheet.getRow(index);
		
		for(int i =0; i < cells.length; i++)
		{
			cellValues.add(cells[i].getContents().toString().trim());
			
		}
		
		
		return cellValues;
	}
	
	public void updateRow(int index, List<String> updatedRow)
	{
		for(int i =0; i< updatedRow.size(); i++)
		{
			WritableCell cell = worksheet.getWritableCell(i, index);
			
			CellType type = cell.getType();
			if (type == CellType.LABEL ) 
			{ 
			  Label l = (Label) cell; 
			  l.setString(updatedRow.get(i)); 
			}
			else if(type ==CellType.EMPTY)
			{
				 Label l = new Label(i, index, updatedRow.get(i));
		
				 try 
				 {
					worksheet.addCell(l);
				 } 
				 catch (RowsExceededException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (WriteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 
			}
			
		}
	}
	
	
	public void updateCurrentRow(List<String> updatedRow)
	{
		
		updateRow(currentRow, updatedRow);
		
	
		
	}
	
	
	
}
