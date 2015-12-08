package com.drc.wtf.object_repository;

public class Element 
{
	
	
	public Element()
	{}
	
	public Element(String type, String name, String property, String property1, String property2)
	{
		fieldType = type;
		fieldName = name;
		fieldProperty = property;
		fieldProperty1 = property1;
		fieldProperty2 = property2;
		
	}
	
	@Override
	public String toString()
	{
		String value = fieldType + " " + fieldName + " " + fieldProperty + " "+ fieldProperty1 + " "+ fieldProperty2;
		return value;
		
	}
	
public String fieldType;
public String fieldName;
public String fieldProperty;
public String fieldProperty1;
public String fieldProperty2;
	
}
