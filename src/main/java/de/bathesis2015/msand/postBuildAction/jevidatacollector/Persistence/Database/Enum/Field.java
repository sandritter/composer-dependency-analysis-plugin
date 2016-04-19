package de.bathesis2015.msand.postBuildAction.jevidatacollector.Persistence.Database.Enum;

/**
 * StorageFields defines the column names of the database tables
 * StorageFields.java
 *
 * @author Michael Sandritter
 * 03.10.2015
 */
public enum Field {
	BUILD_ID ("build_id"),
	BUILD_NUMBER ("build_number"),
    TIMESTAMP ("time_stamp"),
    JOB_NAME ("job_name"),
    CATEGORY ("category"),
    ANALYSE ("analyse"),

    REFERENCE ("reference"),
    VERSION ("version"),
    DEP_TYPE ("type"),

    COMPONENT ("component"),
    COMPONENT_NAME ("component_name"),
	NAME ("name"),
	VC_URL ("source_url"),
	VC_TYPE ("source_type");
	
	
    private final String name;       

    private Field(String s) {
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
