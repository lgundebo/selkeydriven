package DRC.AutomationFramework.Elements;

public class ElementEnums {
	
	public enum BY {
		ID("id"), 
		NAME("name"), 
		XPATH("xpath"), 
		CSSSELECTOR("cssSelector"),
		CLASSNAME("className"),
		LINKTEXT("linkText"),
		PARTIALLINKTEXT("partialLinkText"),
		TAGNAME("tagName");

		private String value;

		private BY(String value) {
			this.value = value;
		}

		public String getValue() {
			return this.value;

		}

	}

}
