package de.bathesis2015.msand.postBuildAction.jevidatacollector.Persistence.Database.Queries;

/**
 * InsertQueryBuilder.java
 * This object is used as helper to provide all insert queries for the database
 *
 * @author Michael Sandritter
 */
public class InsertQueries {
	
	/**
	 * insert query for the build table
	 */
	public String buildInsertQuery = "INSERT OR REPLACE INTO build (id, time_stamp, build_number, job_name) VALUES (?,?,?,?)";
	
	/**
	 * insert query for the stand table
	 */
	public String standInsertQuery = "INSERT OR REPLACE INTO stand (reference, version, component_name) VALUES (?,?,?)";
	
	/**
	 * insert query for the component table
	 */
	public String componentInsertQuery = "INSERT OR REPLACE INTO component (name, source_url, source_type) VALUES (?,?,?)";

	/**
	 * insert query for the dependency table
	 */
	public String dependencyInsertQuery = "INSERT OR REPLACE INTO dependency (reference, build_id, type) VALUES (?,?,?)";
}
