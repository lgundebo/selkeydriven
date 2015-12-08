package com.drc.wtf.actions.interactions;

import org.openqa.selenium.Point;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.WebElement;

public class CoordinateWebElement  implements Coordinates {

	private WebElement element;
	public CoordinateWebElement(WebElement _element)
	{
		element = _element;
	}
	
	@Override
	public Point onScreen() {
		// TODO Auto-generated method stub
		return element.getLocation();
	}

	@Override
	public Point inViewPort() {
		// TODO Auto-generated method stub
		return element.getLocation();
	}

	@Override
	public Point onPage() {
		// TODO Auto-generated method stub
		return element.getLocation();
	}

	@Override
	public Object getAuxiliary() {
		// TODO Auto-generated method stub
		return "Asdfsd";
	}

}
