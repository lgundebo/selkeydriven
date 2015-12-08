package DRC.AutomationFramework.FileUtilities;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public  class FileUtilities 
{
	
	 public static void deleteFile(String sFilePathAndName)
	    {	
		       	try{
		       		File file = new File(sFilePathAndName);
		       		if(file.exists()) { file.delete();}    
		       	}catch(Exception e){
		       		e.printStackTrace();	
		       	}
	    
	    } 

	    public static String copyFile(String sourceFile, String destinationFile) {
            
	    	String destinationFilePath = null;
	  
	    	try {
         	       File source = new File(sourceFile);
         	       
         	       destinationFilePath =  destinationFile + source.getName();                   
         	       File destination  = new File(destinationFilePath);	
         	   
         	       destination.getParentFile().mkdirs();
         	       
                    FileInputStream fileInputStream   = new FileInputStream(source);
                    FileOutputStream fileOutputStream;
					
						fileOutputStream = new FileOutputStream(destination);
					

                    int bufferSize;
                    byte[] bufffer = new byte[512];
                    while ((bufferSize = fileInputStream.read(bufffer)) > 0) 
                    {
                            fileOutputStream.write(bufffer, 0, bufferSize);
                    }
                  
						fileInputStream.close();
						 fileOutputStream.close();
						 
					} 
	    	catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
                    catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					
					} 
					      
	    	return destinationFilePath;
           
    }

	    
   public static String copyFile(String sourceFile, String destinationFile, String newFileName) {
            
	    	String destinationFilePath = null;
	  
	    	try {
         	       File source = new File(sourceFile);
         	       
         	       destinationFilePath =  destinationFile + newFileName;                   
         	       File destination  = new File(destinationFilePath);	
         	   
         	       destination.getParentFile().mkdirs();
         	       
                    FileInputStream fileInputStream   = new FileInputStream(source);
                    FileOutputStream fileOutputStream;
					
						fileOutputStream = new FileOutputStream(destination);
					

                    int bufferSize;
                    byte[] bufffer = new byte[512];
                    while ((bufferSize = fileInputStream.read(bufffer)) > 0) 
                    {
                            fileOutputStream.write(bufffer, 0, bufferSize);
                    }
                  
						fileInputStream.close();
						 fileOutputStream.close();
						 
					} 
	    	catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
                    catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					
					} 
					      
	    	return destinationFilePath;
           
    }
	    
}
