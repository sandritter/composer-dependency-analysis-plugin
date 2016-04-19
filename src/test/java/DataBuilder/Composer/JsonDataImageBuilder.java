package DataBuilder.Composer;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Composer.JsonDataImage;

public class JsonDataImageBuilder {
	
	public JsonDataImage getMock(){
		JsonDataImage json = mock(JsonDataImage.class);
		when(json.getRequirements()).thenReturn(getRequirementMap());
		when(json.getName()).thenReturn("Bestellprozess-Komponente");
		when(json.getType()).thenReturn("typo3-cms-extension");
		return json;
	}
	
	private Map<String, String> getRequirementMap(){
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 1; i < 30; i++){
			String packageName = "packageName" + Integer.toString(i);
			String version = "v1." + Integer.toString(i); 
			map.put(packageName, version);
		}
		return map;
	}

}
