package de.bathesis2015.msand.postBuildAction.jevidatacollector.Mapping.Enum;

/**
 * enum describing defining differnt file types
 * JsonFileType.java
 *
 * @author Michael Sandritter
 * 03.10.2015
 */
public enum FileType {
	COMPOSER_LOCK ("composer.lock"), 
	COMPOSER_JSON ("composer.json");
	
	private final String name;       

    private FileType(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : name.equals(otherName);
    }

    @Override
	public String toString() {
       return this.name;
    }
}
