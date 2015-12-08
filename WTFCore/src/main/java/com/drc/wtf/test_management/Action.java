package com.drc.wtf.test_management;

public class Action implements Cloneable
{
    public String actionName;
    public String fieldName;
    public String fieldValue;
    public String pageName;
    public int    testStep;


    // Do we need all of these?

   
  

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }
}
