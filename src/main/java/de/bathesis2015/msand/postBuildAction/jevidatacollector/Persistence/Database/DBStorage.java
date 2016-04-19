package de.bathesis2015.msand.postBuildAction.jevidatacollector.Persistence.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.DAO.Build;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.DAO.Component;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.DAO.Dependency;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.DAO.Stand;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Transfer.Interface.Transferable;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Persistence.Database.Interface.DataStorage;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Persistence.Database.Util.InsertQueries;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Util.Logger;
import hudson.model.AbstractBuild;

/**
 * 
 * DBStorage.java
 * This objects takes data-access-objects and stores them to database
 *
 * @author Michael Sandritter
 *
 */
public class DBStorage implements DataStorage{
	
	/**
	 * path to the database file
	 */
	private String dbPath;
	
	/**
	 * helper that provides the insert queries
	 */
	private InsertQueries InsertQueries;
	
	/**
	 * {@link Logger}
	 */
	private Logger logger;
	
	/**
	 * constructor
	 * @param dbPath - database path
	 */
	@Inject
	public DBStorage(@Assisted String dbPath){
		this.dbPath = dbPath;
		this.InsertQueries = new InsertQueries();
		this.logger = Logger.getInstance();
	}
	
	/**
	 * is establishing a connection with the database
	 * @return
	 */
	private Connection establishConnection() {
			Connection c = null;
			try {
				Class.forName("org.sqlite.JDBC");
				c = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
				c.setAutoCommit(false);
			} catch (Exception e) {
				logger.println(Logger.LABEL + "-> CONNECTION FAILED: cannot establish connection -> database path: "+ dbPath);
				logger.println(Logger.LABEL + "error message: " + e.getMessage());
				logger.println();
			}
		
		return c;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void storeData(Transferable transport) throws Exception {
		try {
			storeBuild((Build) transport.getObject(Build.class));
			storeComponents((List<Component>) transport.getList(Component.class));
			storeStands((List<Stand>) transport.getList(Stand.class));
			storeDependencies((List<Dependency>) transport.getList(Dependency.class));
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * is storing build-specific data to databse
	 * @param build {@link AbstractBuild}
	 * @throws SQLException
	 */
	private void storeBuild(Build build) throws Exception {
		Connection c = null;
		PreparedStatement stmt = null;
		try {
			c = establishConnection();
			String sql = InsertQueries.buildInsertQuery;
			stmt = c.prepareStatement(sql);
			stmt.setString(1, build.getBuildId());
			stmt.setLong(2, build.getTimestamp());
			stmt.setInt(3, build.getNumber());
			stmt.setString(4, build.getJobName());
			stmt.executeUpdate();
			c.commit();

		} catch (SQLException e) {
			throw new Exception("cannot store data access object -> Build.java\n" + e.getMessage());
		} finally {
			if (stmt != null) stmt.close();
			if (c != null) c.close();
		}
	}

	/**
	 * is storing all component information of a build to database
	 * @param lst - list of {@link Component}
	 * @throws SQLException
	 */
	private void storeComponents(List<Component> lst) throws Exception {
		Connection c = null;
		PreparedStatement stmt = null;
		try {
			c = establishConnection();
			String sql = InsertQueries.componentInsertQuery;
			stmt = c.prepareStatement(sql);
			for (Component component : lst){		
				stmt.setString(1, component.getName());
				stmt.setString(2, component.getSourceUrl());
				stmt.setString(3, component.getSourceType());
				stmt.addBatch();
			}
			stmt.executeBatch();
			c.commit();
		} catch (SQLException e) {
			throw new Exception("cannot store data access object -> Component.java\n" + e.getMessage());
		} finally {
			if (stmt != null) stmt.close();
			if (c != null) c.close();
		}
	}

	/**
	 * is storing all version information about component installed during a build to database
	 * @param lst - list of {@link Stand}
	 * @throws SQLException
	 */
	private void storeStands(List<Stand> lst) throws Exception {
		Connection c = null;
		PreparedStatement stmt = null;
		try {
			c = establishConnection();
			String sql = InsertQueries.standInsertQuery;
			stmt = c.prepareStatement(sql);
			for (Stand s : lst){	
				stmt.setString(1, s.getReference());
				stmt.setString(2, s.getVersion());
				stmt.setString(3, s.getComponentName());
				stmt.addBatch();
			}
			stmt.executeBatch();
			c.commit();

		} catch (SQLException e) {
			throw new Exception("cannot store data access object -> Stand.java\n" + e.getMessage());
		} finally {
			if (stmt != null) stmt.close();
			if (c != null) c.close();
		}
		
	}

	/**
	 * stores the relation information about components and their build to database
	 * @param lst - list of {@link Dependency}
	 * @throws SQLException
	 */
	private void storeDependencies(List<Dependency> lst) throws Exception {
		Connection c = null;
		PreparedStatement stmt = null;
		try {
			c = establishConnection();
			String sql = InsertQueries.dependencyInsertQuery;
			stmt = c.prepareStatement(sql);
			for (Dependency d : lst){				
				stmt.setString(1, d.getReference());
				stmt.setString(2, d.getBuildId());
				stmt.setString(3, d.getType());
				stmt.addBatch();
			}
			stmt.executeBatch();
			c.commit();

		} catch (SQLException e) {
			throw new Exception("cannot store data access object -> Dependency.java\n" + e.getMessage());
		} finally {
			if (stmt != null) stmt.close();
			if (c != null) c.close();
		}
	}
}
