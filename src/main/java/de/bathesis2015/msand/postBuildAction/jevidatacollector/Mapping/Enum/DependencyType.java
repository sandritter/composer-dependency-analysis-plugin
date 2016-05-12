package de.bathesis2015.msand.postBuildAction.jevidatacollector.Mapping.Enum;

/**
 * DependencyType.java
 * defines the dependency types of an installed component of a build
 *
 * @author Michael Sandritter
 */
public enum DependencyType {
	MAIN ("main"), 
	HIGH_LEVEL ("direct"),
	LOW_LEVEL ("indirect"),
	ALL ("all");
	
	private final String name;       

    private DependencyType(String s) 
    {
        name = s;
    }

    public boolean equalsName(String otherName) 
    {
        return (otherName == null) ? false : name.equals(otherName);
    }

    @Override
	public String toString() 
    {
       return this.name;
    }
}
