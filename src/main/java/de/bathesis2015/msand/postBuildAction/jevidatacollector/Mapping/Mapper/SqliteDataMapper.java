package de.bathesis2015.msand.postBuildAction.jevidatacollector.Mapping.Mapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Result.Database.BuildSummary;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Result.Database.ComponentSummary;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Result.Database.DependentComponent;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Transfer.TransferObject;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Transfer.Interface.Transferable;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Mapping.Mapper.Interface.DatabaseMapper;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Mapping.Mapper.Interface.FutureImplementation;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Persistence.Database.Enum.Field;

/**
 * 
 * SqliteDataMapper.java
 * This object maps {@link ResultSet} from the database to model objects
 *
 * @author Michael Sandritter
 *
 */
public class SqliteDataMapper implements DatabaseMapper{
	
	
	@Override
	public Transferable mapComponentResults(ResultSet result) throws SQLException {
		Transferable transport = new TransferObject();	
		Map<String, ComponentSummary> map = saveComponentSummary(result);
		transport.setMap(ComponentSummary.class, map);
		return transport;
	}
	
	@Override
	public Transferable mapResult(ResultSet result, Class<?> cls) throws SQLException{
		Transferable transport = new TransferObject();
		BuildSummary buildSum = null;
		if (cls == BuildSummary.class){			
			buildSum = saveBuildSummary(result);
			transport.setObject(BuildSummary.class, buildSum);
		} else if (cls == ComponentSummary.class){
			Map<String, ComponentSummary> map = saveComponentSummary(result);
			if (map.size() == 1){
				String key = (String) map.keySet().toArray()[0];
				transport.setObject(ComponentSummary.class, map.get(key));
			}
		} else if (cls == DependentComponent.class){
			transport.setMap(cls, saveDependentComponent(result));
		}
		return transport;
	}
	
	@FutureImplementation
	private String[] getColumnNameArray(ResultSet rs) {
		try {
			ResultSetMetaData rm = rs.getMetaData();
			String column[] = new String[rm.getColumnCount()];
			for (int i = 1; i <= column.length; i++){
				String s = rm.getColumnName(i);
				column[i -1] = s;
			}
			return column;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@FutureImplementation
	private String[] getColumnTypeArray(ResultSet rs) {
		try {
			ResultSetMetaData rm = rs.getMetaData();
			String column[] = new String[rm.getColumnCount()];
			for (int i = 1; i <= column.length; i++){
				String s = rm.getColumnTypeName(i);
				column[i -1] = s;
			}
			return column;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@SuppressWarnings("unused")
	@FutureImplementation
	private Object mapResult(ResultSet rs, String[] columnName, String[] columnTypeName){
		try {
			while (rs.next()){
				for (int i = 0; i < columnName.length-1; i++){
					Object o = rs.getObject(columnName[i]);
					if (columnTypeName[i].toUpperCase().equals("TEXT")){
						String s = o.toString();
					} else if (columnTypeName[i].toUpperCase().equals("LONG")){
						long l = (long) o;
					} else if (columnTypeName[i].toUpperCase().equals("INT")){
						int j = (int) o;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * is extracting build-specific information from the database ResultSet.
	 * saves extracted data in {@link BuildSummary} 
	 * @param result {@link ResultSet}
	 * @return {@link BuildSummary}
	 * @throws SQLException
	 */
	private BuildSummary saveBuildSummary(ResultSet result) throws SQLException {
		
		BuildSummary buildSum = null;
		while (result.next()){
			buildSum = new BuildSummary();
			buildSum.setBuildId(result.getString(Field.BUILD_ID.toString()));
			buildSum.setTime_stamp(result.getLong(Field.TIMESTAMP.toString()));
			buildSum.setJobName(result.getString(Field.JOB_NAME.toString()));
			buildSum.setBuildNumber(result.getInt(Field.BUILD_NUMBER.toString()));
		}
		return buildSum;
	}

	/**
	 * is extracting component- and version-specific information from the database ResultSet.
	 * saved extracted data in {@link Map<String, ComponentSummary>}
	 * @param result {@link ResultSet}
	 * @return Map<String, {@link ComponentSummary}>
	 * @throws SQLException
	 */
	private Map<String, ComponentSummary> saveComponentSummary(ResultSet result) throws SQLException{
		Map<String, ComponentSummary> map = new HashMap<String, ComponentSummary>();
		while (result.next()){			
			ComponentSummary sum = new ComponentSummary();
			String reference = result.getString(Field.REFERENCE.toString());
			sum.setBuildId(result.getString(Field.BUILD_ID.toString()));
			sum.setReference(reference);
			sum.setDependencyType(result.getString(Field.DEP_TYPE.toString()));
			sum.setVersion(result.getString(Field.VERSION.toString()));
			sum.setComponentName(result.getString(Field.COMPONENT.toString()));
			sum.setSourceUrl(result.getString(Field.VC_URL.toString()));
			sum.setSourceType(result.getString(Field.VC_TYPE.toString()));
			map.put(reference, sum);
		}
		return map;
	}

	/**
	 * 
	 * @param result
	 * @return Map<String, {@link DependentComponent}>
	 * @throws SQLException
	 */
	private Map<String, DependentComponent> saveDependentComponent(ResultSet result) throws SQLException {
		
		Map<String, DependentComponent> map = new HashMap<String, DependentComponent>();
		while (result.next()){
			DependentComponent c = new DependentComponent();
			c.setComponentName(result.getString(Field.COMPONENT_NAME.toString()));
			c.setJobName(result.getString(Field.JOB_NAME.toString()));
			c.setReference(result.getString(Field.REFERENCE.toString()));
			c.setVersion(result.getString(Field.VERSION.toString()));
			c.setBuildId(result.getString(Field.BUILD_ID.toString()));
			c.setBuildNumber(result.getInt(Field.BUILD_NUMBER.toString()));
			c.setTimeStamp(result.getLong(Field.TIMESTAMP.toString()));
			if (!map.containsKey(c.getComponentName())){
				map.put(c.getComponentName(), c);
			}
		}
		return map;
	}
}
