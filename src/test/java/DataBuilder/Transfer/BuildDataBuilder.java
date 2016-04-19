package DataBuilder.Transfer;

import static org.mockito.Mockito.*;

import java.util.Random;

import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Transfer.BuildData;

public class BuildDataBuilder {
	
	public BuildData getMock(int index){
		BuildData data = mock(BuildData.class);
		String idx = Integer.toString(index);
		when(data.getBuildId()).thenReturn("buildId" + idx);
		when(data.getDbPath()).thenReturn("pathToDatabase" + idx);
		when(data.getFormattedTime()).thenReturn("2015.05.1" + idx);
		when(data.getJobName()).thenReturn("jobName" + idx);
		when(data.getNumber()).thenReturn(index);
		when(data.getTimestamp()).thenReturn((long) index);
		when(data.getRevision()).thenReturn("reference" + idx);
		when(data.getSourceType()).thenReturn(getRandomSourceType());
		when(data.getSourceUrl()).thenReturn("https://sourceUrl." + idx);
		when(data.getVersion()).thenReturn("v1." + idx);
		return data;
	}
	
	public BuildData getLightMock(){
		BuildData data = mock(BuildData.class);
		when(data.getBuildId()).thenReturn("b-k4-02");
		return data;
	}
	
	public BuildData getDefectMock(){
		BuildData data = mock(BuildData.class);
		when(data.getBuildId()).thenReturn(null);
		return data;
	}
	
	
	private String getRandomSourceType(){
		Random r = new Random();
		int low = 1;
		int high = 2;
		int result = r.nextInt(high-low) + low;
		if (result == 1){
			return "git";
		} else {
			return "svn";
		}
	}
}
