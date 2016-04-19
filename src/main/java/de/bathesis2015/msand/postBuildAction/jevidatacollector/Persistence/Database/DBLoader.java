package de.bathesis2015.msand.postBuildAction.jevidatacollector.Persistence.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Result.Database.BuildSummary;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Result.Database.ComponentSummary;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Result.Database.DependentComponent;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Transfer.Interface.Transferable;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Mapping.Enum.DependencyType;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Mapping.Mapper.Interface.DatabaseMapper;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Persistence.Database.Interface.DataLoader;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Persistence.Database.Util.SelectQueries;

/**
 * 
 * DBLoader.java
 * This class handles all requests that are send to database to select data
 *
 * @author Michael Sandritter
 *
 */
public class DBLoader implements DataLoader{
	
	/**
	 * path to the database file
	 */
	private String dbPath;
	
	/**
	 * mapper that maps the ResultSet to model objects {@link DatabaseMapper}
	 */
	private DatabaseMapper mapper;
	
	
	/**
	 * constructor 
	 * @param dbPath - database path
	 * @param mapper - {@link DatabaseMapper}
	 */
	@Inject
	public DBLoader(@Assisted String dbPath, DatabaseMapper mapper){
		this.dbPath = dbPath;
		this.mapper = mapper;
	}
	
	/**
	 * is establishing a connection with the databse
	 * @return {@link Connection}
	 * @throws Exception 
	 */
	private Connection establishConnection() throws Exception {
			Connection c = null;
			try {
				Class.forName("org.sqlite.JDBC");
				c = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
				c.setAutoCommit(false);
			} catch (Exception e) {
				throw new Exception("[DBLoader.java cannot establish connection]: " + dbPath + "\n" + e.getMessage());
			}
		
		return c;
	}
 
	@Override
	public Transferable loadDependencies(String value, DependencyType type) throws Exception {
		Connection c = null;
		PreparedStatement stmt = null;
		try {
			c = establishConnection();
			String sql = "";
			if (type == DependencyType.DIRECT) {
				sql = SelectQueries.directDepsQuery;
			} else if (type == DependencyType.ALL) {
				sql = SelectQueries.allDepsQuery;
			} 
			stmt = c.prepareStatement(sql);
			stmt.setString(1, value);
			
			ResultSet rs = stmt.executeQuery();
			return mapper.mapComponentResults(rs);
		} catch (SQLException e) {
			throw new SQLException("[DBLoader.java cannot dependencies]: " + e.getMessage());
		} finally {
			if (stmt != null) stmt.close();
			if (c != null) c.close();
		}
	}
	
	@Override
	public Transferable loadBuild(String reference) throws Exception {
		Connection c = null;
		PreparedStatement stmt = null;
		try {
			c = establishConnection();
			String sql = SelectQueries.buildOfMainDependencyQuery;
			stmt = c.prepareStatement(sql);
			stmt.setString(1, reference);
			
			ResultSet rs = stmt.executeQuery();
			return mapper.mapResult(rs, BuildSummary.class);
		} catch (SQLException e) {
			throw new SQLException("[DBLoader.java cannot load build]: " + e.getMessage());
		} finally {
			if (stmt != null) stmt.close();
			if (c != null) c.close();
		}
	}
	
	@Override
	public Transferable loadMainComponent(String buildId) throws Exception{
		Connection c = null;
		PreparedStatement stmt = null;
		try {
			c = establishConnection();
			String sql = SelectQueries.mainComponentQuery;
			stmt = c.prepareStatement(sql);
			stmt.setString(1, buildId);
			ResultSet rs = stmt.executeQuery();
			return mapper.mapResult(rs, ComponentSummary.class);
		} catch (SQLException e) {
			throw new SQLException("[DBLoader.java cannot load main component]: " + e.getMessage());
		} finally {
			if (stmt != null) stmt.close();
			if (c != null) c.close();
		}
	}
	
	public Transferable loadDependentComponents(String dependencyName) throws Exception{
		Connection c = null;
		PreparedStatement stmt = null;
		try {
			c = establishConnection();
			String sql = SelectQueries.dependentComponents;
			stmt = c.prepareStatement(sql);
			stmt.setString(1, dependencyName);
			
			ResultSet rs = stmt.executeQuery();
			return mapper.mapResult(rs, DependentComponent.class);
		} catch (SQLException e) {
			throw new SQLException("[DBLoader.java cannot load main component]: " + e.getMessage());
		} finally {
			if (stmt != null) stmt.close();
			if (c != null) c.close();
		}
	}
}
