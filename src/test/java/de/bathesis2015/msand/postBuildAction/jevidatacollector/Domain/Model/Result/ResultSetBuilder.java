package de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Result;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.DAO.Component;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.DAO.Stand;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Result.Database.BuildSummary;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Result.Database.ComponentSummary;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Result.Database.DependentComponent;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Persistence.Database.Enum.Field;

import static org.mockito.Mockito.*;

public class ResultSetBuilder{

	/**
	 * @param cls
	 * @return {@link ResultSet}-Mock
	 * @throws SQLException
	 */
	public ResultSet getMock(Class<?> cls) throws SQLException{
		ResultSet result = null;
		if (cls == Component.class){
			result = getComponentResult();
		} else if (cls == ComponentSummary.class){
			result = getComponentSummaryResult();
		} else if (cls == BuildSummary.class){
			result = getBuildSummaryResult();
		} else if (cls == Stand.class) {
			result = getStandResult();
		} else if (cls == DependentComponent.class){
			result = getDependentComponentsResult();
		}
		return result;
	}
	
	private ResultSet getDependentComponentsResult() throws SQLException {
		ResultSet result = mock(ResultSet.class);
		when(result.next()).thenReturn(true, false);
		when(result.getString(Field.REFERENCE.toString())).thenReturn(Field.REFERENCE.toString());
		when(result.getString(Field.VERSION.toString())).thenReturn(Field.VERSION.toString());
		when(result.getString(Field.COMPONENT_NAME.toString())).thenReturn(Field.COMPONENT_NAME.toString());
		when(result.getString(Field.BUILD_ID.toString())).thenReturn(Field.BUILD_ID.toString());
		when(result.getInt(Field.BUILD_NUMBER.toString())).thenReturn(1);
		when(result.getLong(Field.TIMESTAMP.toString())).thenReturn((long) 1);
		when(result.getString(Field.JOB_NAME.toString())).thenReturn(Field.JOB_NAME.toString());
		return result;
	}

	private ResultSet getStandResult() throws SQLException{
		ResultSet result = mock(ResultSet.class);
		when(result.next()).thenReturn(true, false);
		when(result.getString(Field.REFERENCE.toString())).thenReturn(Field.REFERENCE.toString());
		when(result.getString(Field.VERSION.toString())).thenReturn(Field.VERSION.toString());
		when(result.getString(Field.COMPONENT_NAME.toString())).thenReturn(Field.COMPONENT_NAME.toString());
		return result;
	}
	
	private ResultSet getComponentResult() throws SQLException{
		ResultSet result = mock(ResultSet.class);
		when(result.next()).thenReturn(true, false);
		when(result.getString(Field.NAME.toString())).thenReturn(Field.NAME.toString());
		when(result.getString(Field.VC_TYPE.toString())).thenReturn(Field.VC_TYPE.toString());
		when(result.getString(Field.VC_URL.toString())).thenReturn(Field.VC_URL.toString());
		return result;
	}
	
	private ResultSet getComponentSummaryResult() throws SQLException{
		ResultSet result = mock(ResultSet.class);
		when(result.next()).thenReturn(true, false);
		when(result.getString(Field.BUILD_ID.toString())).thenReturn(Field.BUILD_ID.toString());
		when(result.getString(Field.DEP_TYPE.toString())).thenReturn(Field.DEP_TYPE.toString());
		when(result.getString(Field.VERSION.toString())).thenReturn(Field.VERSION.toString());
		when(result.getString(Field.COMPONENT.toString())).thenReturn(Field.COMPONENT.toString());
		when(result.getString(Field.VC_URL.toString())).thenReturn(Field.VC_URL.toString());
		when(result.getString(Field.VC_TYPE.toString())).thenReturn(Field.VC_TYPE.toString());
		when(result.getString(Field.REFERENCE.toString())).thenReturn(Field.REFERENCE.toString());
		return result;
	}
	
	private ResultSet getBuildSummaryResult() throws SQLException{
		ResultSet result = mock(ResultSet.class);
		when(result.next()).thenReturn(true, false);
		when(result.getString(Field.BUILD_ID.toString())).thenReturn(Field.BUILD_ID.toString());
		when(result.getInt(Field.BUILD_NUMBER.toString())).thenReturn(1234);
		when(result.getLong(Field.TIMESTAMP.toString())).thenReturn((long) 1234);
		when(result.getString(Field.JOB_NAME.toString())).thenReturn(Field.JOB_NAME.toString());
		return result;
	}
}
